package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vibeosys.fitnessapp.R;

/**
 * Created by shrinivas on 25-04-2016.
 */
public class DietPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_plan);
        getSupportActionBar().setTitle("Diet plan");
    }
}
