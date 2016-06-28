package com.vibeosys.paymybill.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
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
import com.vibeosys.paymybill.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LoginActivity extends BaseActivity {

    Button mRegisterUser;
    Button mSignIn;
    LoginButton mFacebbokLogin;
    TextView mForgotPassword;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));

        mRegisterUser = (Button) findViewById(R.id.register_user);
        mForgotPassword =(TextView) findViewById(R.id.forgot_password);
        mFacebbokLogin =(LoginButton) findViewById(R.id.login_with_Facebook);
        mFacebbokLogin.setReadPermissions(Arrays.asList("public_profile", "email"));

        mFacebbokLogin.registerCallback(callbackManager,  new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessTokenTracker.startTracking();
                profileTracker.startTracking();

            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancel button is clicked",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Login Error with Facebook",Toast.LENGTH_SHORT).show();

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



        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registerUser = new Intent(getApplicationContext(), UserRegisterActivity.class);
                startActivity(registerUser);

            }
        });
        mSignIn = (Button) findViewById(R.id.sign_in_user_btn);
       /* mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(signInIntent);
            }
        });*/

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPass = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(forgotPass);

            }
        });

        /* To get the facebook hash key for development.
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);




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
    }
}
