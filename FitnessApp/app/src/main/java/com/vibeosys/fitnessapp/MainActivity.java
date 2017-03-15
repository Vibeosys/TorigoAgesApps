package com.vibeosys.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.activities.BaseActivity;
import com.vibeosys.fitnessapp.activities.DashboardActivity;
import com.vibeosys.fitnessapp.activities.ImageConverter;
import com.vibeosys.fitnessapp.activities.RegisterUserActivity;
import com.vibeosys.fitnessapp.data.UserInfo;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.UserAuth;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button login;
    private TextView mNewUser;
    private EditText mUserEmailId, mUserPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbtn);
        ImageView circularImageView = (ImageView) findViewById(R.id.imageButton);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.gym_logo_new);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        circularImageView.setImageBitmap(circularBitmap);
        context = MainActivity.this;
        mNewUser = (TextView) findViewById(R.id.newUser);
        mUserEmailId = (EditText) findViewById(R.id.editTextusername);
        mUserPassword = (EditText) findViewById(R.id.editTextuserpassword);
        if (UserAuth.isUserLogin()) {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean val = validation();
                if (val) {
                    String[] args = {mUserEmailId.getText().toString().trim(), mUserPassword.getText().toString().trim()};
                    try {
                        Cursor cursor = getContentResolver().query(FitnessContract.UserLogin.CONTENT_URI,
                                null, "user_email = ? AND user_password = ?", args, null);
                        if (cursor == null) {
                            Log.e(TAG, cursor.toString());
                            openAlertDialog();
                        } else if (cursor.getCount() < 1) {
                            Log.e(TAG, cursor.toString());
                            openAlertDialog();
                        }
                        if (cursor.getCount() >= 1) {
                            Toast.makeText(getApplicationContext(), "Valid user", Toast.LENGTH_SHORT).show();
                            if (cursor.moveToFirst()) {
                                Log.e(TAG, cursor.toString());
                                String userName = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_NAME));
                                String userEmailId = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_EMAIL_ID));
                                int userAge = cursor.getInt(cursor.getColumnIndex(FitnessContract.UserLogin.USER_AGE));
                                String userPassword = cursor.getString(cursor.getColumnIndex(FitnessContract.UserLogin.USER_PASSWORD));
                                double userHeight = cursor.getDouble(cursor.getColumnIndex(FitnessContract.UserLogin.USER_HEIGHT));
                                double userWeight = cursor.getDouble(cursor.getColumnIndex(FitnessContract.UserLogin.USER_WEIGHT));
                                cursor.close();
                                Log.e(TAG, cursor.toString());
                                UserInfo userInfo = new UserInfo(userName, userEmailId, userPassword, userAge, userHeight, userWeight);
                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                intent.putExtra("UserInfo", userInfo);
                                startActivity(intent);
                                finish();
                                setSessionManager(userInfo);
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

    private void setSessionManager(UserInfo userInfo) {
        sharedPrefManager.setUserName(userInfo.getUserName());
        sharedPrefManager.setUserEmailId(userInfo.getUserEmailId());
        sharedPrefManager.setUserAge(userInfo.getUserAge());
        sharedPrefManager.setUserHeight(userInfo.getUserHeight());
        sharedPrefManager.setUserWeight(userInfo.getUserWeight());
    }

    private void openAlertDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Login")
                .setMessage("Invalid credentials please check email id and password")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.cancel();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}

