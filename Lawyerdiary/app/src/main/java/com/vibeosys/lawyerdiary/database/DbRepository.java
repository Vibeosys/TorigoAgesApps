package com.vibeosys.lawyerdiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.vibeosys.lawyerdiary.database.LawyerContract;

import android.util.Log;

/**
 * Created by Vibeosys software on 09-09-2016.
 */

/**
 * Data base class that extend SQLiteOpenHelper
 * see SQLiteOpenHelper for more intro
 * <p>
 * Database consist of the 5 tables
 * </br>1. Client table contains Id,name,email,email,ph no., address,date
 * </br>2. Case table contains Id,case name,client id(FK of the client table), against,situation, date,
 * time,court location,description,status,key points.
 * </br>3. Document table contains Id, name,File path,case id(FK of the client table),last updated date.
 * </br>4. Reminder table contains the id,reminder name,start date time,end date time,
 * location,note,colour,case id(FK of the Case table)
 * </br>5. User table the application current user contains id,name,email,password.
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
    /**
     * Create the client table contains Id,name,email,email,ph no., address,date
     */
    private String CREATE_CLIENT_TABLE = "CREATE TABLE " + LawyerContract.Client.TABLE_NAME + "(" +
            LawyerContract.Client._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Client.NAME + " TEXT NOT NULL," +
            LawyerContract.Client.EMAIL + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Client.PH_NUMBER + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Client.ADDRESS + " TEXT," + LawyerContract.Client.IMG + " TEXT," +
            LawyerContract.Client.DATE_TIME + " INTEGER);";

    /**
     * Create Case details table contains Id,case name,client id(FK of the client table), against,situation, date,
     * time,court location,description,status,key points
     */
    private String CREATE_CASE_TABLE = "CREATE TABLE " + LawyerContract.Case.TABLE_NAME + "(" +
            LawyerContract.Case._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Case.CASE_NAME + " TEXT NOT NULL," +
            LawyerContract.Case.CLIENT_ID + " INTEGER NOT NULL," +
            LawyerContract.Case.AGAINST + " TEXT NOT NULL," +
            LawyerContract.Case.SITUATION + " TEXT," +
            LawyerContract.Case.CASE_DATE + " INTEGER," +
            LawyerContract.Case.CASE_TIME + " INTEGER," +
            LawyerContract.Case.COURT_LOCATION + " TEXT NOT NULL," +
            LawyerContract.Case.DESCRIPTION + " TEXT," +
            LawyerContract.Case.STATUS + " INTEGER DEFAULT 0," +
            LawyerContract.Case.KEY_POINTS + " TEXT,FOREIGN KEY(" +
            LawyerContract.Case.CLIENT_ID + ") REFERENCES " +
            LawyerContract.Client.TABLE_NAME + "(" + LawyerContract.Client._ID + "));";

    /**
     * Create Document table contains Id, name,File path,case id(FK of the Case table),last updated date.
     */
    private String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + LawyerContract.Document.TABLE_NAME + "(" +
            LawyerContract.Document._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Document.DOCUMENT_NAME + " TEXT NOT NULL," +
            LawyerContract.Document.FILE_PATH + " TEXT NOT NULL UNIQUE," +
            LawyerContract.Document.CASE_ID + " INTEGER NOT NULL," +
            LawyerContract.Document.LAST_UPDATED_DATE + " INTEGER NOT NULL," +
            "FOREIGN KEY(" + LawyerContract.Document.CASE_ID + ") REFERENCES " +
            LawyerContract.Case.TABLE_NAME + "(" + LawyerContract.Case._ID + "));";

    /**
     * Create Reminder table contains the id,reminder name,start date time,end date time,
     * location,note,colour,case id(FK of the Case table)
     */
    private String CREATE_REMINDER_TABLE = "CREATE TABLE " + LawyerContract.Reminder.TABLE_NAME + " (" +
            LawyerContract.Reminder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.Reminder.REMINDER_NAME + " TEXT NOT NULL," +
            LawyerContract.Reminder.START_DATE_TIME + " INTEGER NOT NULL," +
            LawyerContract.Reminder.END_DATE_TIME + " INTEGER NOT NULL," +
            LawyerContract.Reminder.LOCATION + " TEXT NOT NULL," +
            LawyerContract.Reminder.NOTE + " TEXT," +
            LawyerContract.Reminder.COLOUR + " TEXT NOT NULL," +
            LawyerContract.Reminder.CASE_ID + " INTEGER)";

    /**
     * Create User table contains id,name,email,password.
     */
    private String CREATE_USER_TABLE = "CREATE TABLE " + LawyerContract.UserLogin.TABLE_NAME + "(" +
            LawyerContract.UserLogin.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LawyerContract.UserLogin.USER_NAME + " TEXT NOT NULL," +
            LawyerContract.UserLogin.USER_EMAIL_ID + " TEXT NOT NULL UNIQUE," +
            LawyerContract.UserLogin.USER_PASSWORD + " TEXT NOT NULL)";
           /* +
            "FOREIGN KEY(" + LawyerContract.Reminder.CASE_ID + ") REFERENCES " +
            LawyerContract.Case.TABLE_NAME + "(" + LawyerContract.Case._ID + "));";*/

    /**
     * Constructor called when the application is started 1st time
     *
     * @param context Application context
     */
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
        try {
            db.execSQL(CREATE_USER_TABLE);
            Log.d(TAG, "User Table is created ");
        } catch (SQLiteException e) {
            Log.e(TAG, "Cannot create table" + e.toString());
        }
    }

    /**
     * On data base upgraded this method is called
     * called only when the new version is greater than old version
     *
     * @param db         Database object {@link SQLiteDatabase Sqlite Database}
     * @param oldVersion int Old version of the database
     * @param newVersion int new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
