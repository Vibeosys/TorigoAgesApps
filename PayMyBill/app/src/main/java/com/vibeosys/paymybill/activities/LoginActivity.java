package com.vibeosys.paymybill.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vibeosys.paymybill.R;

public class LoginActivity extends BaseActivity {

    Button mRegisterUser;
    Button mSignIn;
    TextView mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));
        mRegisterUser = (Button) findViewById(R.id.register_user);
        mForgotPassword =(TextView) findViewById(R.id.forgot_password);
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
    }
}
