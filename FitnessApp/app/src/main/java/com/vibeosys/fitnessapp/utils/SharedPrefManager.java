package com.vibeosys.fitnessapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akshay on 10-03-2017.
 */
public class SharedPrefManager {

    private static final String PROJECT_PREFERENCE = "com.vibeosys.fitness";
    private static SharedPrefManager sharedPrefManager;
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;

    public static SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager != null)
            return sharedPrefManager;

        mContext = context;
        sharedPrefManager = new SharedPrefManager();
        loadProjectSharedPreferences();
        return sharedPrefManager;
    }

    private static void loadProjectSharedPreferences() {
        if (mProjectSharedPref == null) {
            mProjectSharedPref = mContext.getSharedPreferences(PROJECT_PREFERENCE, Context.MODE_PRIVATE);
        }
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, long sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putLong(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, int sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putInt(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    public long getWorkId() {
        return mProjectSharedPref.getLong(SharedPrefKeyConstant.WORK_ID, 0);
    }

    public void setWorkId(long workId) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.WORK_ID, workId);
    }

    public long getLastDate() {
        return mProjectSharedPref.getLong(SharedPrefKeyConstant.WORK_DATE, 0);
    }

    public void setLastDate(long lastDate) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.WORK_DATE, lastDate);
    }

    public long getSetId() {
        return mProjectSharedPref.getLong(SharedPrefKeyConstant.SET_ID, 0);
    }

    public void setSetId(long setId) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.SET_ID, setId);
    }

    public long getDailyWorkId() {
        return mProjectSharedPref.getLong(SharedPrefKeyConstant.DAYILY_WORK_ID, 0);
    }

    public void setDailyWorkId(long dailyWorkId) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.DAYILY_WORK_ID, dailyWorkId);
    }
}
