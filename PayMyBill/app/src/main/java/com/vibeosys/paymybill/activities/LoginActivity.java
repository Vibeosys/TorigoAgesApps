package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vibeosys.paymybill.R;

public class LoginActivity extends AppCompatActivity {

    Button mRegisterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login_title));
        mRegisterUser =(Button) findViewById(R.id.register_user);
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerUser = new Intent(getApplicationContext(),UserRegisterActivity.class);
                startActivity(registerUser);
            }
        });
    }
}
