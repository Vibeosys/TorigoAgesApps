package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.finaqa.R;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mNewUserLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        mNewUserLayout = (LinearLayout) findViewById(R.id.newUser);
        setTitle("User Login");
        mNewUserLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newUser:
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(intent);
        }

    }
}
