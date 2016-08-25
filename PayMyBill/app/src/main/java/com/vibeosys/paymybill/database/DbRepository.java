package com.vibeosys.paymybill.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.vibeosys.paymybill.data.BillDetailsDTO;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendBillTransaction;
import com.vibeosys.paymybill.data.FriendTransactions.FriendBills;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.data.HistoryDTO;
import com.vibeosys.paymybill.data.Sync;
import com.vibeosys.paymybill.data.databasedto.FriendDbDTO;
import com.vibeosys.paymybill.data.databasedto.UserRegisterDbDTO;
import com.vibeosys.paymybill.util.AppConstants;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 24/05/2016.
 */

public class DbRepository extends SQLiteOpenHelper {

    private final String TAG = DbRepository.class.getSimpleName();
    private static int DATABASE_VERSION_NUMBER = 1;
    private final String BILL_TYPE = "CREATE TABLE BillType (BillTypeId INTEGER," +
            "BillTypeName TEXT UNIQUE,PRIMARY KEY(BillTypeId));";

    private final String CREATE_CURRENCY = "CREATE TABLE CurrencyType (CurrencyTypeId INTEGER," +
            "CurrencyTypeName TEXT UNIQUE," +
            "CurrencySymbol TEXT UNIQUE,PRIMARY KEY(CurrencyTypeId));";

    private final String LOGIN_SOURCE_TYPE = "CREATE TABLE LoginSourceType (LoginSourceId INTEGER," +
            "LoginSourceName TEXT UNIQUE,PRIMARY KEY(LoginSourceId));";

    private final String CREATE_USER_TABLE = "CREATE TABLE UserData (UserId INTEGER," +
            "UserEmail TEXT UNIQUE,Pwd TEXT,FirstName TEXT," +
            "LastName TEXT,Phone TEXT,LoginSource INTEGER NOT NULL," +
            "FbApiToken TEXT,PhotoUrl TEXT,PRIMARY KEY(UserId)," +
            "FOREIGN KEY(`LoginSource`) REFERENCES LoginSourceType(LoginSourceId));";


    private final String CREATE_FRIEND = "CREATE TABLE Friend(FriendId INTEGER," +
            "Name TEXT,ContactNo TEXT," +
            "emailId TEXT UNIQUE,ImageUrl TEXT,PRIMARY KEY(FriendId));";

    private final String CREATE_BILL = "CREATE TABLE Bill(BillId INTEGER," +
            "BillNo INTEGER NOT NULL UNIQUE,BillDate TEXT,BillAmount NUMERIC," +
            "BillDesc TEXT,BillTypeId INTEGER,CurrencyId INTEGER,PaidBy INTEGER," +
            "PRIMARY KEY(BillId),FOREIGN KEY(BillTypeId) REFERENCES BillType(BillTypeId)," +
            "FOREIGN KEY(CurrencyId) REFERENCES CurrencyType(CurrencyTypeId)," +
            "FOREIGN KEY(PaidBy) REFERENCES Friend(FriendId));";

    private final String CREATE_TRANSACTION = "CREATE TABLE TransactionDetails(TransactionId INTEGER," +
            "BillId INTEGER,PersonId INTEGER,CreditAmt TEXT,DebitAmt TEXT," +
            "Desc TEXT,TransactionDate TEXT,IsSettle INTEGER DEFAULT 0,PRIMARY KEY(TransactionId)," +
            "FOREIGN KEY(BillId) REFERENCES Bill(BillId),FOREIGN KEY(PersonId) " +
            "REFERENCES Friend(FriendId));";

