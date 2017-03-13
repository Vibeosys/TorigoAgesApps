package com.vibeosys.fitnessapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by akshay on 04-03-2017.
 */
public class FitnessProvider extends ContentProvider {

    private DbRepository mDbRepository;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int USER = 100;
    static final int BMI_RANGE = 200;
    static final int WORKOUT_MASTER = 300;
    static final int SETS_MASTER = 400;
    static final int DAILY_WORKOUT = 500;
    static final int SETS_REPETITION = 600;
    static final int WORKOUT_CATEGORY = 700;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FitnessContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, FitnessContract.PATH_USER_LOGIN, USER);
        matcher.addURI(authority, FitnessContract.PATH_BMI_RANGE, BMI_RANGE);
        matcher.addURI(authority, FitnessContract.PATH_WORKOUT_MASTER, WORKOUT_MASTER);
        matcher.addURI(authority, FitnessContract.PATH_SETS_MASTER, SETS_MASTER);
        matcher.addURI(authority, FitnessContract.PATH_DAILY_WORKOUT, DAILY_WORKOUT);
        matcher.addURI(authority, FitnessContract.PATH_SETS_REPETITION, SETS_REPETITION);
        matcher.addURI(authority, FitnessContract.PATH_WORK_CATEGORY, WORKOUT_CATEGORY);

        return matcher;
    }

    public FitnessProvider() {
    }

    @Override
    public String getType(Uri uri) {

        final int math = sUriMatcher.match(uri);

        switch (math) {

            case USER:
                return FitnessContract.UserLogin.CONTENT_TYPE;
            case BMI_RANGE:
                return FitnessContract.BMIRange.CONTENT_TYPE;
            case WORKOUT_MASTER:
                return FitnessContract.WorkOutMaster.CONTENT_TYPE;
            case SETS_MASTER:
                return FitnessContract.SetsMaster.CONTENT_TYPE;
            case DAILY_WORKOUT:
                return FitnessContract.DailyWorkout.CONTENT_TYPE;
            case SETS_REPETITION:
                return FitnessContract.SetsRepetition.CONTENT_TYPE;
            case WORKOUT_CATEGORY:
                return FitnessContract.WorkCategory.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri in getType: " + uri);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case USER: {
                long returnId = db.insert(FitnessContract.UserLogin.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.UserLogin.userLoginUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.UserLogin.userLoginUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert user into" + uri);

                }
                break;
            }
            case BMI_RANGE: {
                long returnId = db.insert(FitnessContract.BMIRange.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.BMIRange.bmiRangeUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.BMIRange.bmiRangeUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert Bmi range into" + uri);

                }
                break;
            }
            case WORKOUT_MASTER: {
                long returnId = db.insert(FitnessContract.WorkOutMaster.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.WorkOutMaster.workoutMasterUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.WorkOutMaster.workoutMasterUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert Workout master into" + uri);

                }
                break;
            }
            case SETS_MASTER: {
                long returnId = db.insert(FitnessContract.SetsMaster.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.SetsMaster.setMasterUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.SetsMaster.setMasterUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert Sets master into" + uri);

                }
                break;
            }
            case DAILY_WORKOUT: {
                long returnId = db.insert(FitnessContract.DailyWorkout.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.DailyWorkout.dailyWorkoutUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.DailyWorkout.dailyWorkoutUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert Daily workout into" + uri);

                }
                break;
            }
            case SETS_REPETITION: {
                long returnId = db.insert(FitnessContract.SetsRepetition.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.SetsRepetition.setsRepUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.SetsRepetition.setsRepUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert Sets repetition into" + uri);
                }
                break;
            }
            case WORKOUT_CATEGORY: {
                long returnId = db.insert(FitnessContract.WorkCategory.TABLE_NAME, null, values);
                if (returnId > 0) {
                    returnUri = FitnessContract.WorkCategory.categoryUri(returnId);
                } else if (returnId == -1) {
                    returnUri = FitnessContract.WorkCategory.categoryUri(returnId);
                } else {
                    Log.d("TAG", "TAG");
                    throw new android.database.SQLException("Fail to insert category into" + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri in insert: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch (match) {

            case USER:
                rowDeleted = db.delete(FitnessContract.UserLogin.TABLE_NAME, selection, selectionArgs);
                break;
            case BMI_RANGE:
                rowDeleted = db.delete(FitnessContract.BMIRange.TABLE_NAME, selection, selectionArgs);
                break;
            case WORKOUT_MASTER:
                rowDeleted = db.delete(FitnessContract.WorkOutMaster.TABLE_NAME, selection, selectionArgs);
                break;
            case SETS_MASTER:
                rowDeleted = db.delete(FitnessContract.SetsMaster.TABLE_NAME, selection, selectionArgs);
                break;
            case DAILY_WORKOUT:
                rowDeleted = db.delete(FitnessContract.DailyWorkout.TABLE_NAME, selection, selectionArgs);
                break;
            case SETS_REPETITION:
                rowDeleted = db.delete(FitnessContract.SetsRepetition.TABLE_NAME, selection, selectionArgs);
                break;
            case WORKOUT_CATEGORY:
                rowDeleted = db.delete(FitnessContract.WorkCategory.TABLE_NAME, selection, selectionArgs);
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

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case USER: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.UserLogin.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case BMI_RANGE: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.BMIRange.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case WORKOUT_MASTER: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.WorkOutMaster.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case SETS_MASTER: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.SetsMaster.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case DAILY_WORKOUT: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.DailyWorkout.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case SETS_REPETITION: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.SetsRepetition.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            case WORKOUT_CATEGORY: {
                retCursor = mDbRepository.getReadableDatabase().query(
                        FitnessContract.WorkCategory.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mDbRepository.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowUpdated;
        switch (match) {
            case USER:
                rowUpdated = db.update(FitnessContract.UserLogin.TABLE_NAME, values, selection, selectionArgs);
                break;
            case BMI_RANGE:
                rowUpdated = db.update(FitnessContract.BMIRange.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WORKOUT_MASTER:
                rowUpdated = db.update(FitnessContract.WorkOutMaster.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SETS_MASTER:
                rowUpdated = db.update(FitnessContract.SetsMaster.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DAILY_WORKOUT:
                rowUpdated = db.update(FitnessContract.DailyWorkout.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SETS_REPETITION:
                rowUpdated = db.update(FitnessContract.SetsRepetition.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WORKOUT_CATEGORY:
                rowUpdated = db.update(FitnessContract.WorkCategory.TABLE_NAME, values, selection, selectionArgs);
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
