package com.vibeosys.lawyerdiary.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void callMain(View v) {
        Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iMain);
    }

}
