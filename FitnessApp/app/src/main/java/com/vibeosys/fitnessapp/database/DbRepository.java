package com.vibeosys.fitnessapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Akshay software on 04-03-2017.
 */

public class DbRepository extends SQLiteOpenHelper {

    /**
     * Name of the database
     */
    public final static String DATABASE_NAME = "LawyerDiary";
    /**
     * Database version
     */
    public final static int DATABASE_VERSION = 1;

    private static final String TAG = DbRepository.class.getSimpleName();

    private String CREATE_USER_TABLE = "CREATE TABLE " + FitnessContract.UserLogin.TABLE_NAME + "(" +
            FitnessContract.UserLogin.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FitnessContract.UserLogin.USER_NAME + " TEXT NOT NULL," +
            FitnessContract.UserLogin.USER_EMAIL_ID + " TEXT NOT NULL UNIQUE," +
            FitnessContract.UserLogin.USER_PASSWORD + " TEXT NOT NULL)";


    public DbRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USER_TABLE);
            Log.d(TAG, "User Table is created ");
        } catch (SQLiteException e) {
            Log.e(TAG, "Cannot create table" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
