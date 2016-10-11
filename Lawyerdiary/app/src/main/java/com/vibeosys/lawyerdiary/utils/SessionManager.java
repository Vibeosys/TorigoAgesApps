package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shrinivas on 11-10-2016.
 */
public class SessionManager {

    private static final String PROJECT_PREFERENCES = "com.vibeosys.lawyerdiary";
    private static SessionManager mSessionManager;
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;
    private static PropertyFileReader mPropertyFileReader = null;

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
    private static void loadProjectSharedPreferences() {
        if (mProjectSharedPref == null) {
            mProjectSharedPref = mContext.getSharedPreferences(PROJECT_PREFERENCES, Context.MODE_PRIVATE);
        }
        setValuesInSharedPrefs(PropertyTypeConstants.API_ENDPOINT_URI, mPropertyFileReader.getEndPointUri());

    }
    private static void setValuesInSharedPrefs(String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
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
}
