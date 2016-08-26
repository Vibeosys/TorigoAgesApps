package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.paymybill.R;

public class MyProfileActivity extends BaseActivity {

    private TextView mUserName,mUserEmailId,mUserMobileNo,mUserCountry;
    private String mImageUri;
    private ImageView mUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle(getResources().getString(R.string.my_profile));
        mUserName = (TextView) findViewById(R.id.nameTv);
        mUserEmailId = (TextView) findViewById(R.id.emailIdTv);
        mUserMobileNo =(TextView) findViewById(R.id.mobileTv);
        mUserCountry =(TextView) findViewById(R.id.countryTv);
        mUserPhoto =(ImageView) findViewById(R.id.userPhoto);
        //mImageUri = mDbRepository.getUserProfileImage(mSessionManager.getUserEmailId());
        mImageUri = mSessionManager.getUserProfileImage();
        if (!TextUtils.isEmpty(mImageUri)) {
            Bitmap mBitmapString = BitmapFactory.decodeFile(mImageUri);
            mUserPhoto.setImageBitmap(mBitmapString);
        }
        else {
            mUserPhoto.setImageDrawable(getResources().getDrawable(R.drawable.avatar_profile));
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserName()))
        {
            mUserName.setText(""+mSessionManager.getUserName());
        }
        else
        {
            mUserName.setText("");
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserEmailId()))
        {
            mUserEmailId.setText(""+mSessionManager.getUserEmailId());
        }
        else
        {
            mUserEmailId.setText("");
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserPhoneNo()))
        {
            mUserMobileNo.setText(""+mSessionManager.getUserPhoneNo());
        }else
        {
            mUserMobileNo.setText("");
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserCountry()))
        {
            mUserCountry.setText(""+mSessionManager.getUserCountry());
        }else
        {
            mUserCountry.setText("");
        }

        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_profile);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(getApplicationContext(), EditMyProfile.class);
                startActivity(editProfile);
            }
        });

    }
}
