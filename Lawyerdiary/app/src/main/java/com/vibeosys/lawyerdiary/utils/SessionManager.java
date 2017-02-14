package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vibeosys software on 11-10-2016.
 */

/**
 * Manage the session of the user using this util class
 * Manage the shred preference values add, update that values using this util class
 */
public class SessionManager {

    private static final String PROJECT_PREFERENCES = "com.vibeosys.lawyerdiary";
    private static SessionManager mSessionManager;
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;
    private static PropertyFileReader mPropertyFileReader = null;

    /**
     * This will returns the instance of SessionManager class.
     * Create the new if the mSessionManager is null
     *
     * @param context Application context
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
     *
     * @param sharedPrefKey   This is having unique String value.
     * @param sharedPrefValue This is having string data type value.
     */
    private static void setValuesInSharedPrefs(String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    /**
     * This function is use to edit the shared preferences having key and value as integer.
     *
     * @param sharedPrefKey   This is having unique String value.
     * @param sharedPrefValue This is having integer data type value.
     */
    private static void setValuesInSharedPrefs(String sharedPrefKey, int sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putInt(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    /**
     * Get the stored user id
     *
     * @return String get the user id stored the shared preference
     * return null if its not present
     */
    public String getUserId() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_ID, null);
    }

    /**
     * Set the user id that want to store the user in the application
     *
     * @param userId String userid that want to store in shared preference
     */
    public void setUserId(String userId) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_ID, userId);
    }

    /**
     * Get the stored user name
     *
     * @return String get the user name stored the shared preference
     * return null if its not present
     */
    public String gerUserName() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_NAME, null);
    }

    /**
     * Set the user Name that want to store the user in the application
     *
     * @param userName String username that want to store in shared preference
     */
    public void setUserName(String userName) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_NAME, userName);
    }

    /**
     * Get the stored user email id
     *
     * @return String get the user email id stored the shared preference
     * return null if its not present
     */
    public String getUserEmailId() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_EMAIL_ID, null);
    }

    /**
     * Set the user email that want to store the user in the application
     *
     * @param userEmailId String user email id that want to store in shared preference
     */
    public void setUserEmailId(String userEmailId) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_EMAIL_ID, userEmailId);
    }

    /**
     * Get the stored user password
     *
     * @return tring get the user password id stored the shared preference
     * return null if its not present
     */
    public String getUserPassword() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_PASSWORD, null);
    }

    /**
     * Set the user password that want to store the user in the application
     *
     * @param userPassword String user password that want to store in shared preference
     */
    public void setUserPassword(String userPassword) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_PASSWORD, userPassword);
    }

    /**
     * Set user can purchase the application or not
     *
     * @param purchase Int flag represent that user is purchase application or not
     */
    public void setInAppPurchase(int purchase) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_PURCHASE, purchase);
    }

    /**
     * Get the purchase status of the user
     *
     * @return int 1 if user purchase the application o.w. 0
     */
    public int getInAppPurchase() {
        return mProjectSharedPref.getInt(PropertyTypeConstants.USER_PURCHASE, 0);
    }
}
