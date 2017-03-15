package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;

public class MyProfileActivity extends BaseActivity {

    private TextView mUserName, mUserAge, mUserHeight, mUserWeight;

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
        mUserName.setText(sharedPrefManager.getUserName());
        mUserAge.setText("" + sharedPrefManager.getUserAge());
        mUserHeight.setText("" + sharedPrefManager.getUserHeight());
        mUserWeight.setText("" + sharedPrefManager.getUserWeight());
        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.profile_new_test);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);*/

        // ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        // circularImageView.setImageBitmap(circularBitmap);

    }

}
