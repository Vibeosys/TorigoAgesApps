package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle(getResources().getString(R.string.str_forgot_password));
    }
}
