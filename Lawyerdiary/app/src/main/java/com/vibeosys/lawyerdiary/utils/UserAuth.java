package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.vibeosys.lawyerdiary.activities.LoginActivity;

/**
 * Created by shrinivas on 11-10-2016.
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

    public static boolean isUserLoggedIn() {
        String userEmailId = SessionManager.Instance().getUserEmailId();
        String userName = SessionManager.Instance().gerUserName();

        if(userName==null || userName=="" || TextUtils.isEmpty(userName))
        {
            return false;
        }
        return true;
    }

    public static boolean CleanAuthenticationInfo() {

        SessionManager theSessionManager = SessionManager.Instance();
        theSessionManager.setUserId(null);
        theSessionManager.setUserName(null);
        theSessionManager.setUserEmailId(null);
        theSessionManager.setUserPassword(null);


        return true;
    }
}
