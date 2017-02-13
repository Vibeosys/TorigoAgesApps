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

/**
 * Created by Vibeosys software on 27-04-2016.
 */


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
                    boolean insertReturnVal=callToInsertData();
                    if(insertReturnVal==true)
                    {
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                        finish();
                    }
                    else if(insertReturnVal==false)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.str_register_validation), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }
                break;
            case R.id.newUser:
                Intent registerUser = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(registerUser);
                finish();
                break;
        }
    }

    /**
     * This function is use to validate the password and email id field.
     * email id is also validated for its standard pattern.
     * @return it returns false when email id and password is empty or not valid and returns true for valid parameter.
     */
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

    /**
     * This function is use to insert  user  email id and password in to local data base.
     *
     * @return it returns true on successful insertion and returns false on when it cannot insert the data.
     */
    private boolean callToInsertData() {
        String mUserEmail = mUserName.getText().toString().trim();
        String mUserPwd = mPassword.getText().toString().trim();
        ContentValues userLoginVal = new ContentValues();
        userLoginVal.put(LawyerContract.UserLogin.USER_EMAIL_ID, mUserEmail);
        userLoginVal.put(LawyerContract.UserLogin.USER_PASSWORD, mUserPwd);

        try {
            String[] projection = {LawyerContract.UserLogin.USER_EMAIL_ID, LawyerContract.UserLogin.USER_ID, LawyerContract.UserLogin.USER_PASSWORD,LawyerContract.UserLogin.USER_NAME};
            String[] selectionArg = {mUserEmail};

            Cursor cursor = getApplicationContext().getContentResolver().query(LawyerContract.UserLogin.CONTENT_URI, projection, LawyerContract.UserLogin.USER_EMAIL_ID + "=?", selectionArg, null);
            int cursorCount = cursor.getCount();
            if (cursor.getCount() > 0) {
                int cnt = cursor.getCount();
                cursor.moveToFirst();
                do {

                    String email = cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_EMAIL_ID));
                    String Pwd = cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_PASSWORD));
                    String UserId = cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_ID));
                    String userName =cursor.getString(cursor.getColumnIndex(LawyerContract.UserLogin.USER_NAME));
                    callToSessionManager(userName,email,Pwd,UserId);
                } while (cursor.moveToNext());

            } else if(cursor.getCount()==0){
                Log.d("TAG", "Cannot perform insert operation");
                Log.d("TAG", "TAG");
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    /**
     * This function is called when user is successfully login and sets the parameter in session manager.
     * @param userName user email name in String format.
     * @param email user email id in String format.
     * @param pwd user password in String format.
     * @param userId user id in String format.
     */
    private void callToSessionManager(String userName,String email, String pwd, String userId) {
        mSessionManager.setUserId(userId);
        mSessionManager.setUserEmailId(email);
        mSessionManager.setUserName(userName);
        mSessionManager.setUserPassword(pwd);
    }
}
