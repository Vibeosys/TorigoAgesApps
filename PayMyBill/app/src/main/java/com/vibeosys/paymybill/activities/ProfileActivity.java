package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mImgProfile;
    private TextView mTxtName;
    private TextView mTxtEmailId;
    private TextView mTxtMobNo;
    private TextView mTxtCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(getResources().getString(R.string.profile_activity));
        mImgProfile = (ImageView) findViewById(R.id.profilePic);
        mTxtName = (TextView) findViewById(R.id.txtUserName);
        mTxtEmailId = (TextView) findViewById(R.id.txtEmailId);
        mTxtMobNo = (TextView) findViewById(R.id.txtMobNo);
        mTxtCountry = (TextView) findViewById(R.id.txtCountry);
    }
}
