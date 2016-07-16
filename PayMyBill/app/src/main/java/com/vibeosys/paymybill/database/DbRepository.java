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
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.data.Sync;
import com.vibeosys.paymybill.data.databasedto.FriendDbDTO;
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
    private final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SqlContract.SqlRegisterUser.TABLE_NAME + "(" +
                    SqlContract.SqlRegisterUser.USER_ID + " INTEGER NOT NULL,"
                    + SqlContract.SqlRegisterUser.USER_NAME
                    + " TEXT," + SqlContract.SqlRegisterUser.USER_EMAIL_ID
                    + " TEXT," + SqlContract.SqlRegisterUser.USER_PASSWORD +
                    " TEXT," + SqlContract.SqlRegisterUser.USER_SOURCE + " TEXT,"
                    + SqlContract.SqlRegisterUser.USER_LOGIN_SOURCE_KEY + " TEXT,"
                    + SqlContract.SqlRegisterUser.USER_ROLE_ID + " INTEGER,"
                    + SqlContract.SqlRegisterUser.USER_ACTIVE + " INTEGER," +
                    SqlContract.SqlRegisterUser.USER_IMAGE + " VARCHAR(55),PRIMARY KEY("
                    + SqlContract.SqlRegisterUser.USER_ID + "))";
    private final String CREATE_CURRENCY =
            "CREATE TABLE IF NOT EXISTS " + SqlContract.SqlCurrency.TABLE_NAME + "(" +
                    SqlContract.SqlCurrency.CURRENCY_ID + " INTEGER NOT NULL,"
                    + SqlContract.SqlCurrency.CURRENCY + " VARCHAR(10),PRIMARY KEY("
                    + SqlContract.SqlCurrency.CURRENCY_ID + "))";
    private final String CREATE_FRIEND = "CREATE TABLE IF NOT EXISTS"
            + SqlContract.SqlFriend.TABLE_NAME + "(" + SqlContract.SqlFriend.FRIEND_ID + " INTEGER NOT NULL," +
            SqlContract.SqlFriend.FRIEND_NAME + " VARCHAR(55)," + SqlContract.SqlFriend.FRIEND_CONTACT_NO + " VARCHAR(25),"
            + SqlContract.SqlFriend.FRIEND_EMAIL + " VARCHAR(45)," + SqlContract.SqlFriend.FRIEND_PHOTO + " VARCHAR(45),"
            + "PRIMARY KEY(" + SqlContract.SqlFriend.FRIEND_ID + ") )";
    private final String BILL_TYPE =
            "CREATE TABLE IF NOT EXISTS " + SqlContract.SqlBillType.TABLE_NAME + "(" +
                    SqlContract.SqlBillType.BILL_ID + " INTEGER NOT NULL," +
                    SqlContract.SqlBillType.BILL_TYPE + " TEXT,PRIMARY KEY("
                    + SqlContract.SqlBillType.BILL_ID + "))";

    private final String BILL_SHARED =
            "CREATE TABLE IF NOT EXISTS " + SqlContract.SqlBillShared.TABLE_NAME + "(" +
                    SqlContract.SqlBillShared.BILL_SHARED_ID + " INTEGER NOT NULL,"
                    + SqlContract.SqlBillShared.BILL_TRANSACTION_ID + " INTEGER," +
                    SqlContract.SqlBillShared.FRIEND_ID + " INTEGER,PRIMARY KEY(" +
                    SqlContract.SqlBillShared.BILL_SHARED_ID + "))";

    private final String TRANSACTION =
            "CREATE TABLE IF NOT EXISTS " + SqlContract.SqlTransaction.TABLE_NAME + "(" +
                    SqlContract.SqlTransaction.TRANSACTION_ID + " INTEGER NOT NULL,"
                    + SqlContract.SqlTransaction.TRANSACTION_CURRENCY + " INTEGER," +
                    SqlContract.SqlTransaction.TRANSACTION_IN_AMOUNT + " DOUBLE(17,10) DEFAULT 0," +
                    SqlContract.SqlTransaction.TRANSACTION_OUT_AMOUNT + " DOUBLE(17,10) DEFAULT 0," +
                    SqlContract.SqlTransaction.TRANSACTION_PAID_ON_DATE + " DATETIME," +
                    SqlContract.SqlTransaction.TRANSACTION_GENERATED_DATE + " DATETIME," +
                    SqlContract.SqlTransaction.TRANSACTION_DESCRIPTION + " VARCHAR(255)," +
                    SqlContract.SqlTransaction.TRANSACTION_BILL_TYPE + " INTEGER," + ",PRIMARY KEY("
                    + SqlContract.SqlTransaction.TRANSACTION_ID + ")";

    public DbRepository(Context context) {
        super(context, SqlContract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_USER_TABLE);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create User" + CREATE_USER_TABLE);
        }
        try {
            db.execSQL(CREATE_FRIEND);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create Friend" + CREATE_FRIEND);
        }
        try {
            db.execSQL(CREATE_CURRENCY);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create Currency" + CREATE_CURRENCY);
        }
        try {
            db.execSQL(BILL_TYPE);
        } catch (SQLiteException e) {
            Log.d(TAG, "Bill type" + BILL_TYPE);
        }
        try {
            db.execSQL(TRANSACTION);
        } catch (SQLiteException e) {
            Log.d(TAG, "Transaction" + TRANSACTION);
        }
        try {
            db.execSQL(BILL_SHARED);
        } catch (SQLiteException e) {
            Log.d(TAG, "Bill Shared" + BILL_SHARED);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SqlContract.SqlRegisterUser.TABLE_NAME);
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

    public long getLastId(SQLiteDatabase db, String columnName, String tableName) {
        long lLastId = 0;
        try {
            final String sql = "SELECT " + columnName + " FROM " + tableName
                    + " ORDER BY " + columnName + " DESC LIMIT 1";
            Cursor c = db.rawQuery(sql, null);
            if (c != null && c.moveToFirst()) {
                lLastId = c.getLong(0); // The 0 is the column index, we only
                // have 1 column, so the index is 0
            }
            // db.close();
            return lLastId + 1;
        } catch (SQLiteException e) {
            Log.e(TAG, "Get Last" + e);
            return -1;
        } finally {
            Log.i(TAG, "Last MyDetails Id=" + lLastId);
            // db.close();
        }
    }

    public boolean insertFriend(FriendDbDTO friendDbDTO) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlFriend.FRIEND_ID, getLastId
                        (sqLiteDatabase, SqlContract.SqlFriend.FRIEND_ID, SqlContract.SqlFriend.TABLE_NAME));
                contentValues.put(SqlContract.SqlFriend.FRIEND_NAME, friendDbDTO.getName());
                contentValues.put(SqlContract.SqlFriend.FRIEND_CONTACT_NO, friendDbDTO.getContact());
                contentValues.put(SqlContract.SqlFriend.FRIEND_EMAIL, friendDbDTO.getEmail());
                contentValues.put(SqlContract.SqlFriend.FRIEND_PHOTO, friendDbDTO.getImage());
                if (!sqLiteDatabase.isOpen()) sqLiteDatabase = getWritableDatabase();
                count = sqLiteDatabase.insert(SqlContract.SqlFriend.TABLE_NAME, null, contentValues);
                contentValues.clear();
                Log.d(TAG, "## Friend is Added Successfully");
                flagError = true;

            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Friend " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##Insert Friend" + errorMessage);
        }
        return flagError;
    }

    public ArrayList<FriendDbDTO> getFriendList() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FriendDbDTO> friendDbDTOs = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlFriend.TABLE_NAME, null);
                friendDbDTOs = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            int friendId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlFriend.FRIEND_ID));
                            String name = cursor.getString(cursor.getColumnIndex(SqlContract.SqlFriend.FRIEND_NAME));
                            String contact = cursor.getString(cursor.getColumnIndex(SqlContract.SqlFriend.FRIEND_CONTACT_NO));
                            String email = cursor.getString(cursor.getColumnIndex(SqlContract.SqlFriend.FRIEND_EMAIL));
                            String image = cursor.getString(cursor.getColumnIndex(SqlContract.SqlFriend.FRIEND_PHOTO));
                            FriendDbDTO friendsDT = new FriendDbDTO(friendId, name, contact, email, image);
                            friendDbDTOs.add(friendsDT);

                        } while (cursor.moveToNext());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return friendDbDTOs;
    }
}

