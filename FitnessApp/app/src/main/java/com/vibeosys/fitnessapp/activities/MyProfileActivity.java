package com.vibeosys.fitnessapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.WorkoutData;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.BmiCalculation;
import com.vibeosys.fitnessapp.utils.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MyProfileActivity extends BaseActivity {

    private static final String TAG = MyProfileActivity.class.getSimpleName();
    private TextView mUserName, mUserAge, mUserHeight, mUserWeight, mUserBMI, mBMIUnit, mUserBloodGroup;
    private double heightInMeter, userWeight;
    private double mBmi;
    private Button mEditBtn;
    private ImageView mImageView;
    private String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        //setContentView(R.layout.new_round_dashboard);
        getSupportActionBar().setTitle("My profile");
        mUserName = (TextView) findViewById(R.id.userNameTv);
        mUserAge = (TextView) findViewById(R.id.userAgeTv);
        mUserHeight = (TextView) findViewById(R.id.heightTv);
        mUserWeight = (TextView) findViewById(R.id.weightTv);
        mUserBMI = (TextView) findViewById(R.id.bmiTv);
        mBMIUnit = (TextView) findViewById(R.id.bmiTitle);
        mEditBtn = (Button) findViewById(R.id.edit_profile);
        mImageView = (ImageView) findViewById(R.id.circleView);
        mUserBloodGroup = (TextView) findViewById(R.id.bloodGroupsTv);
        mBMIUnit.setText(Html.fromHtml("Kg/m<sup>2</sup>"));
        try {
            heightInMeter = (sharedPrefManager.getUserHeight() / 3.2808);
            userWeight = sharedPrefManager.getUserWeight();
            mBmi = BmiCalculation.calculateBMI(heightInMeter, userWeight);
        } catch (Exception e) {
            e.toString();
        }
        mUserName.setText(sharedPrefManager.getUserName());
        mUserAge.setText("" + sharedPrefManager.getUserAge());
        mUserHeight.setText("" + sharedPrefManager.getUserHeight());
        mUserWeight.setText("" + sharedPrefManager.getUserWeight());
        DecimalFormat df2 = new DecimalFormat("###.##");
        mUserBMI.setText("" + Double.valueOf(df2.format(mBmi)));
        mUserBloodGroup.setText(sharedPrefManager.getUserBloodGroup());
        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.profile_new_test);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);*/

        // ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        // circularImageView.setImageBitmap(circularBitmap);
        try {
            imgDecodableString = sharedPrefManager.getUserImageString();
            Bitmap mBitmapString = BitmapFactory.decodeFile(imgDecodableString);
            mImageView.setImageBitmap(mBitmapString);

        } catch (Exception e) {
            Log.d("Profile", e.toString());
        }
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
