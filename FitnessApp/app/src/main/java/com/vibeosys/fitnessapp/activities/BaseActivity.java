package com.vibeosys.fitnessapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vibeosys.fitnessapp.data.AppWorkoutData;
import com.vibeosys.fitnessapp.utils.SharedPrefManager;

/**
 * Created by akshay on 11-03-2017.
 */
public class BaseActivity extends AppCompatActivity {

    protected SharedPrefManager sharedPrefManager;
    protected AppWorkoutData appWorkoutData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        appWorkoutData = new AppWorkoutData(sharedPrefManager.getLastDate(),
                sharedPrefManager.getWorkId(), sharedPrefManager.getSetId(), sharedPrefManager.getDailyWorkId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        appWorkoutData.setWorkoutDate(sharedPrefManager.getLastDate());
        appWorkoutData.setWorkoutId(sharedPrefManager.getWorkId());
        appWorkoutData.setSetId(sharedPrefManager.getSetId());
        appWorkoutData.setDailyWorkId(sharedPrefManager.getDailyWorkId());
    }
}
