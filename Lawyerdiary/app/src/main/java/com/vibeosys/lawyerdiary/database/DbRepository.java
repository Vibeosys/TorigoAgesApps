package com.vibeosys.lawyerdiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.vibeosys.lawyerdiary.database.LawyerContract;

import android.util.Log;

/**
 * Created by akshay on 09-09-2016.
 */
public class DbRepository extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "LawyerDiary";
    public final static int DATABASE_VERSION = 1;

    private static final String TAG = DbRepository.class.getSimpleName();
    private String CREATE_CLIENT_TABLE = "CREATE TABLE " + LawyerContract.Client.TABLE_NAME + "(" +
            LawyerContract.Client._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Client.NAME + " TEXT NOT NULL," +
            LawyerContract.Client.EMAIL + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Client.PH_NUMBER + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Client.ADDRESS + " TEXT);";

    private String CREATE_CASE_TABLE = "CREATE TABLE " + LawyerContract.Case.TABLE_NAME + "(" +
            LawyerContract.Case._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Case.CASE_NAME + " TEXT NOT NULL," +
            LawyerContract.Case.CLIENT_ID + " INTEGER NOT NULL," +
            LawyerContract.Case.AGAINST + " TEXT NOT NULL," +
            LawyerContract.Case.SITUATION + " TEXT," +
            LawyerContract.Case.CASE_DATE + " TEXT," +
            LawyerContract.Case.COURT_LOCATION + " TEXT NOT NULL," +
            LawyerContract.Case.DESCRIPTION + " TEXT," +
            LawyerContract.Case.STATUS + " INTEGER DEFAULT 0," +
            LawyerContract.Case.KEY_POINTS + " TEXT,FOREIGN KEY(" +
            LawyerContract.Case.CLIENT_ID + ") REFERENCES " +
            LawyerContract.Client.TABLE_NAME + "(" + LawyerContract.Client._ID + "));";

    private String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + LawyerContract.Document.TABLE_NAME + "(" +
            LawyerContract.Document._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Document.DOCUMENT_NAME + " TEXT NOT NULL," +
            LawyerContract.Document.FILE_PATH + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Document.CASE_ID + " INTEGER NOT NULL," +
            LawyerContract.Document.LAST_UPDATED_DATE + " TEXT NOT NULL," +
            "FOREIGN KEY(" + LawyerContract.Document.CASE_ID + ") REFERENCES " +
            LawyerContract.Case.TABLE_NAME + "(" + LawyerContract.Case._ID + "));";

    private String CREATE_REMINDER_TABLE = "CREATE TABLE " + LawyerContract.Reminder.TABLE_NAME + " (" +
            LawyerContract.Reminder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Reminder.REMINDER_NAME + " TEXT NOT NULL," +
            LawyerContract.Reminder.START_DATE_TIME + " TEXT NOT NULL," +
            LawyerContract.Reminder.END_DATE_TIME + " TEXT NOT NULL," +
            LawyerContract.Reminder.LOCATION + " TEXT NOT NULL," +
            LawyerContract.Reminder.NOTE + " TEXT," +
            LawyerContract.Reminder.COLOUR + " TEXT NOT NULL," +
            LawyerContract.Reminder.CASE_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY(" + LawyerContract.Reminder.CASE_ID + ") REFERENCES " +
            LawyerContract.Case.TABLE_NAME + "(" + LawyerContract.Case._ID + "));";

    public DbRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_CLIENT_TABLE);
            Log.d(TAG, "##Client Table Create " + CREATE_CLIENT_TABLE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create client table" + e.toString());
        }
        try {
            db.execSQL(CREATE_CASE_TABLE);
            Log.d(TAG, "##Case Table Create " + CREATE_CASE_TABLE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create case table" + e.toString());
        }
        try {
            db.execSQL(CREATE_DOCUMENT_TABLE);
            Log.d(TAG, "##Document Table Create " + CREATE_DOCUMENT_TABLE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create document table" + e.toString());
        }
        try {
            db.execSQL(CREATE_REMINDER_TABLE);
            Log.d(TAG, "##Reminder Table Create " + CREATE_REMINDER_TABLE);
        } catch (SQLiteException e) {
            Log.e(TAG, "##Could not create reminder table" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
