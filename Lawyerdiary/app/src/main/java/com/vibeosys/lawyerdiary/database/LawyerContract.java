package com.vibeosys.lawyerdiary.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by akshay on 09-09-2016.
 */
public class LawyerContract {

    public static final String CONTENT_AUTHORITY = "com.vibeosys.lawyerdiary";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CLIENT = "client";
    public static final String PATH_CASE = "case_data";
    public static final String PATH_DOCUMENT = "document";
    public static final String PATH_REMINDER = "reminder";


    public static final class Client implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLIENT).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CLIENT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CLIENT;
        public final static String TABLE_NAME = "client";
        public final static String NAME = "name";
        public final static String EMAIL = "email";
        public final static String PH_NUMBER = "phone_number";
        public final static String ADDRESS = "address";

        public static Uri buildClientUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildClientPhMatchUri(String phNo) {
            return CONTENT_URI.buildUpon().appendPath(phNo).build();
        }

        public static String getClientPhNoFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getClientIdfromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class Case implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CASE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CASE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CASE;

        public final static String TABLE_NAME = "case_data";
        public final static String CASE_NAME = "case_name";
        public final static String CLIENT_ID = "client_id";
        public final static String AGAINST = "against";
        public final static String SITUATION = "situation";
        public final static String CASE_DATE = "case_date";
        public final static String COURT_LOCATION = "court_location";
        public final static String DESCRIPTION = "description";
        public final static String STATUS = "status";
        public final static String KEY_POINTS = "key_points";

        public static final Uri buildCaseUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getCaseIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class Document implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DOCUMENT).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DOCUMENT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DOCUMENT;
        public final static String TABLE_NAME = "document";
        public final static String DOCUMENT_NAME = "name";
        public final static String FILE_PATH = "file_path";
        public final static String CASE_ID = "case_id";
        public final static String LAST_UPDATED_DATE = "last_updated_date";

        public static final Uri buildDocumentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Reminder implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REMINDER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_REMINDER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_REMINDER;
        public final static String TABLE_NAME = "reminder";
        public final static String REMINDER_NAME = "name";
        public final static String START_DATE_TIME = "start_date_time";
        public final static String END_DATE_TIME = "end_date_time";
        public final static String LOCATION = "location";
        public final static String NOTE = "note";
        public final static String COLOUR = "colour";
        public final static String CASE_ID = "case_id";

        public static final Uri buildReminderUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
