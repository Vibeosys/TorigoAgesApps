package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vibeosys.fitnessapp.R;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        //setContentView(R.layout.new_round_dashboard);
        getSupportActionBar().setTitle("My profile");


        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.profile_new_test);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);*/

        // ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        // circularImageView.setImageBitmap(circularBitmap);

    }

}
