package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vibeosys software on 11-10-2016.
 */
public class SessionManager {

    private static final String PROJECT_PREFERENCES = "com.vibeosys.lawyerdiary";
    private static SessionManager mSessionManager;
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;
    private static PropertyFileReader mPropertyFileReader = null;

    /**
     * This will returns the instance of SessionManager class.
     * @param context Context of the calling class.
     * @return instance of SessionManager class.
     */
    public static SessionManager getInstance(Context context) {
        if (mSessionManager != null)
            return mSessionManager;

        mContext = context;
        mPropertyFileReader = PropertyFileReader.getInstance(context);
        loadProjectSharedPreferences();
        mSessionManager = new SessionManager();

        return mSessionManager;
    }
    public static SessionManager Instance() {
        if (mSessionManager != null)
            return mSessionManager;
        else
            throw new IllegalArgumentException("No instance is yet created");
    }
    private SessionManager() {
    }

    /**
     * This function is use to initialize the  shared preference.
     */
    private static void loadProjectSharedPreferences() {
        if (mProjectSharedPref == null) {
            mProjectSharedPref = mContext.getSharedPreferences(PROJECT_PREFERENCES, Context.MODE_PRIVATE);
        }
        setValuesInSharedPrefs(PropertyTypeConstants.API_ENDPOINT_URI, mPropertyFileReader.getEndPointUri());

    }

    /**
     * This function is use to edit the shared preferences having key and value parameter.
     * @param sharedPrefKey This is having unique String value.
     * @param sharedPrefValue This is having string data type value.
     */
    private static void setValuesInSharedPrefs(String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    /**
     * This function is use to edit the shared preferences having key and value as integer.
     * @param sharedPrefKey This is having unique String value.
     * @param sharedPrefValue This is having integer data type value.
     */
    private static void setValuesInSharedPrefs(String sharedPrefKey, int sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putInt(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }
    public String getUserId()
    {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_ID,null);
    }
    public void setUserId(String userId)
    {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_ID,userId);
    }
    public String gerUserName()
    {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_NAME,null);
    }
    public void setUserName(String userName)
    {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_NAME,userName);
    }
    public  String getUserEmailId()
    {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_EMAIL_ID,null);
    }
    public void setUserEmailId(String userEmailId)
    {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_EMAIL_ID,userEmailId);
    }
    public  String getUserPassword()
    {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_PASSWORD,null);
    }
    public void setUserPassword(String userPassword)
    {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_PASSWORD,userPassword);
    }
    public void setInAppPurchase(int purchase)
    {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_PURCHASE,purchase);
    }
    public int getInAppPurchase()
    {
        return mProjectSharedPref.getInt(PropertyTypeConstants.USER_PURCHASE,0);
    }
}
