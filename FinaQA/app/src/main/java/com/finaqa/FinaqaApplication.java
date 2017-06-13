package com.finaqa;

import android.app.Application;

import com.finaqa.database.DbRepository;

import java.io.IOException;

/**
 * Created by shrinivas on 13-06-2017.
 */
public class FinaqaApplication extends Application {

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
