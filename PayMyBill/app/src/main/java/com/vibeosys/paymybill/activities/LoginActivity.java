package com.vibeosys.paymybill.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.*;
import com.google.android.gms.*;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.vibeosys.paymybill.MainActivity;
import com.vibeosys.paymybill.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    private LoginButton mFacebbokLogin;
    private SignInButton mGoogSignInButton;
    private TextView mForgotPassword;
    private Button mRegisterUser;
    private Button mSignIn;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    protected GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private static int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private EditText mEmailId, mPassword;
    private boolean setFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));

        mForgotPassword = (TextView) findViewById(R.id.forgot_password);
        mFacebbokLogin = (LoginButton) findViewById(R.id.login_with_Facebook);
        mGoogSignInButton = (SignInButton) findViewById(R.id.google_Plus_signIn);
        mRegisterUser = (Button) findViewById(R.id.register_user);
        mSignIn = (Button) findViewById(R.id.sign_in_user_btn);
        mEmailId = (EditText) findViewById(R.id.emailIdEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);
        setGooglePlusButtonText(mGoogSignInButton, "Log in with Google");
        mFacebbokLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        mGoogSignInButton.setOnClickListener(this);
        mRegisterUser.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);

        /*Facebook login function*/
        mFacebbokLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessTokenTracker.startTracking();
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        /*Facebook access Token key*/
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                currentAccessToken = AccessToken.getCurrentAccessToken();
                if (currentAccessToken != null) {
                    String UserID = currentAccessToken.getUserId();
                    String FbToken = currentAccessToken.getToken();
                    mSessionManager.setUserAccessToken(FbToken);
                    mSessionManager.setLoginSource("1");
                    Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(loginIntent);
                    finish();

                }
            }
        };
        /*Facebook profile function*/
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                currentProfile = Profile.getCurrentProfile();
                if (currentProfile != null) {
                    String firstName = currentProfile.getFirstName();
                    String lastName = currentProfile.getLastName();
                    Uri FbIdT = currentProfile.getLinkUri();
                    String FbId = currentProfile.getId();
                    Toast.makeText(getApplicationContext(), "First Name " + firstName + " Last Name " + lastName, Toast.LENGTH_SHORT).show();
                } else if (currentProfile == null) {
                    /*String firstName= "";
                    String lastName =  "";
                    String FbId= "";*/
                    Log.d("FbProfile", "current profile found null");
                }
            }
        };
    }

    /*Login validation functions*/
    private boolean validate() {

        if (mEmailId.getText().toString().trim().length() == 0) {
            mEmailId.requestFocus();
            mEmailId.setError("Please enter email id");
            mEmailId.clearFocus();
            setFlag = false;
            return false;
        } else if (mEmailId.getText().toString().trim().length() != 0) {
            if (!Patterns.EMAIL_ADDRESS.matcher(mEmailId.getText().toString()).matches()) {
                mEmailId.requestFocus();
                mEmailId.setError("Invalid email Id");
                mEmailId.clearFocus();
                setFlag = false;
                return false;
            } else {
                Log.d("TAG", "TAG");
            }
        }
        if (mPassword.getText().toString().trim().length() == 0) {
            mPassword.getFocusables(View.FOCUS_RIGHT);
            mPassword.setError("Please enter Password");
            mPassword.clearFocus();
            setFlag = false;
            return false;
        }
        return true;
    }

    /*Google login error function*/
    private void resolveSignInError() {

        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    /*Google login initialization functions*/
    private void googlePlusAPIInit() {
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(LoginActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RC_SIGN_IN) {
            mSignInClicked = false;
        } else {
            if (mGoogleApiClient != null)
                mGoogleApiClient.connect();
        }
    }

    public static void LogoutFacebook() {
        LoginManager.getInstance().logOut();
        Log.d("FBLOGIN", "Log out");
        /*Intent logout = new Intent(context, MainActivity.class);
        context.startActivity(logout);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    /*Google login functions*/
    @Override
    public void onConnected(Bundle bundle) {
        getProfileInformation();
        Log.d("TAG", "LOGIN");
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                // GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
                return;
            } catch (Exception e) {
                mGoogleApiClient.connect();
            }

        }
        if (!mIntentInProgress) {
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    /*Google login get profile*/
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String personGoogleId = currentPerson.getId();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl+"person Id"+ personGoogleId );
                Toast toast = Toast.makeText(getApplicationContext(),"Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl+"person Id"+ personGoogleId ,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            } else {
               /* Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();*/
                Log.e("user profile is null","profile is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Alignment for google login button */
    protected void setGooglePlusButtonText(SignInButton mGoogSignInButton,
                                           String buttonText) {
        for (int i = 0; i < mGoogSignInButton.getChildCount(); i++) {
            View v = mGoogSignInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(14);
                tv.setPadding(0, 0, 14, 0);
                tv.setTypeface(null, Typeface.BOLD);
                tv.setText(buttonText);
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int btnId = v.getId();

        switch (btnId) {
            case R.id.google_Plus_signIn:
                googlePlusAPIInit();
                if (!mGoogleApiClient.isConnecting()) {
                    googlePlusAPIInit();
                    if (mGoogleApiClient.isConnected()) {
                        mSignInClicked = true;
                        resolveSignInError();
                    } else {
                        mGoogleApiClient.connect();
                        if (mGoogleApiClient.isConnected()) {
                            mSignInClicked = true;
                            resolveSignInError();
                        }
                    }

                }
                break;
            case R.id.sign_in_user_btn:
                boolean val = validate();
                if (val == false) {
                    /*call method on login successfully */
                } else if (val == true) {
                    Toast toast = Toast.makeText(getApplicationContext(), "All validations are done", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;

            case R.id.register_user:
                Intent registerUser = new Intent(getApplicationContext(), UserRegisterActivity.class);
                startActivity(registerUser);
                break;

            case R.id.forgot_password:
                Intent forgotPass = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(forgotPass);
                break;
        }
    }
}
