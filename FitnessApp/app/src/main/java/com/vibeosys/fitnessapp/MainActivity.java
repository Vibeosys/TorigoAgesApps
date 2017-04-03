package com.vibeosys.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
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
import com.vibeosys.fitnessapp.utils.BmiCalculation;
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
                                setSessionManager(userInfo);
                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                intent.putExtra("UserInfo", userInfo);
                                startActivity(intent);
                                finish();

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
        double heightInMeter = (sharedPrefManager.getUserHeight() / 3.2808);
        double userWeight = sharedPrefManager.getUserWeight();
        double mBmi = BmiCalculation.calculateBMI(heightInMeter, userWeight);
        updateTheAdvice(mBmi);
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

    private void updateTheAdvice(double mBmi) {
        int delete = getContentResolver().delete(FitnessContract.UserWorkoutAdvice.CONTENT_URI, null, null);
        ArrayList<Long> workIds = new ArrayList<>();
        Cursor workOutMaster = getApplicationContext().getContentResolver().query(FitnessContract.WorkOutMaster.CONTENT_URI,
                new String[]{FitnessContract.WorkOutMaster.WKM_ID
                }, FitnessContract.WorkOutMaster.BMI_MAX + " >=? AND " + FitnessContract.WorkOutMaster.BMI_MIN + " <= ?",
                new String[]{String.valueOf(mBmi), String.valueOf(mBmi)}, null);

        if (workOutMaster.getCount() > 0) {
            workOutMaster.moveToFirst();
            do {
                long workId = workOutMaster.getLong(workOutMaster.getColumnIndex(FitnessContract.WorkOutMaster.WKM_ID));
                if (!workIds.contains(workId))
                    workIds.add(workId);
            }
            while (workOutMaster.moveToNext());
        }
        for (long workId : workIds) {
            ContentValues clientValues = new ContentValues();
            clientValues.put(FitnessContract.UserWorkoutAdvice.WORK_ID, workId);
            try {
                Uri insertCase = getContentResolver().insert(FitnessContract.UserWorkoutAdvice.CONTENT_URI, clientValues);
                long _dailyWorkId = ContentUris.parseId(insertCase);
                if (_dailyWorkId > 0) {

                }
            } catch (SQLException e) {
                Log.e(TAG, "Daily Workout is not added " + e.toString());
            }
        }
    }

}

