package com.vibeosys.lawyerdiary.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;

public class RegisterUser extends BaseActivity implements View.OnClickListener {

    private EditText mUserName, mUserEmailId, mUserPassword, mConfirmPassword;
    private Button mUserRegisterBtn;
    long userRegisterId;
    private static final String TAG = RegisterUser.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mUserName = (EditText) findViewById(R.id.userName);
        mUserEmailId = (EditText) findViewById(R.id.userEmail);
        mUserPassword = (EditText) findViewById(R.id.UserPassword);
        mConfirmPassword = (EditText) findViewById(R.id.UserConfPassword);
        mUserRegisterBtn = (Button) findViewById(R.id.registerUser);
        mUserRegisterBtn.setOnClickListener(RegisterUser.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.registerUser:
                boolean returnVal = callToValidation();
                if (returnVal == true) {
                    boolean databaseVal = callToInsertValue();
                    if(databaseVal ==true)
                    {
                        Intent loginScreen = new Intent(RegisterUser.this,LoginActivity.class);
                        startActivity(loginScreen);
                        finish();
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_user_register_successfully), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else if(databaseVal ==false)
                    {
                        if(userRegisterId==-1)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_user_already), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_user_something_went), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }
                break;
        }
    }

    private boolean callToValidation() {
        String userName = mUserName.getText().toString().trim();
        String userEmail = mUserEmailId.getText().toString().trim();
        String userPassword = mUserPassword.getText().toString().trim();
        String userConfirmPwd = mConfirmPassword.getText().toString().trim();
        if (mUserName.getText().toString().trim().length() == 0) {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_user_name_val));
            return false;
        } else if (mUserEmailId.getText().toString().trim().length() == 0) {
            mUserEmailId.requestFocus();
            mUserEmailId.setError(getResources().getString(R.string.str_email_validation));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            mUserEmailId.requestFocus();
            mUserEmailId.setError(getResources().getString(R.string.str_email_pattern_validation));
            return false;
        }
        if (mUserPassword.getText().toString().trim().length() == 0) {
            mUserPassword.requestFocus();
            mUserPassword.setError(getResources().getString(R.string.str_pwd_validation));
            return false;
        } else if (mConfirmPassword.getText().toString().trim().length() == 0) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getResources().getString(R.string.str_conf_pwd_validation));
            return false;
        }
        if (!TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(userConfirmPwd)) {
            if (!userPassword.equals(userConfirmPwd)) {
                mConfirmPassword.requestFocus();
                mConfirmPassword.setError(getResources().getString(R.string.str_conf_pwd));
                return false;
            }
        }

        return true;
    }

    private boolean callToInsertValue() {
        String userName = mUserName.getText().toString().trim();
        String userEmail = mUserEmailId.getText().toString().trim();
        String userPassword = mUserPassword.getText().toString().trim();
        String userConfirmPwd = mConfirmPassword.getText().toString().trim();
        ContentValues registerUser = new ContentValues();
        registerUser.put(LawyerContract.UserLogin.USER_NAME, userName);
        registerUser.put(LawyerContract.UserLogin.USER_EMAIL_ID, userEmail);
        registerUser.put(LawyerContract.UserLogin.USER_PASSWORD, userPassword);
        try {

            Uri insertUserLogin = getContentResolver().insert(LawyerContract.UserLogin.CONTENT_URI, registerUser);
            userRegisterId = ContentUris.parseId(insertUserLogin);
            if(userRegisterId==-1)
            {
                return false;
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Login data is not inserted" + e.toString());
            return false;
        }
        return true;
    }
}
