package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.finaqa.MainActivity;
import com.finaqa.R;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mNewUserLayout;
    private Button mUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        mNewUserLayout = (LinearLayout) findViewById(R.id.newUser);
        mUserLogin = (Button) findViewById(R.id.userLoginBtn);
        setTitle("User Login");
        mNewUserLayout.setOnClickListener(this);
        mUserLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newUser:
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(intent);
                break;
            case R.id.userLoginBtn:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
