package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.utils.BmiCalculation;

import java.text.DecimalFormat;

public class MyProfileActivity extends BaseActivity {

    private TextView mUserName, mUserAge, mUserHeight, mUserWeight, mUserBMI;
    private double heightInMeter, userWeight;
    private double mBmi;

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
        mUserBMI = (TextView) findViewById(R.id.bmiTitle);
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
        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.profile_new_test);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);*/

        // ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        // circularImageView.setImageBitmap(circularBitmap);

    }

}
