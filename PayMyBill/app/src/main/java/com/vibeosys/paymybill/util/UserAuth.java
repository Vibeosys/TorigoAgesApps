package com.vibeosys.paymybill.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.vibeosys.paymybill.activities.LoginActivity;

/**
 * Created by shrinivas on 12-07-2016.
 */
public class UserAuth {

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

    public static boolean isUserLoggedIn(Context context) {
        String theUserEmailId = SessionManager.Instance().getUserEmailId();
        String theUserName = SessionManager.Instance().getUserName();
        return isUserLoggedIn(context, theUserName, theUserEmailId);
    }

    public static boolean isUserLoggedIn() {
        String theUserEmailId = SessionManager.Instance().getUserEmailId();
        String theUserName = SessionManager.Instance().getUserName();
        String accessTokenKey = SessionManager.Instance().getUserAccessToken();
        //String theUserPhotoURL = SessionManager.Instance().getUserPhotoUrl();

       /* if (theUserEmailId == null || theUserEmailId == "" || theUserName == null || theUserName == "") {
            return false;
        }*/
        /*if(accessTokenKey==null || accessTokenKey==""|| accessTokenKey==null ||accessTokenKey=="")
        {
            return false;
        }*/
        if(theUserEmailId==null || theUserEmailId==""|| TextUtils.isEmpty(theUserEmailId))
        {
            return false;
        }
        return true;
    }

    /*public void saveAuthenticationInfo(UserDbDTO userInfo, final Context context) {
        if (userInfo == null)
            return;

        if (userInfo.getEmail() == null || userInfo.getEmail() == "" ||
                userInfo.getFname() == null || userInfo.getFname() == "")
            return;

        SessionManager theSessionManager = SessionManager.getInstance(context);
        theSessionManager.setUserId(userInfo.getUserid());
        theSessionManager.setUserName(userInfo.getFname(), userInfo.getLname());
        theSessionManager.setUserEmailId(userInfo.getEmail());
        theSessionManager.setUserRoleId(userInfo.getRoleid());
        theSessionManager.setUserPassword(userInfo.getPwd());
        theSessionManager.setUserAccessToken(userInfo.getToken());
        theSessionManager.setRadiusInKm(userInfo.getRadiusInKm());
    }
*/
    public static boolean CleanAuthenticationInfo() {

        SessionManager theSessionManager = SessionManager.Instance();
        theSessionManager.setUserId(null);
        theSessionManager.setUserName(null, null);
        theSessionManager.setUserEmailId(null);
        /*theSessionManager.setUserRoleId(0);*/
        theSessionManager.setUserAccessToken(null);
        theSessionManager.setUserPassword(null);
        theSessionManager.setUserAccessToken(null);

        return true;
    }
}
