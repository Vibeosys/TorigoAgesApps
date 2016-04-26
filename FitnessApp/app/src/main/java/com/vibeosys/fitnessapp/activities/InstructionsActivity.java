package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vibeosys.fitnessapp.R;


/**
 * Created by shrinivas on 25-04-2016.
 */
public class InstructionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_activity);
        getSupportActionBar().setTitle("Instructions");


    }
}
