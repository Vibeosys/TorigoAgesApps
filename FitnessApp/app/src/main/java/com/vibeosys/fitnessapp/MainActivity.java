package com.vibeosys.fitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.activities.BaseActivity;
import com.vibeosys.fitnessapp.activities.DashboardActivity;
import com.vibeosys.fitnessapp.activities.ImageConverter;
import com.vibeosys.fitnessapp.activities.RegisterUserActivity;
import com.vibeosys.fitnessapp.database.FitnessContract;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button login;
    private ImageView circularImageView;
    private TextView mNewUser;
    private EditText mUserEmailId, mUserPassword;
    private String[] mSelectionArgs = {""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbtn);
        getSupportActionBar().setTitle("");
        ImageView circularImageView = (ImageView) findViewById(R.id.imageButton);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.gym_logo_new);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        circularImageView.setImageBitmap(circularBitmap);
        mNewUser = (TextView) findViewById(R.id.newUser);
        mUserEmailId = (EditText) findViewById(R.id.editTextusername);
        mUserPassword = (EditText) findViewById(R.id.editTextuserpassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent nextAction = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(nextAction);*/
                boolean val = validation();
                if (val) {
                    String selectionArg = FitnessContract.UserLogin.USER_EMAIL_ID + " and " + FitnessContract.UserLogin.USER_PASSWORD;
                    String[] args = {mUserEmailId.getText().toString().trim(), mUserPassword.getText().toString().trim()};
                    try {
                        Cursor cursor = getContentResolver().query(FitnessContract.UserLogin.CONTENT_URI, null, "user_email = ? AND user_password = ?", args, null);
                        int cursorCount = cursor.getCount();
                        if (cursor == null) {
                            Log.e(TAG, cursor.toString());
                        } else if (cursor.getCount() < 1) {
                            Log.e(TAG, cursor.toString());
                        }
                        if (cursor.getCount() >= 1) {
                            Toast.makeText(getApplicationContext(), "Valid user", Toast.LENGTH_SHORT).show();
                            if (cursor.moveToFirst()) {
                                Log.e(TAG, cursor.toString());
                                String userName = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_NAME));
                                String userEmailId = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_EMAIL_ID));
                                int userAge = cursor.getInt(cursor.getColumnIndex(FitnessContract.UserLogin.USER_AGE));
                                String userPassword = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_PASSWORD));
                                double userHeightD = cursor.getDouble(cursor.getColumnIndex(FitnessContract.UserLogin.USER_HEIGHT));
                                double userWeightD = cursor.getDouble(cursor.getColumnIndex(FitnessContract.UserLogin.USER_WEIGHT));
                                cursor.close();
                                Log.e(TAG, cursor.toString());
                            }
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, e.toString());
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }

                }
            }
        });

        mNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validation() {
        String UserEmailId = mUserEmailId.getText().toString().trim();
        String UserPassword = mUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(UserEmailId)) {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("Please enter emailid");
            return false;
        } else if (!TextUtils.isEmpty(UserEmailId)) {
            if (!Patterns.EMAIL_ADDRESS.matcher(mUserEmailId.getText().toString()).matches()) {
                mUserEmailId.requestFocus();
                mUserEmailId.setError("Invalid emailid");
                return false;
            }

        }
        if (TextUtils.isEmpty(UserPassword)) {
            mUserPassword.requestFocus();
            mUserPassword.setError("Please enter password");
            return false;
        }
        return true;
    }
}

