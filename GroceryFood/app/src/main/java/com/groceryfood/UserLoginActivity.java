package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class UserLoginActivity extends AppCompatActivity {
TextView mForgotPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** Making this activity, full screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mForgotPwd = (TextView) findViewById(R.id.forgotPw);

    }
}
