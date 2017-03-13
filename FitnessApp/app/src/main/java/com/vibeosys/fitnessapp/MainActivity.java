package com.vibeosys.fitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextAction = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(nextAction);
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
}

