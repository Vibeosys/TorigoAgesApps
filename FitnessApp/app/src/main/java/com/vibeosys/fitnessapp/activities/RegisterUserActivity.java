package com.vibeosys.fitnessapp.activities;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.fitnessapp.MainActivity;
import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.database.FitnessContract;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserName, mUserEmailId, mUserPwd, mUserConfirmPwd, mUserAge, mUserHeight,
            mUserWeight;
    private Button mRegisterUser;
    private static final String TAG = RegisterUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_register_user);
        mUserName = (EditText) findViewById(R.id.userName);
        mUserEmailId = (EditText) findViewById(R.id.userEmail);
        mUserPwd = (EditText) findViewById(R.id.userPwd);
        mUserConfirmPwd = (EditText) findViewById(R.id.userConfirmPwd);
        mUserAge = (EditText) findViewById(R.id.userAge);
        mUserHeight = (EditText) findViewById(R.id.userHeight);
        mUserWeight = (EditText) findViewById(R.id.userWeight);
        mRegisterUser = (Button) findViewById(R.id.registerBtn);
        mRegisterUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        boolean val = validateUser();
        if (val == true) {
            String userName = mUserName.getText().toString().trim();
            String userEmailId = mUserEmailId.getText().toString().trim();
            String userPwd = mUserPwd.getText().toString().trim();
            String userConfirmPwd = mUserConfirmPwd.getText().toString().trim();
            String userAge = mUserAge.getText().toString().trim();
            String userHeight = mUserHeight.getText().toString().trim();
            String userWeight = mUserWeight.getText().toString().trim();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FitnessContract.UserLogin.USER_NAME, userName);
            contentValues.put(FitnessContract.UserLogin.USER_EMAIL_ID, userEmailId);
            contentValues.put(FitnessContract.UserLogin.USER_PASSWORD, userPwd);
            contentValues.put(FitnessContract.UserLogin.USER_AGE, userAge);
            contentValues.put(FitnessContract.UserLogin.USER_HEIGHT, userHeight);
            contentValues.put(FitnessContract.UserLogin.USER_WEIGHT, userWeight);
            try {
                Uri insertUser = getContentResolver().insert(FitnessContract.UserLogin.CONTENT_URI, contentValues);
                long user_id = ContentUris.parseId(insertUser);
                if (user_id > 0) {
                    Toast.makeText(getApplicationContext(), "user Registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (SQLiteException e) {
                Log.e(TAG, "User Registration Page " + e.toString());
            }
        }

    }

    private boolean validateUser() {
        String userName = mUserName.getText().toString().trim();
        String userEmailId = mUserEmailId.getText().toString().trim();
        String userPwd = mUserPwd.getText().toString().trim();
        String userConfirmPwd = mUserConfirmPwd.getText().toString().trim();
        String userAge = mUserAge.getText().toString().trim();
        String userHeight = mUserHeight.getText().toString().trim();
        String userWeight = mUserWeight.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            mUserName.requestFocus();
            mUserName.setError("Please enter your name");
            return false;

        } else if (TextUtils.isEmpty(userEmailId)) {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("Please enter your emailid");
            return false;
        } else if (!TextUtils.isEmpty(userEmailId)) {
            if (!Patterns.EMAIL_ADDRESS.matcher(mUserEmailId.getText().toString()).matches()) {
                mUserEmailId.requestFocus();
                mUserEmailId.setError("Invalid emailid");
                return false;
            }
        }
        if (TextUtils.isEmpty(userPwd)) {
            mUserPwd.requestFocus();
            mUserPwd.setError("Please enter Your Password");
            return false;
        } else if (TextUtils.isEmpty(userConfirmPwd)) {
            mUserConfirmPwd.requestFocus();
            mUserConfirmPwd.setError("Please re-enter Your Password");
            return false;
        }
        if (!TextUtils.isEmpty(userPwd) && !TextUtils.isEmpty(userConfirmPwd)) {
            if (!userPwd.equals(userConfirmPwd)) {
                mUserConfirmPwd.requestFocus();
                mUserConfirmPwd.setError("Password did not match");
                return false;
            }
        }
        if (TextUtils.isEmpty(userAge)) {
            mUserAge.requestFocus();
            mUserAge.setError("Please enter your age");
            return false;
        } else if (TextUtils.isEmpty(userHeight)) {
            mUserHeight.requestFocus();
            mUserHeight.setError("Please enter your height");
            return false;
        } else if (TextUtils.isEmpty(userWeight)) {
            mUserWeight.requestFocus();
            mUserWeight.setError("Please enter your weight");
            return false;
        }
        return true;
    }
}
