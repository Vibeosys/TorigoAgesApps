package com.vibeosys.fitnessapp;

import android.app.Application;

import com.vibeosys.fitnessapp.database.DbRepository;

import java.io.IOException;

/**
 * Created by akshay on 08-03-2017.
 */
public class FitnessApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbRepository dbRepository = new DbRepository(getApplicationContext());
        try {
            dbRepository.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbRepository.getDatabaseStructure();
    }
}
