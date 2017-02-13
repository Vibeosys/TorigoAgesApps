package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.vibeosys.lawyerdiary.activities.LoginActivity;

/**
 * Created by Vibeosys software on 11-10-2016.
 */
public class UserAuth {
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;
    private static final String PROJECT_PREFERENCES = "com.vibeosys.lawyerdiary";

    public static boolean isUserLoggedIn(Context context, String userName, String userEmailId) {
        if (userEmailId == null || userEmailId == "" || userName == null || userName == "") {
            Intent theLoginIntent = new Intent(context, LoginActivity.class);
            //theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(theLoginIntent);
            return false;
        }
        return true;
    }

    /**
     * It is use to check whether user is login or not by checking user name is empty or not.
     * @return it returns true when user is login and returns false when user is not login.
     */
    public static boolean isUserLoggedIn() {
        String userEmailId = SessionManager.Instance().getUserEmailId();
        String userName = SessionManager.Instance().gerUserName();

        if(userName==null || userName=="" || TextUtils.isEmpty(userName))
        {
            return false;
        }
        return true;
    }

    /**
     * This function is use to clear the user  authentication from SessionManager class.
     * @return it returns true when it clear the user credentials.
     */

    public static boolean CleanAuthenticationInfo() {

        SessionManager theSessionManager = SessionManager.Instance();
        theSessionManager.setUserId(null);
        theSessionManager.setUserName(null);
        theSessionManager.setUserEmailId(null);
        theSessionManager.setUserPassword(null);


        return true;
    }
}