    public DbRepository(Context context) {
        super(context, SqlContract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(BILL_TYPE);
        } catch (SQLiteException e) {
            Log.d(TAG, "Bill type" + BILL_TYPE);
        }
        try {
            db.execSQL(CREATE_CURRENCY);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create Currency" + CREATE_CURRENCY);
        }
        try {
            db.execSQL(LOGIN_SOURCE_TYPE);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create LoginSourceType" + LOGIN_SOURCE_TYPE);
        }
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
            db.execSQL(CREATE_BILL);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create Bill" + CREATE_BILL);
        }
        try {
            db.execSQL(CREATE_TRANSACTION);
        } catch (SQLiteException e) {
            Log.d(TAG, "Create Transaction" + CREATE_TRANSACTION);
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

    public int insertFriend(FriendDbDTO friendDbDTO) {
        // boolean flagError = false;
        int flagError;
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
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlFriend.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Friend is Added Successfully");
                    // flagError = true;
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Friend record already present");
                    // flagError = true;
                    flagError = 2;
                }

            }
        } catch (Exception e) {
            // flagError = false;
            flagError = 3;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Friend " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            /*if (!flagError)
                Log.e(TAG, "##Insert Friend" + errorMessage);*/
        }
        return flagError;
    }

    public ArrayList<FriendsDTO> getFriendList() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FriendsDTO> friendDbDTOs = null;
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
                            FriendsDTO friendsDT = new FriendsDTO(friendId, name, image, 0, false);
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

    public int insertBill(BillDetailsDTO billDetailsDTO) {
        int flagError;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long billId = 0;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            billId = getLastId(sqLiteDatabase, SqlContract.SqlBill.BILL_ID, SqlContract.SqlBill.TABLE_NAME);
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlBill.BILL_ID, billId);
                contentValues.put(SqlContract.SqlBill.BILL_NO, billDetailsDTO.getBillNo());
                contentValues.put(SqlContract.SqlBill.BILL_DATE, billDetailsDTO.getBillDate());
                contentValues.put(SqlContract.SqlBill.BILL_AMOUNT, billDetailsDTO.getAmount());
                contentValues.put(SqlContract.SqlBill.BILL_DESC, billDetailsDTO.getDescription());
                contentValues.put(SqlContract.SqlBill.BILL_TYPE_ID, billDetailsDTO.getTypeId());
                contentValues.put(SqlContract.SqlBill.BILL_CURRENCY_ID, billDetailsDTO.getCurrencyId());
                contentValues.put(SqlContract.SqlBill.BILL_PAID_ID, billDetailsDTO.getPaidBy());
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlBill.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Bill is Added Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Bill record already present");
                    flagError = 2;
                }

            }
        } catch (Exception e) {
            flagError = 3;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Bill " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        if (flagError == 1) {
            flagError = insertBillTransaction(billId, billDetailsDTO);
        }
        return flagError;
    }

    public int insertBillTransaction(long billId, BillDetailsDTO billDetailsDTO) {
        int flagError = 0;
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        FriendsDTO paidByFriend = null;
        long count = -1;
        try {
            for (FriendsDTO friendsDTO : billDetailsDTO.getShareWith()) {
                if (friendsDTO.getId() == billDetailsDTO.getPaidBy()) {
                    paidByFriend = friendsDTO;
                }
                sqLiteDatabase = null;
                sqLiteDatabase = getWritableDatabase();
                synchronized (sqLiteDatabase) {
                    contentValues = new ContentValues();
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_ID, getLastId
                            (sqLiteDatabase, SqlContract.SqlTransaction.TRANSACTION_ID, SqlContract.SqlTransaction.TABLE_NAME));
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_BILL_ID, billId);
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_PERSON_ID, friendsDTO.getId());
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_CREDIT_AMOUNT, 0);
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DEBIT_AMOUNT, friendsDTO.getAmount());
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DESCRIPTION, billDetailsDTO.getDescription());
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DATE, billDetailsDTO.getBillDate());
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_IS_SETTLE, 0);
                    if (!sqLiteDatabase.isOpen())
                        sqLiteDatabase = getWritableDatabase();
                    try {
                        count = sqLiteDatabase.insertOrThrow(SqlContract.SqlTransaction.TABLE_NAME, null, contentValues);
                        contentValues.clear();
                        Log.d(TAG, "## Transaction is Added Successfully");
                        flagError = 1;
                    } catch (SQLiteConstraintException e) {
                        Log.d(TAG, "## Transaction is already present");
                        flagError = 2;
                    } finally {
                        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                            sqLiteDatabase.close();
                    }
                }
            }
        } catch (Exception e) {
            flagError = 3;
            Log.e(TAG, "##Error while insert Transaction " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        if (flagError == 1) {
            flagError = insertUserTransaction(billDetailsDTO, billId, paidByFriend);
        }
        return flagError;
    }

    public int insertUserTransaction(BillDetailsDTO billDetailsDTO, long billId, FriendsDTO paidByFriend) {
        int flagError = 0;
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_ID, getLastId
                        (sqLiteDatabase, SqlContract.SqlTransaction.TRANSACTION_ID, SqlContract.SqlTransaction.TABLE_NAME));
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_BILL_ID, billId);
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_PERSON_ID, billDetailsDTO.getPaidBy());
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_CREDIT_AMOUNT, paidByFriend.getAmount());
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DEBIT_AMOUNT, 0);
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DESCRIPTION, billDetailsDTO.getDescription());
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DATE, billDetailsDTO.getBillDate());
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_IS_SETTLE, 0);
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlTransaction.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Transaction is Added Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Transaction is already present");
                    flagError = 2;
                }
            }
        } catch (Exception e) {
            flagError = 3;
            Log.e(TAG, "##Error while insert Transaction " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return flagError;
    }

    public int userRegister(UserRegisterDbDTO userRegisterDbDTO) {
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        int returnVal;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlRegisterUser.USER_EMAIL_ID, userRegisterDbDTO.getUserEmailId());
                contentValues.put(SqlContract.SqlRegisterUser.USER_PASSWORD, userRegisterDbDTO.getUserPassword());
                contentValues.put(SqlContract.SqlRegisterUser.USER_FIRST_NAME, userRegisterDbDTO.getFirstName());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LAST_NAME, userRegisterDbDTO.getLastName());
                contentValues.put(SqlContract.SqlRegisterUser.USER_PHONE, userRegisterDbDTO.getPhoneNumber());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LOGIN_SOURCE, userRegisterDbDTO.getLoginSource());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LOGIN_SOURCE_KEY, userRegisterDbDTO.getFbTokenId());
                contentValues.put(SqlContract.SqlRegisterUser.USER_IMAGE_URL, userRegisterDbDTO.getPhotoUrl());

                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlRegisterUser.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    returnVal = 1;

                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "User is already register");
                    returnVal = 2;
                } catch (SQLiteException e) {
                    Log.d(TAG, "SQL Exception in UserData Table");
                    returnVal = 3;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Problem while inserting user");
            returnVal = 3;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return returnVal;
    }

    public boolean deleteBillAndTransactions(BillDetailsDTO billDetailsDTO) {
        boolean deleteFlag = false;
        long billId = getBillId(billDetailsDTO.getBillNo());
        boolean deleteTransaction = deleteTransaction(billId);
        boolean deleteBill = deleteBill(billId);
        if (deleteTransaction && deleteBill) {
            deleteFlag = true;
        } else {
            deleteFlag = false;
        }
        return deleteFlag;
    }

    public long getBillId(long billNo) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        long billId = 0;
        try {
            String[] whereClause = new String[]{String.valueOf(billNo)};
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT " + SqlContract.SqlBill.BILL_ID
                        + " From " + SqlContract.SqlBill.TABLE_NAME + " WHERE " + SqlContract.SqlBill.BILL_NO
                        + "=?", whereClause);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        billId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_ID));
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
        return billId;
    }

    public boolean deleteTransaction(long billId) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                String[] whereClause = new String[]{String.valueOf(billId)};
                count = sqLiteDatabase.delete(SqlContract.SqlTransaction.TABLE_NAME, SqlContract.SqlTransaction.TRANSACTION_BILL_ID + "=?", whereClause);
                Log.d(TAG, " ## delete Transactions data successfully");

            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while delete Transactions " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##delete Transactions by Bill" + errorMessage);
        }
        return flagError;
    }

    public boolean deleteBill(long billId) {
        boolean flagError = false;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                String[] whereClause = new String[]{String.valueOf(billId)};
                count = sqLiteDatabase.delete(SqlContract.SqlBill.TABLE_NAME, SqlContract.SqlBill.BILL_ID + "=?", whereClause);
                Log.d(TAG, " ## delete Bill successfully");

            }
        } catch (Exception e) {
            flagError = false;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while delete Bill " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
            if (!flagError)
                Log.e(TAG, "##delete Bill" + errorMessage);
        }
        return flagError;
    }

    public int CheckUserRegistration(String userName, String password) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        long count = -1;
        int returnVal = -2;
        int countVal;
        String Password;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                try {
                    cursor = sqLiteDatabase.rawQuery("select " + SqlContract.SqlRegisterUser.USER_PASSWORD + " from " + SqlContract.SqlRegisterUser.TABLE_NAME
                            + " where " + SqlContract.SqlRegisterUser.USER_EMAIL_ID + "=?", new String[]{userName});
                    countVal = cursor.getCount();
                    if (countVal > 0) {
                        cursor.moveToFirst();
                        do {
                            Password = cursor.getString(cursor.getColumnIndex(SqlContract.SqlRegisterUser.USER_PASSWORD));
                        } while (cursor.moveToNext());
                        if (TextUtils.equals(password, Password)) {
                            returnVal = 1;
                            return returnVal;
                        } else if (!TextUtils.equals(password, Password)) {
                            returnVal = 2;
                            return returnVal;

                        }
                    } else if (countVal == 0) {
                        returnVal = 3;
                        return returnVal;
                    }


                } catch (SQLiteException e) {
                    Log.d(TAG, "user Login problem");
                    returnVal = 4;
                    return returnVal;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "user Registration error");
            returnVal = 5;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return returnVal;
    }

    public int userRegisterSocialMedia(UserRegisterDbDTO userRegisterDbDTO) {
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        int returnVal;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlRegisterUser.USER_EMAIL_ID, userRegisterDbDTO.getUserEmailId());
                contentValues.put(SqlContract.SqlRegisterUser.USER_PASSWORD, userRegisterDbDTO.getUserPassword());
                contentValues.put(SqlContract.SqlRegisterUser.USER_FIRST_NAME, userRegisterDbDTO.getFirstName());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LAST_NAME, userRegisterDbDTO.getLastName());
                contentValues.put(SqlContract.SqlRegisterUser.USER_PHONE, userRegisterDbDTO.getPhoneNumber());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LOGIN_SOURCE, userRegisterDbDTO.getLoginSource());
                contentValues.put(SqlContract.SqlRegisterUser.USER_LOGIN_SOURCE_KEY, userRegisterDbDTO.getFbTokenId());
                contentValues.put(SqlContract.SqlRegisterUser.USER_IMAGE_URL, userRegisterDbDTO.getPhotoUrl());

                try {
                    count = sqLiteDatabase.insertWithOnConflict(SqlContract.SqlRegisterUser.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    contentValues.clear();
                    returnVal = 1;

                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "User is already register");
                    returnVal = 2;
                } catch (SQLiteException e) {
                    Log.d(TAG, "SQL Exception in UserData Table");
                    returnVal = 3;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Problem while inserting user");
            returnVal = 3;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return returnVal;
    }

    public long addFirstFriend(FriendDbDTO friendDbDTO) {
        long returnVal = 0;
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        Cursor cursor = null;
        long count = -1;
        String query = "select FriendId from Friend order by ROWID DESC limit 1";
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlFriend.FRIEND_NAME, friendDbDTO.getName());
                contentValues.put(SqlContract.SqlFriend.FRIEND_CONTACT_NO, friendDbDTO.getContact());
                contentValues.put(SqlContract.SqlFriend.FRIEND_EMAIL, friendDbDTO.getEmail());
                contentValues.put(SqlContract.SqlFriend.FRIEND_PHOTO, friendDbDTO.getImage());

                try {
                    count = sqLiteDatabase.insertWithOnConflict(SqlContract.SqlFriend.TABLE_NAME,
                            null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    contentValues.clear();
                    if (count > 0) {
                        cursor = sqLiteDatabase.rawQuery(query, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            long Id = cursor.getLong(0);
                            returnVal = Id;
                        }
                    }
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "Error while inserting first friend record");
                } catch (SQLiteException e) {
                    Log.d(TAG, "Error while inserting first friend record");
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Problem while inserting first friend record");
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return returnVal;
    }

    public boolean deleteAllUserFriendRecords() {

        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                try {
                    sqLiteDatabase.delete(SqlContract.SqlFriend.TABLE_NAME, null, null);
                    sqLiteDatabase.close();
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "Sql deleting friends record");
                    return false;
                } catch (SQLiteException e) {
                    Log.d(TAG, "Sql deleting friends record");
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while deleting friend records");
            return false;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return true;
    }

    /**
     * For main screen function
     **/
    public ArrayList<FriendTransactions> getFriendListTransaction() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FriendTransactions> friendDbDTOs = null;
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
                            FriendTransactions friendsDT = new FriendTransactions(friendId, name, image);
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

    public ArrayList<FriendBills> getBills(int userId, int friendId) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FriendBills> bills = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                String[] whereClause = new String[]{String.valueOf(userId), String.valueOf(friendId)};
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlBill.TABLE_NAME + " WHERE "
                        + SqlContract.SqlBill.BILL_PAID_ID + "=? OR " + SqlContract.SqlBill.BILL_PAID_ID + "=?", whereClause);
                bills = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            int billId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_ID));
                            String billDesc = cursor.getString(cursor.getColumnIndex(SqlContract.SqlBill.BILL_DESC));
                            String date = cursor.getString(cursor.getColumnIndex(SqlContract.SqlBill.BILL_DATE));
                            double amount = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlBill.BILL_AMOUNT));
                            int paidBy = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_PAID_ID));
                            int billType = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_TYPE_ID));
                            FriendBills friendsDT = new FriendBills(billId, billDesc, date, amount, paidBy, billType);
                            bills.add(friendsDT);

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
        return bills;
    }

    public ArrayList<FriendBillTransaction> getTransactionByBill(long billId, int userId, int friendId) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FriendBillTransaction> transactions = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                String[] whereClause = new String[]{String.valueOf(billId), String.valueOf(userId), String.valueOf(friendId)};
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlTransaction.TABLE_NAME + " WHERE "
                        + SqlContract.SqlTransaction.TRANSACTION_BILL_ID + "=? AND ("
                        + SqlContract.SqlTransaction.TRANSACTION_PERSON_ID + "=? OR "
                        + SqlContract.SqlTransaction.TRANSACTION_PERSON_ID + "=?)", whereClause);
                transactions = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            int transactionId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlTransaction.TRANSACTION_ID));
                            int personId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlTransaction.TRANSACTION_PERSON_ID));
                            double creditAmount = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTransaction.TRANSACTION_CREDIT_AMOUNT));
                            double debitAmount = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlTransaction.TRANSACTION_DEBIT_AMOUNT));
                            String transactionDate = cursor.getString(cursor.getColumnIndex(SqlContract.SqlTransaction.TRANSACTION_DATE));
                            FriendBillTransaction friendsDT = new FriendBillTransaction(transactionId, personId, creditAmount, debitAmount, transactionDate);
                            transactions.add(friendsDT);

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
        return transactions;
    }

    public String getUserProfileImage(String userEmailId) {
        String mImageUri = "";
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        int countVal;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                try {
                    cursor = sqLiteDatabase.rawQuery("select " + SqlContract.SqlRegisterUser.USER_IMAGE_URL
                            + " from " + SqlContract.SqlRegisterUser.TABLE_NAME + " where "
                            + SqlContract.SqlRegisterUser.USER_EMAIL_ID + "=?", new String[]{userEmailId});
                    countVal = cursor.getCount();
                    if (countVal > 0) {
                        cursor.moveToFirst();
                        do {
                            mImageUri = cursor.getString(cursor.getColumnIndex(SqlContract.SqlRegisterUser.USER_IMAGE_URL));
                        } while (cursor.moveToNext());
                    }

                } catch (SQLiteException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }

        }
        return mImageUri;
    }

    public int settleAllBills(FriendTransactions friendTransactions, int userId) {
        int flagError;
        flagError = insertSettleBill(friendTransactions, userId);
        if (flagError == 1) {
            ArrayList<FriendBills> bills = friendTransactions.getBills();
            for (FriendBills bill : bills) {
                ArrayList<FriendBillTransaction> billTransactions = bill.getTransaction();
                for (FriendBillTransaction friendBillTransaction : billTransactions) {
                    flagError = updateSettleTransaction(friendBillTransaction);
                }
            }
        }
        return flagError;
    }


    private int insertSettleBill(FriendTransactions friendTransactions, int userId) {
        int flagError;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long billId = 0;
        long count = -1;
        DateUtils dateUtils = new DateUtils();
        Date d = new Date();
        String date = dateUtils.getLocalDateInReadableFormat(d);
        try {
            sqLiteDatabase = getWritableDatabase();
            billId = getLastId(sqLiteDatabase, SqlContract.SqlBill.BILL_ID, SqlContract.SqlBill.TABLE_NAME);
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlBill.BILL_ID, billId);
                contentValues.put(SqlContract.SqlBill.BILL_NO, d.getTime());
                contentValues.put(SqlContract.SqlBill.BILL_DATE, date);
                double amount = friendTransactions.getAmount();
                if (amount < 0) {
                    contentValues.put(SqlContract.SqlBill.BILL_AMOUNT, -(amount));
                } else if (amount > 0) {
                    contentValues.put(SqlContract.SqlBill.BILL_AMOUNT, amount);
                }

                contentValues.put(SqlContract.SqlBill.BILL_DESC, "Settlement of bills");
                contentValues.put(SqlContract.SqlBill.BILL_TYPE_ID, AppConstants.BILL_TYPE_SETTLEMENT);
                contentValues.put(SqlContract.SqlBill.BILL_CURRENCY_ID, 0);
                int paidBy = 0;
                if (friendTransactions.getType() == BorrowType.OWES_YOU) {
                    paidBy = userId;//Replace 1 with for user
                } else if (friendTransactions.getType() == BorrowType.YOU_OWE) {
                    paidBy = friendTransactions.getFriendId();
                }
                contentValues.put(SqlContract.SqlBill.BILL_PAID_ID, paidBy);
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlBill.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Settle Bill is Added Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Settle Bill record already present");
                    flagError = 2;
                }

            }
        } catch (Exception e) {
            flagError = 3;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Settle Bill " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        if (flagError == 1) {
            flagError = insertSettleTransaction(billId, friendTransactions);
        }
        return flagError;
    }

    public int insertSettleTransaction(long billId, FriendTransactions friendTransactions) {
        int flagError = 0;
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        DateUtils dateUtils = new DateUtils();
        Date d = new Date();
        String date = dateUtils.getLocalDateInReadableFormat(d);
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_ID, getLastId
                        (sqLiteDatabase, SqlContract.SqlTransaction.TRANSACTION_ID, SqlContract.SqlTransaction.TABLE_NAME));
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_BILL_ID, billId);
                int personId = 0;
                if (friendTransactions.getType() == BorrowType.OWES_YOU) {
                    personId = friendTransactions.getFriendId();
                } else if (friendTransactions.getType() == BorrowType.YOU_OWE) {
                    personId = 1;//Replace 1 with for user
                }
                double amount = friendTransactions.getAmount();
                if (amount < 0) {
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_CREDIT_AMOUNT, -(amount));
                } else if (amount > 0) {
                    contentValues.put(SqlContract.SqlTransaction.TRANSACTION_CREDIT_AMOUNT, amount);
                }
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_PERSON_ID, personId);
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DEBIT_AMOUNT, 0);
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DESCRIPTION, "Settlement");
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_DATE, date);
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_IS_SETTLE, 1);
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlTransaction.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Transaction settle is Added Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Transaction settle is already present");
                    flagError = 2;
                }
            }
        } catch (Exception e) {
            flagError = 3;
            Log.e(TAG, "##Error while insert Transaction settle " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return flagError;
    }

    private int updateSettleTransaction(FriendBillTransaction friendBillTransaction) {
        int flagError;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            synchronized (sqLiteDatabase) {
                String[] whereClause = new String[]{String.valueOf(friendBillTransaction.getTransactionId())};
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlTransaction.TRANSACTION_IS_SETTLE, 1);
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.update(SqlContract.SqlTransaction.TABLE_NAME, contentValues,
                            SqlContract.SqlTransaction.TRANSACTION_ID + "=?", whereClause);
                    contentValues.clear();
                    Log.d(TAG, "## Transaction Updated Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Transaction not Updated");
                    flagError = 2;
                }

            }
        } catch (Exception e) {
            flagError = 3;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while update Settle transaction " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return flagError;
    }

    public int insertMyExpances(BillDetailsDTO billDetailsDTO) {
        int flagError;
        String errorMessage = "";
        SQLiteDatabase sqLiteDatabase = null;
        ContentValues contentValues = null;
        long billId = 0;
        long count = -1;
        try {
            sqLiteDatabase = getWritableDatabase();
            billId = getLastId(sqLiteDatabase, SqlContract.SqlBill.BILL_ID, SqlContract.SqlBill.TABLE_NAME);
            synchronized (sqLiteDatabase) {
                contentValues = new ContentValues();
                contentValues.put(SqlContract.SqlBill.BILL_ID, billId);
                contentValues.put(SqlContract.SqlBill.BILL_NO, billDetailsDTO.getBillNo());
                contentValues.put(SqlContract.SqlBill.BILL_DATE, billDetailsDTO.getBillDate());
                contentValues.put(SqlContract.SqlBill.BILL_AMOUNT, billDetailsDTO.getAmount());
                contentValues.put(SqlContract.SqlBill.BILL_DESC, billDetailsDTO.getDescription());
                contentValues.put(SqlContract.SqlBill.BILL_TYPE_ID, billDetailsDTO.getTypeId());
                contentValues.put(SqlContract.SqlBill.BILL_CURRENCY_ID, billDetailsDTO.getCurrencyId());
                contentValues.put(SqlContract.SqlBill.BILL_PAID_ID, billDetailsDTO.getPaidBy());
                if (!sqLiteDatabase.isOpen())
                    sqLiteDatabase = getWritableDatabase();
                try {
                    count = sqLiteDatabase.insertOrThrow(SqlContract.SqlBill.TABLE_NAME, null, contentValues);
                    contentValues.clear();
                    Log.d(TAG, "## Expenses is Added Successfully");
                    flagError = 1;
                } catch (SQLiteConstraintException e) {
                    Log.d(TAG, "## Bill record already present");
                    flagError = 2;
                }

            }
        } catch (Exception e) {
            flagError = 3;
            errorMessage = e.getMessage();
            Log.e(TAG, "##Error while insert Bill " + e.toString());
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return flagError;
    }

    public ArrayList<HistoryDTO> getExpensesList() {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<HistoryDTO> bills = null;
        try {
            sqLiteDatabase = getReadableDatabase();
            synchronized (sqLiteDatabase) {
                cursor = sqLiteDatabase.rawQuery("SELECT * From " + SqlContract.SqlBill.TABLE_NAME + " WHERE "
                        + SqlContract.SqlBill.BILL_TYPE_ID + "=" + AppConstants.BILL_TYPE_EXPANSES, null);
                bills = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        do {
                            int billId = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_ID));
                            String billDesc = cursor.getString(cursor.getColumnIndex(SqlContract.SqlBill.BILL_DESC));
                            String date = cursor.getString(cursor.getColumnIndex(SqlContract.SqlBill.BILL_DATE));
                            double amount = cursor.getDouble(cursor.getColumnIndex(SqlContract.SqlBill.BILL_AMOUNT));
                            int paidBy = cursor.getInt(cursor.getColumnIndex(SqlContract.SqlBill.BILL_PAID_ID));
                            HistoryDTO billHostory = new HistoryDTO(billId, billDesc, date, amount);
                            bills.add(billHostory);

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
        return bills;
    }
}

