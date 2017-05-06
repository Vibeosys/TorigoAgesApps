package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.finaqa.MainActivity;
import com.finaqa.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mConsultantLogin, mGetStarted, mCustomerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mConsultantLogin = (LinearLayout) findViewById(R.id.consultantLogin);
        mGetStarted = (LinearLayout) findViewById(R.id.getStarted);
        mCustomerLogin = (LinearLayout) findViewById(R.id.customerLogin);
        mConsultantLogin.setOnClickListener(this);
        mGetStarted.setOnClickListener(this);
        mCustomerLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.consultantLogin:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.getStarted:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.customerLogin:
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                break;
            default:
                break;

        }
    }
}
