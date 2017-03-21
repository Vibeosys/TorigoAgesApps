package com.vibeosys.fitnessapp.utils;

import android.text.TextUtils;

/**
 * Created by shrinivas on 15-03-2017.
 */
public class UserAuth {
    public static boolean isUserLogin() {
        String userEmailId = SharedPrefManager.instance().getUserEmailId();
        if (TextUtils.isEmpty(userEmailId) || userEmailId == null || userEmailId == "") {
            return false;
        } else
            return true;
    }

    public static boolean clearUserAuthentication() {
        SharedPrefManager sharedPrefManager = SharedPrefManager.instance();
        sharedPrefManager.setUserEmailId("");
        sharedPrefManager.setUserPassword("");
        return true;
    }
}
