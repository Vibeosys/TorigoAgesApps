package com.vibeosys.lawyerdiary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.R;

public class RegisterUser extends BaseActivity implements View.OnClickListener {

    private EditText mUserName ,mUserEmailId,mUserPassword,mConfirmPassword;
    private Button mUserRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mUserName = (EditText) findViewById(R.id.userName);
        mUserEmailId =(EditText) findViewById(R.id.userEmail);
        mUserPassword =(EditText) findViewById(R.id.UserPassword);
        mConfirmPassword =(EditText) findViewById(R.id.UserConfPassword);
        mUserRegisterBtn = (Button) findViewById(R.id.registerUser);
        mUserRegisterBtn.setOnClickListener(RegisterUser.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.registerUser:
              boolean returnVal  = callToValidation();
                if(returnVal==true)
                {
                    Toast toast =Toast.makeText(getApplicationContext(),"All validations are done",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;
        }
    }
    private boolean callToValidation()
    {
        String userName= mUserName.getText().toString().trim();
        String userEmail = mUserEmailId.getText().toString().trim();
        String userPassword = mUserPassword.getText().toString().trim();
        String userConfirmPwd =mConfirmPassword.getText().toString().trim();
        if(mUserName.getText().toString().trim().length()==0)
        {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_user_name_val));
            return false;
        }else if(mUserEmailId.getText().toString().trim().length()==0)
        {
            mUserEmailId.requestFocus();
            mUserEmailId.setError(getResources().getString(R.string.str_email_validation));
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            mUserEmailId.requestFocus();
            mUserEmailId.setError(getResources().getString(R.string.str_email_pattern_validation));
            return false;
        }
        if(mUserPassword.getText().toString().trim().length()==0)
        {
            mUserPassword.requestFocus();
            mUserPassword.setError(getResources().getString(R.string.str_pwd_validation));
            return false;
        }else if(mConfirmPassword.getText().toString().trim().length()==0)
        {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getResources().getString(R.string.str_conf_pwd_validation));
            return false;
        }
        if(!TextUtils.isEmpty(userPassword)&& !TextUtils.isEmpty(userConfirmPwd))
        {
            if(!userPassword.equals(userConfirmPwd))
            {
                mConfirmPassword.requestFocus();
                mConfirmPassword.setError(getResources().getString(R.string.str_conf_pwd));
                return false;
            }
        }

        return true;
    }
}
