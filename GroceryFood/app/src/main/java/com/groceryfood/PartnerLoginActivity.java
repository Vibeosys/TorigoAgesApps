package com.groceryfood;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PartnerLoginActivity extends AppCompatActivity {

    TextView txtNewUser, txtForgotPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patner_login);
        getSupportActionBar().hide();
        txtNewUser = (TextView) findViewById(R.id.create_account_text);
        txtForgotPass = (TextView) findViewById(R.id.forgot_password_textview);
        btnLogin = (Button) findViewById(R.id.login_user);

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRegister = new Intent(getApplicationContext(), PartnerRegistrationActivity.class);
                startActivity(iRegister);
            }
        });
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRegister = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(iRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PartnerMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
