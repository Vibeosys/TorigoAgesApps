package com.vibeosys.paymybill.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.google.gson.Gson;
import com.vibeosys.paymybill.data.Sync;
import com.vibeosys.paymybill.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akshay on 24/05/2016.
 */

public class DbRepository extends SQLiteOpenHelper {

    private final String TAG = DbRepository.class.getSimpleName();
    private static int DATABASE_VERSION_NUMBER = 1;


    public DbRepository(Context context) {
        super(context,SqlContract.DATABASE_NAME  , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGISTER_TABLE="CREATE TABLE "+ SqlContract.SqlRegisterUser.TABLE_NAME +"("+
                SqlContract.SqlRegisterUser.USER_ID + " INTEGER PRIMARY KEY," + SqlContract.SqlRegisterUser.USER_NAME +
                " TEXT,"+ SqlContract.SqlRegisterUser.USER_EMAIL_ID +" TEXT,"+ SqlContract.SqlRegisterUser.USER_PASSWORD +
                " TEXT,"+ SqlContract.SqlRegisterUser.USER_IMAGE +" BLOB" +")";
        try
        {
            db.execSQL(CREATE_REGISTER_TABLE);
        }catch (SQLiteException e)
        {
            Log.d("TAG","SQL");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SqlContract.SqlRegisterUser.TABLE_NAME);
        onCreate(db);

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

    public ArrayList<Sync> getPendingSyncRecords() {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<Sync> syncTableData = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("select * from Sync ", null);
                syncTableData = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            Sync sync = new Sync();
                            sync.setJsonSync(cursor.getString(cursor.getColumnIndex("JsonSync")));
                            sync.setTableName(cursor.getString(cursor.getColumnIndex("TableName")));
                            syncTableData.add(sync);
                        } while (cursor.moveToNext());

                    }
                }
                flagError = true;
            }


        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError) ;
            //addError(TAG, "Get Sync Records", errorMessage);
        }
        return syncTableData;
    }

    public boolean clearSyncData() {
        SQLiteDatabase sqLiteDatabase = null;
        boolean flagError = false;
        String errorMessage = "";
        // ContentValues contentValues = null;
        sqLiteDatabase = getWritableDatabase();
        long count = -1;

        try {
            synchronized (sqLiteDatabase) {
                count = sqLiteDatabase.delete("sync", null,
                        null);
                // contentValues.clear();
                //sqLiteDatabase.close();
                flagError = true;
                Log.d(TAG, " ## clear sync sucessfully");
            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            e.printStackTrace();
            Log.d(TAG, "## clear sync is not sucessfully" + e.toString());
            //sqLiteDatabase.close();

        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError) ;
            //addError(TAG, "Clear Sync", errorMessage);
        }
        return count != -1;
    }

    public boolean addDataToSync(String tableName, String userId, String jsonSync) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long rowsAffected = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            contentValues = new ContentValues();
            contentValues.put("UserId", userId);
            contentValues.put("JsonSync", jsonSync);
            contentValues.put("TableName", tableName);
            if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
            rowsAffected = sqLiteDatabase.insert("Sync", null, contentValues);
            flagError = true;
        } catch (Exception e) {
            flagError = false;
            Log.e("SyncInsert", "Error occurred while inserting in Sync table " + e.toString());
        } finally {
            if (contentValues != null)
                contentValues.clear();

            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError) ;
            //addError(TAG, "Insert Sync Entry", errorMessage);
        }
        return rowsAffected != -1;
    }
}
/*
"SELECT Distinct menu.MenuId,menu.FoodType,menu.Image,menu.FbTypeId,"+
        "menu.MenuTitle,menu.Tags,menu.IsSpicy,menu_category.CategoryTitle,"+
        "menu.AvailabilityStatus,menu.Active,menu.Price ,(Select temp_order.Quantity "+
        "from temp_order   where menu.MenuId=temp_order.MenuId and temp_order.CustId=?)"+
        " as Quantity From menu Left Join menu_category on menu.CategoryId="+
        "menu_category.CategoryId left join temp_order on menu.MenuId=temp_order.MenuId "+
        "where menu.Active=1"*/
