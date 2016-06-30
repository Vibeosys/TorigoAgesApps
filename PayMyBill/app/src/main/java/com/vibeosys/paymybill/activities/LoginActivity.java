package com.vibeosys.paymybill.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.vibeosys.paymybill.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    Button mRegisterUser;
    Button mSignIn;
    LoginButton mFacebbokLogin;
    SignInButton mGoogSignInButton;
    TextView mForgotPassword;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    protected GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    public static  int RC_SIGN_IN =0;
    private boolean mIntentInProgress ;
    private boolean mSignInClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));
        /* Google pluse login*/
        googlePlusAPIInit();
        mRegisterUser = (Button) findViewById(R.id.register_user);
        mForgotPassword =(TextView) findViewById(R.id.forgot_password);
        mFacebbokLogin =(LoginButton) findViewById(R.id.login_with_Facebook);
        mGoogSignInButton =  (SignInButton) findViewById(R.id.google_Plus_signIn);
        mFacebbokLogin.setReadPermissions(Arrays.asList("public_profile", "email"));


        mFacebbokLogin.registerCallback(callbackManager,  new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessTokenTracker.startTracking();
                profileTracker.startTracking();

            }
            @Override
            public void onCancel() {
              //  Toast.makeText(getApplicationContext(),"Cancel button is clicked",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
              //  Toast.makeText(getApplicationContext(),"Login Error with Facebook",Toast.LENGTH_SHORT).show();

            }
        });


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                currentAccessToken = AccessToken.getCurrentAccessToken();
                if(currentAccessToken !=null)
                {
                    String UserID = currentAccessToken.getUserId();
                    String FbToken = currentAccessToken.getToken();
                    Toast tost = Toast.makeText(getApplicationContext(),"Current Access Token "+FbToken ,Toast.LENGTH_SHORT);
                    tost.setGravity(Gravity.CENTER,0,0);
                    tost.show();
                }


            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                currentProfile = Profile.getCurrentProfile();
                if(currentProfile !=null)
                {
                    String firstName= currentProfile.getFirstName();
                    String lastName =  currentProfile.getLastName();
                    Uri FbIdT = currentProfile.getLinkUri();
                    String FbId= currentProfile.getId();
                    Toast.makeText(getApplicationContext(),"First Name "+firstName+" Last Name "+lastName,Toast.LENGTH_SHORT).show();
                }
                else if(currentProfile ==null)
                {
                    /*String firstName= "";
                    String lastName =  "";
                    String FbId= "";*/
                    Log.d("FbProfile","current profile found null");
                }
            }
        };

        mGoogSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if(!mGoogleApiClient.isConnecting())
                     {
                         googlePlusAPIInit();
                         if(mGoogleApiClient.isConnected())
                         {
                             mSignInClicked=true;
                             resolveSignInError();
                         }
                         else
                         {
                             mGoogleApiClient.connect();
                             if(mGoogleApiClient.isConnected())
                             {
                                 mSignInClicked=true;
                                 resolveSignInError();
                             }
                         }

                     }
            }
        });

        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registerUser = new Intent(getApplicationContext(), UserRegisterActivity.class);
                startActivity(registerUser);

            }
        });
        mSignIn = (Button) findViewById(R.id.sign_in_user_btn);
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPass = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(forgotPass);

            }
        });

        /* --- To get the facebook hash key for development.---
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.vibeosys.paymybill",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/



    }
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
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RC_SIGN_IN)
        {
            mSignInClicked = false;
        }
        else
        {
            mGoogleApiClient.connect();
        }
    }

     public static void LogoutFacebook()
    {
        LoginManager.getInstance().logOut();
        Log.d("FBLOGIN","Log out");
        /*Intent logout = new Intent(context, MainActivity.class);
        context.startActivity(logout);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.disconnect();
    }





    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null)
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        getProfileInformation();
        Log.d("TAG","LOGIN");

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try
            {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
               // GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
                return;
            }catch ( Exception e)
            {
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
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);
/*
                txtName.setText(personName);
                txtEmail.setText(email);*/

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                /*personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
