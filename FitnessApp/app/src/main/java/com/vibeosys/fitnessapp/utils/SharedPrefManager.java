package com.vibeosys.fitnessapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    public static SharedPrefManager instance()
    {
        if(sharedPrefManager!=null)
        {
            return sharedPrefManager;
        }
        else {
            throw new IllegalArgumentException("No instance is yet created");
        }
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

    private static void setValuesInSharedPrefs(String sharedPrefKey, double sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putLong(sharedPrefKey, Double.doubleToLongBits(sharedPrefValue));
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

    public void setUserName(String userName) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_NAME_KEY, userName);
    }

    public String getUserName() {
        return mProjectSharedPref.getString(SharedPrefKeyConstant.USER_NAME_KEY, "");
    }

    public void setUserEmailId(String userEmailId) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_EMAIL_ID, userEmailId);
    }

    public String getUserEmailId() {
        return mProjectSharedPref.getString(SharedPrefKeyConstant.USER_EMAIL_ID, "");
    }

    public void setUserPassword(String userPassword) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_PASSWORD, userPassword);
    }

    public String getUserPassword() {
        return mProjectSharedPref.getString(SharedPrefKeyConstant.USER_PASSWORD, "");
    }

    public void setUserAge(int userAge) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_AGE, userAge);
    }

    public int getUserAge() {
        int retInt = mProjectSharedPref.getInt(SharedPrefKeyConstant.USER_AGE, 0);
        Log.d("TAG", "TAG");
        return retInt;
    }

    public void setUserHeight(double userHeight) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_HEIGHT, userHeight);
    }

    public double getUserHeight() {
        Long longVal = mProjectSharedPref.getLong(SharedPrefKeyConstant.USER_HEIGHT, 0);
        double doubleVal = Double.longBitsToDouble(longVal);
        return doubleVal;
    }

    public void setUserWeight(double userWeight) {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_WEIGHT, userWeight);
    }

    public double getUserWeight() {
        Long longVal = mProjectSharedPref.getLong(SharedPrefKeyConstant.USER_WEIGHT, 0);
        double doubleVal = Double.longBitsToDouble(longVal);
        return doubleVal;
    }
    public void setUserBloodGroup(String bloodGroup)
    {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_BLOOD_GROUP,bloodGroup);
    }
    public String getUserBloodGroup()
    {
        return mProjectSharedPref.getString(SharedPrefKeyConstant.USER_BLOOD_GROUP,"");
    }
    public void setUserImageString(String imageUri)
    {
        setValuesInSharedPrefs(SharedPrefKeyConstant.USER_IMAGE_URI,imageUri);
    }
    public String getUserImageString()
    {
        return mProjectSharedPref.getString(SharedPrefKeyConstant.USER_IMAGE_URI,"");
    }
}
