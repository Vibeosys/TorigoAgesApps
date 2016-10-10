package com.vibeosys.lawyerdiary.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.nfc.Tag;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUserName, mPassword;
    private Button mLoginBtn;
    private TextView mNewUser;
    private static final String TAG = LoginActivity.class.getSimpleName();
    long userLoginEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.email_sign_in_button);
        mNewUser = (TextView) findViewById(R.id.newUser);
        mLoginBtn.setOnClickListener(LoginActivity.this);
        mNewUser.setOnClickListener(LoginActivity.this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.email_sign_in_button:
                boolean returnVal = callToValidation();
                if (returnVal == true) {
                    Toast toast = Toast.makeText(getApplicationContext(), "All Validations are done", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    callToInsertData();
                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(login);
                    finish();
                }
                break;
            case R.id.newUser:
                Intent registerUser = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(registerUser);
                finish();
                break;
        }
    }

    private boolean callToValidation() {
        String mUserEmail = mUserName.getText().toString().trim();
        String mUserPwd = mPassword.getText().toString().trim();
        if (mUserEmail.trim().length() == 0) {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_email_validation));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mUserEmail).matches()) {
            mUserName.requestFocus();
            mUserName.setError(getResources().getString(R.string.str_email_pattern_validation));
            return false;
        }
        if (mUserPwd.trim().length() == 0) {
            mPassword.requestFocus();
            mPassword.setError(getResources().getString(R.string.str_pwd_validation));
            return false;
        }
        return true;
    }

    private boolean callToInsertData() {
        String mUserEmail = mUserName.getText().toString().trim();
        String mUserPwd = mPassword.getText().toString().trim();
        ContentValues userLoginVal = new ContentValues();
        userLoginVal.put(LawyerContract.UserLogin.USER_EMAIL_ID, mUserEmail);
        userLoginVal.put(LawyerContract.UserLogin.USER_PASSWORD, mUserPwd);
        /*try {

            Uri insertUserLogin = getContentResolver().insert(LawyerContract.UserLogin.CONTENT_URI,userLoginVal);
            userLoginEventId = ContentUris.parseId(insertUserLogin);
        }catch (SQLiteException e)
        {
            Log.e(TAG,"Login data is not inserted"+e.toString());
        }*/
        try {
            String[] projection = {LawyerContract.UserLogin.USER_EMAIL_ID, LawyerContract.UserLogin.USER_ID, LawyerContract.UserLogin.USER_PASSWORD};
            String[] selectionArg = {mUserEmail};

            Cursor cursor = getApplicationContext().getContentResolver().query(LawyerContract.UserLogin.CONTENT_URI, projection, LawyerContract.UserLogin.USER_EMAIL_ID + "=?", selectionArg, null);
            if (cursor.getCount() > 0) {
                int cnt = cursor.getCount();
                cursor.moveToFirst();
                do {
                    String email = cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_EMAIL_ID));
                    String Pwd = cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_PASSWORD));
                    Log.d("TAG", "TAG");
                    Log.d("TAG", "TAG");
                    Log.d("TAG", "TAG");
                } while (cursor.moveToNext());

            } else {
                Log.d("TAG", "TAG");
                Log.d("TAG", "TAG");
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Login data  problem");
            Log.e(TAG, "Login data  problem");
            Log.e(TAG, "Login data  problem");
        }
        return false;
    }
}
