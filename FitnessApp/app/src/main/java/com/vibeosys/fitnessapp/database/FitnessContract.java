package com.vibeosys.fitnessapp.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by akshay on 04-03-2017.
 */
public class FitnessContract {

    public static final String CONTENT_AUTHORITY = "com.vibeosys.fitnessapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER_LOGIN = "user_login";


    public static final class UserLogin implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_LOGIN).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USER_LOGIN;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USER_LOGIN;
        public final static String TABLE_NAME = "user";
        public final static String USER_NAME = "user_name";
        public final static String USER_EMAIL_ID = "email_id";
        public final static String USER_ID = "user_id";
        public final static String USER_PASSWORD = "user_password";

        public static final Uri userLoginUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
