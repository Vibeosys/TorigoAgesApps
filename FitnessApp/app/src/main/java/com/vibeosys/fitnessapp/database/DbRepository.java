package com.vibeosys.fitnessapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vibeosys.fitnessapp.utils.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Akshay software on 04-03-2017.
 */

public class DbRepository extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "FitnessApp";
    public final static int DATABASE_VERSION = 1;

    private static final String TAG = DbRepository.class.getSimpleName();
    private Context mContext;

    public DbRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void getDatabaseStructure() {
        final ArrayList<String> dirArray = new ArrayList<String>();

        SQLiteDatabase DB = getReadableDatabase();
        //SQLiteDatabase DB = sqlHelper.getWritableDatabase();
        Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                dirArray.add(c.getString(c.getColumnIndex("name")));

                c.moveToNext();
            }
        }
        Log.i(TAG, "##" + dirArray);
        c.close();

    }

    //Create a empty database on the system
    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.v("DB Exists", "## db exists");
        }
        boolean dbExist1 = checkDataBase();
        if (!dbExist1) {
            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("## Error copying database");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            String myPath = AppConstants.DB_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException {
        String outFileName = AppConstants.DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }
}
