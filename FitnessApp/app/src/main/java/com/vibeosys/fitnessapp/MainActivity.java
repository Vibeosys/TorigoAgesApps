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

import com.vibeosys.fitnessapp.activities.BaseActivity;
import com.vibeosys.fitnessapp.activities.DashboardActivity;
import com.vibeosys.fitnessapp.database.FitnessContract;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbtn);
        getSupportActionBar().setTitle("");
        ImageView circularImageView = (ImageView) findViewById(R.id.imageButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextAction = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(nextAction);
            }
        });


    }
}

