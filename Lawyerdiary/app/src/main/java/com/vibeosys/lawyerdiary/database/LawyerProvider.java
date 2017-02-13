package com.vibeosys.lawyerdiary.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
/**
 * Created by Vibeosys software on 09-09-2016.
 */

/**
 * The content provider class that can use to fire the queries,fetch the data from the database
 * Create the joins and the relations of the table.
 */
public class LawyerProvider extends ContentProvider {

    private DbRepository mDbRepository;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int CLIENT = 100;
    static final int CLIENT_MATCH_PH_NO = 101;
    static final int CASE = 200;
    static final int CASE_DETAILS = 201;
    static final int DOCUMENT = 300;
    static final int REMINDER = 400;
    static final int USER_LOGIN = 500;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = LawyerContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, LawyerContract.PATH_CLIENT, CLIENT);
        matcher.addURI(authority, LawyerContract.PATH_CASE, CASE);
        matcher.addURI(authority, LawyerContract.PATH_DOCUMENT, DOCUMENT);
        matcher.addURI(authority, LawyerContract.PATH_REMINDER, REMINDER);
        matcher.addURI(authority, LawyerContract.PATH_CLIENT + "/*", CLIENT_MATCH_PH_NO);
        matcher.addURI(authority, LawyerContract.PATH_CASE + "/#", CASE_DETAILS);
        matcher.addURI(authority, LawyerContract.PATH_USER_LOGIN, USER_LOGIN);

        return matcher;
    }

    private static final SQLiteQueryBuilder sCaseDetailsQueryBuilder;

    static {
        sCaseDetailsQueryBuilder = new SQLiteQueryBuilder();
        //This is an inner join which looks like
        //case INNER JOIN client ON case.client_id = client._id
        sCaseDetailsQueryBuilder.setTables(
                LawyerContract.Case.TABLE_NAME + " INNER JOIN " +
                        LawyerContract.Client.TABLE_NAME +
                        " ON " + LawyerContract.Case.TABLE_NAME +
                        "." + LawyerContract.Case.CLIENT_ID +
                        " = " + LawyerContract.Client.TABLE_NAME +
                        "." + LawyerContract.Client._ID);
    }

    public LawyerProvider() {
    }

    @Override
    public String getType(Uri uri) {

        final int math = sUriMatcher.match(uri);

        switch (math) {

            case CLIENT:
                return LawyerContract.Client.CONTENT_TYPE;
            case CLIENT_MATCH_PH_NO:
                return LawyerContract.Client.CONTENT_ITEM_TYPE;
            case CASE:
                return LawyerContract.Case.CONTENT_TYPE;
            case CASE_DETAILS:
                return LawyerContract.Case.CONTENT_ITEM_TYPE;
            case DOCUMENT:
                return LawyerContract.Document.CONTENT_TYPE;
            case REMINDER:
                return LawyerContract.Reminder.CONTENT_TYPE;
            case USER_LOGIN:
                return LawyerContract.UserLogin.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri in getType: " + uri);
        }
    }

    /**
     * Insert the row in the application database in local
     *
     * @param uri    Uri link of the table
     * @param values Content Values are the values that want to store in the database
     * @return Uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case CLIENT: {
                long _id = db.insert(LawyerContract.Client.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = LawyerContract.Client.buildClientUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CASE: {
                long _id = db.insert(LawyerContract.Case.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = LawyerContract.Case.buildCaseUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DOCUMENT: {
                long _id = db.insert(LawyerContract.Document.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = LawyerContract.Document.buildDocumentUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REMINDER: {
                long _id = db.insert(LawyerContract.Reminder.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = LawyerContract.Reminder.buildReminderUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case USER_LOGIN: {
                long returnId = db.insert(LawyerContract.UserLogin.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = LawyerContract.UserLogin.userLoginUri(returnId);
                } else if (returnId == -1) {
                    returnUri = LawyerContract.UserLogin.userLoginUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert user login into" + uri);

                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri in insert: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * Delete the records from the database
     *
     * @param uri           URI of the table
     * @param selection     where clause if any put null to delete all the records
     * @param selectionArgs where clause arguments null if selection is null
     * @return int row id of the deleted record
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch (match) {
            case CLIENT:
                rowDeleted = db.delete(LawyerContract.Client.TABLE_NAME, selection, selectionArgs);
                break;
            case CASE:
                rowDeleted = db.delete(LawyerContract.Case.TABLE_NAME, selection, selectionArgs);
                break;
            case DOCUMENT:
                rowDeleted = db.delete(LawyerContract.Document.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDER:
                rowDeleted = db.delete(LawyerContract.Reminder.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_LOGIN:
                rowDeleted = db.delete(LawyerContract.UserLogin.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in delete: " + uri);
        }

        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public boolean onCreate() {
        mDbRepository = new DbRepository(getContext());
        return false;
    }

    /**
     * Fetch the records from the database.
     *
     * @param uri           Table Uri to identify the table
     * @param projection    Column names that is to get from the database
     * @param selection     Where clause to add the condition in the query. to get all the data set it null
     * @param selectionArgs Where clause arguments as per the selection. set null if selection is null
     * @param sortOrder     Sort order asc od desc to sort the data
     * @return Cursor which contains the data in table format
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CLIENT: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        LawyerContract.Client.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case CLIENT_MATCH_PH_NO: {
                retCursor = getClientOfPhoneNo(uri, projection, sortOrder);
                break;
            }
            case CASE: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        LawyerContract.Case.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case CASE_DETAILS: {
                retCursor = getCaseDetails(uri, projection, sortOrder);
                break;
            }
            case DOCUMENT: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        LawyerContract.Document.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case REMINDER: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        LawyerContract.Reminder.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case USER_LOGIN: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        LawyerContract.UserLogin.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getClientDetails(Uri uri, String[] projection, String sortOrder) {
        long clientId = LawyerContract.Client.getClientIdfromUri(uri);
        String[] selectionArgs = new String[]{String.valueOf(clientId)};
        String selection = LawyerContract.Client._ID + "=?";
        return mDbRepository.getReadableDatabase().query(LawyerContract.Client.TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getCaseDetails(Uri uri, String[] projection, String sortOrder) {
        long caseId = LawyerContract.Case.getCaseIdFromUri(uri);
        String[] selectionArgs = new String[]{String.valueOf(caseId)};
        String selection = LawyerContract.Case.TABLE_NAME + "." + LawyerContract.Case._ID + "=?";
        return sCaseDetailsQueryBuilder.query(mDbRepository.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getClientOfPhoneNo(Uri uri, String[] projection, String sortOrder) {
        String clientPhNo = LawyerContract.Client.getClientPhNoFromUri(uri);
        String[] selectionArgs = new String[]{clientPhNo};
        String selection = LawyerContract.Client.PH_NUMBER + "=?";
        return mDbRepository.getReadableDatabase().query(LawyerContract.Client.TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    /**
     * Update the case record which is selected
     *
     * @param uri           table uri to get the table
     * @param values        Content values column names and values that want to update
     * @param selection     Where clause to select the correct record to update
     * @param selectionArgs where clause arguments values of the where clause
     * @return int the record id which we update
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowUpdated;
        switch (match) {
            case CLIENT:
                rowUpdated = db.delete(LawyerContract.Client.TABLE_NAME, selection, selectionArgs);
                break;
            case CASE:
                rowUpdated = db.delete(LawyerContract.Case.TABLE_NAME, selection, selectionArgs);
                break;
            case DOCUMENT:
                rowUpdated = db.delete(LawyerContract.Document.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDER:
                rowUpdated = db.delete(LawyerContract.Reminder.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_LOGIN:
                rowUpdated = db.delete(LawyerContract.UserLogin.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri in update: " + uri);
        }

        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    @Override
    public void shutdown() {
        mDbRepository.close();
        super.shutdown();
    }
}
