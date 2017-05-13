package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.finaqa.R;

public class ConsultantLogin extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mRegisterUser;
    private Button mConsultantLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_login);
        setTitle("Consultant Login");
        mRegisterUser = (LinearLayout) findViewById(R.id.newUser);
        mConsultantLogin = (Button) findViewById(R.id.consultant_login);
        mRegisterUser.setOnClickListener(this);
        mConsultantLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newUser:
                Intent intent = new Intent(getApplicationContext(), ConsultantRegistration.class);
                startActivity(intent);
                break;
            case R.id.consultant_login:
                Intent intent1 = new Intent(getApplicationContext(), ConsultantMainActivity.class);
                startActivity(intent1);
                break;

        }

    }
}
