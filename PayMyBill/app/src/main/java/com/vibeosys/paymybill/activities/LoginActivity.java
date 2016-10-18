package com.vibeosys.paymybill.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.*;
import com.google.android.gms.*;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.vibeosys.paymybill.MainActivity;
import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.CountryAdapter;
import com.vibeosys.paymybill.data.databasedto.FriendDbDTO;
import com.vibeosys.paymybill.data.databasedto.UserRegisterDbDTO;
import com.vibeosys.paymybill.util.UserAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    private LoginButton mFacebbokLogin;
    private SignInButton mGoogSignInButton;
    private TextView mForgotPassword;
    private Button mRegisterUser;
    private Button mSignIn;
    private CallbackManager callbackManager;
    protected GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private static int RC_SIGN_IN = 405;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private EditText mEmailId, mPassword;
    private boolean setFlag = true;
    private int ACCOUNT_PERMISSION_CODE = 14;
    private int count = 0;
    private Context context = this;
    private Locale selectedLocale = null;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(LoginActivity.this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));
        googlePlusAPIInit();
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
        selectUserCurrency();
        /*Facebook login function*/
        mFacebbokLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Set<String> permiss = loginResult.getRecentlyGrantedPermissions();
                loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                JSONObject data = response.getJSONObject();
                                String photoUrl = "";
                                if (object != null) {
                                    String name = object.optString("name");
                                    String emailId = object.optString("email");
                                    String uid = object.optString("id");
                                    String firstName = object.optString("first_name");
                                    String lastName = object.optString("last_name");

                                    try {
                                        photoUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    String FbTokenId = token.getUserId();
                                    callFromFacebookLogin(emailId, "", firstName, lastName, "", 1, FbTokenId, photoUrl);

                                }
                            }
                        });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "email,first_name,last_name,picture.type(large)");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }

    private void selectUserCurrency() {
        final Dialog currencyDialog = new Dialog(context);
        currencyDialog.setTitle(getResources().getString(R.string.select_country));
        currencyDialog.setContentView(R.layout.dialog_select_country);
        currencyDialog.setCancelable(false);
        Spinner spnCountry = (Spinner) currencyDialog.findViewById(R.id.spnCountry);
        Button btnOk = (Button) currencyDialog.findViewById(R.id.btnOk);
        final CountryAdapter adapter = new CountryAdapter(context);
        spnCountry.setAdapter(adapter);

        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocale = (Locale) adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLocale != null) {
                    Currency currency = Currency.getInstance(selectedLocale);
                    mSessionManager.setUserCurrency(currency);
                    Log.d(TAG, "##" + currency.getSymbol());
                    currencyDialog.dismiss();
                }
            }
        });
        currencyDialog.show();
    }

    public void callFromFacebookLogin(String email, String password, String firstName, String lastName,
                                      String phoneNo, int loginSource, String FbTokenId, String photourl) {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        UserRegisterDbDTO userRegisterDbDTO = new UserRegisterDbDTO(email, "", firstName, lastName, "",
                loginSource, FbTokenId, photourl);
        FriendDbDTO friendDbDTO = new FriendDbDTO(1, firstName, "", email, photourl);
        int returnVal;
        long friendID;
        friendID = mDbRepository.addFirstFriend(friendDbDTO);
        returnVal = mDbRepository.userRegisterSocialMedia(userRegisterDbDTO);
        String convertVal = String.valueOf(returnVal);
        callToSessionManager(email, "1", firstName, lastName, FbTokenId, convertVal);
        if (returnVal == 1) {
            //Login success
            Intent mainActivity = new Intent(getApplication(), MainActivity.class);
            startActivity(mainActivity);
            finish();
            progressDialog.cancel();
        }
        if (returnVal == 3 || returnVal == 2) {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot able to insert record", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (progressDialog.isShowing())
                progressDialog.cancel();
        }
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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, LoginActivity.this)
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addScope(new Scope(Scopes.PLUS_ME))
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            //mSignInClicked = false;
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getProfileInformation(result);
        } else {
            if (mGoogleApiClient != null)
                mGoogleApiClient.connect();
        }
    }

    public static void LogoutFacebook() {
        try {
            LoginManager.getInstance().logOut();
            Log.d("FBLOGIN", "Log out");

        } catch (FacebookException e) {
            e.printStackTrace();
            Log.d(TAG, "Facebook logout exception");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
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
       // getProfileInformation();
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
    /*private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String personGoogleId = currentPerson.getId();
                callFromGoogleRegisterUser(email, personName, 2, personGoogleId, personPhotoUrl);
            } else {
                Log.e("user profile is null", "profile is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    private void getProfileInformation(GoogleSignInResult result) {
        try {
            if (result.isSuccess()) {

                GoogleSignInAccount acct = result.getSignInAccount();
                String email = acct.getEmail();
                Uri Img = acct.getPhotoUrl();
                String profileImg=null;
                if (Img != null) {
                    profileImg = String.valueOf(Img);
                }
                String id = acct.getId();
               String  name = acct.getDisplayName();
                callFromGoogleRegisterUser(email, name, 2, id,profileImg);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callFromGoogleRegisterUser(String email, String personName,
                                            int loginSource, String googleId, String PhotoUrl) {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        UserRegisterDbDTO userRegisterDbDTO = new UserRegisterDbDTO(email, "", personName, "", "",
                loginSource, googleId, PhotoUrl);
        FriendDbDTO friendDbDTO = new FriendDbDTO(1, personName, "", email, PhotoUrl);
        int returnVal;
        long friendID;
        returnVal = mDbRepository.userRegisterSocialMedia(userRegisterDbDTO);
        friendID = mDbRepository.addFirstFriend(friendDbDTO);
        String convertVal = String.valueOf(friendID);
        callToSessionManager(email, "2", personName, "", googleId, convertVal);
        if (returnVal == 1) {
            //Login success
            Intent mainActivity = new Intent(getApplication(), MainActivity.class);
            startActivity(mainActivity);
            finish();
            progressDialog.cancel();
        }
        if (returnVal == 3 || returnVal == 2) {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot able to insert record", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
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
                getAccountPermission();
                break;
            case R.id.sign_in_user_btn:
                boolean val = validate();
                if (val == true) {
                    String mUserEmailId = mEmailId.getText().toString().trim();
                    String mUserPassword = mPassword.getText().toString().trim();
                    callToUserRegistration(mUserEmailId, mUserPassword);
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

    private void callToUserRegistration(String mUserEmailId, String mUserPassword) {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        int returnVal = mDbRepository.CheckUserRegistration(mUserEmailId, mUserPassword);
        FriendDbDTO friendDbDTO = new FriendDbDTO(1, "", "", mUserEmailId, "");
        long friendID = mDbRepository.addFirstFriend(friendDbDTO);
        String convertVal = String.valueOf(friendID);
        UserRegisterDbDTO userRegisterDbDTO = mDbRepository.getUserRegistrationDetails(mUserEmailId, mUserPassword);
        if (userRegisterDbDTO != null) {
            callToSessionManager(mUserEmailId, "3", userRegisterDbDTO.getFirstName().toString(),
                    userRegisterDbDTO.getLastName().toString(), "", convertVal);
        } else {
            callToSessionManager(mUserEmailId, "3", "", "", "", convertVal);
        }

        if (returnVal == 1) {
            //Login success
            Intent mainActivity = new Intent(getApplication(), MainActivity.class);
            startActivity(mainActivity);
            finish();
            progressDialog.cancel();
        }
        if (returnVal == 2) {
            mPassword.requestFocus();
            mPassword.setError("Incorrect Password");
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        }
        if (returnVal == 3) {
            mEmailId.requestFocus();
            mEmailId.setError("Invalid email Id");
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        }
        if (returnVal == -1 || returnVal == 4 || returnVal == 5) {
            Toast toast = Toast.makeText(getApplicationContext(), "user sign in error", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        }

    }

    public void getAccountPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS},
                ACCOUNT_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCOUNT_PERMISSION_CODE && grantResults[0] == 0) {
            signInGooglePluse();
           // googlePlusAPIInit();
            /*if (!mGoogleApiClient.isConnecting()) {
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
            }*/
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void callToSessionManager(String emailId, String loginSource, String firstName,
                                     String lastName, String accessToken, String userFriendId) {
        mSessionManager.setUserEmailId(emailId);
        mSessionManager.setLoginSource(loginSource);
        mSessionManager.setUserName(firstName, lastName);
        mSessionManager.setUserAccessToken(accessToken);
        mSessionManager.setUserFriendId(userFriendId);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (!UserAuth.isUserLoggedIn()) {
            if (count == 1) {
                moveTaskToBack(true);
                finish();
            } else {
                count = count + 1;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Press once's again back button will exit from application", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


        }
    }
    private void signInGooglePluse() {
        if (mGoogleApiClient != null) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);

        }

    }
}
