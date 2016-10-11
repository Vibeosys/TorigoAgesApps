package com.vibeosys.lawyerdiary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vibeosys.lawyerdiary.database.DbRepository;
import com.vibeosys.lawyerdiary.utils.SessionManager;

/**
 * Created by akshay on 08-09-2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected static SessionManager mSessionManager = null;
    protected DbRepository mDbRepository = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = SessionManager.getInstance(getApplicationContext());
        mDbRepository = new DbRepository(getApplicationContext());
    }
}
