package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.finaqa.R;

public class ConsultantLogin extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_login);
        setTitle("Consultant Login");
        mRegisterUser = (LinearLayout) findViewById(R.id.newUser);
        mRegisterUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newUser:
                Intent intent = new Intent(getApplicationContext(), ConsultantRegistration.class);
                startActivity(intent);
        }

    }
}
