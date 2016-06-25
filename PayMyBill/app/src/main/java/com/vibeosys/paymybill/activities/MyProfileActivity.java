package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vibeosys.paymybill.R;

public class MyProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle(getResources().getString(R.string.my_profile));
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_profile);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast toast = Toast.makeText(getApplicationContext(),
                        "Now new activity will open for edit text", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();*/
                Intent editProfile = new Intent(getApplicationContext(),EditMyProfile.class);
                startActivity(editProfile);
            }
        });

    }
}
