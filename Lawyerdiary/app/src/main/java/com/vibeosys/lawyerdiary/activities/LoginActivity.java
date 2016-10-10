package com.vibeosys.lawyerdiary.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUserName,mPassword;
    private Button mLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.email_sign_in_button);
        mLoginBtn.setOnClickListener(LoginActivity.this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
                case R.id.email_sign_in_button:
                boolean returnVal =callToValidation();
                if(returnVal==true)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"All Validations are done",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    Intent login = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(login);
                    finish();
                }
                break;
        }
    }
    private boolean callToValidation()
    {
        String mUserEmail= mUserName.getText().toString().trim();
        String mUserPwd = mPassword.getText().toString().trim();
        if(mUserEmail.trim().length()==0)
        {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_email_validation));
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mUserEmail).matches())
        {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_email_pattern_validation));
            return false;
        }
        if(mUserPwd.trim().length()==0)
        {
            mPassword.requestFocus();
            mPassword.setError(getResources().getString(R.string.str_pwd_validation));
            return false;
        }
        return true;
    }

}
