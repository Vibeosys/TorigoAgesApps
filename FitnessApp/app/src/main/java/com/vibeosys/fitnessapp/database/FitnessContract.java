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
    public static final String PATH_BMI_RANGE = "bmi_range";
    public static final String PATH_WORKOUT_MASTER = "workout_master";
    public static final String PATH_SETS_MASTER = "sets_master";
    public static final String PATH_DAILY_WORKOUT_SET = "daily_workout_set";
    public static final String PATH_SETS_REPETITION = "sets_repetition";
    public static final String PATH_WORK_CATEGORY = "work_category";
    public static final String PATH_WORK_HISTORY = "work_history";
    public static final String PATH_DAILY_WORK = "daily_work";
    public static final String PATH_SETS_HISTORY = "sets_history";
    public static final String PATH_USERS_BMI = "bmi_users";
    public static final String PATH_USR_DIET = "user_diet";
    public static final String PATH_USR_WORKOUT_ADVICE = "user_workout_advice";


    public static final class UserLogin implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_LOGIN).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USER_LOGIN;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USER_LOGIN;
        public final static String TABLE_NAME = "User";
        public final static String USER_ID = "user_id";
        public final static String USER_NAME = "user_name";
        public final static String USER_EMAIL_ID = "user_email";
        public final static String USER_PASSWORD = "user_password";
        public final static String USER_AGE = "user_age";
        public final static String USER_HEIGHT = "user_height";
        public final static String USER_WEIGHT = "user_weight";

        public static final Uri userLoginUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class BMIRange implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BMI_RANGE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_BMI_RANGE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_BMI_RANGE;
        public final static String TABLE_NAME = "BmiRange";
        public final static String BMI_ID = "bmi_id";
        public final static String BMI_MIN = "bmi_min";
        public final static String BMI_MAX = "bmi_max";
        public final static String BMI_RESULT = "bmi_result";

        public static final Uri bmiRangeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class WorkOutMaster implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKOUT_MASTER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORKOUT_MASTER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORKOUT_MASTER;
        public final static String TABLE_NAME = "WorkOutMaster";
        public final static String WKM_ID = "wkm_id";
        public final static String WKM_NAME = "wkm_name";
        public final static String WKM_DESC = "wkm_desc";
        public final static String BMI_MIN = "bmi_min";
        public final static String BMI_MAX = "bmi_max";

        public static final Uri workoutMasterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class SetsMaster implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SETS_MASTER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_MASTER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_MASTER;
        public final static String TABLE_NAME = "SetsMaster";
        public final static String SET_ID = "set_id";
        public final static String SET_NAME = "set_name";
        public final static String SET_DESC = "set_desc";
        public final static String SET_WKM_ID = "set_wkm_id";
        public final static String SET_CATEGORY_ID = "set_category_id";

        public static final Uri setMasterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class DailyWorkoutSets implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DAILY_WORKOUT_SET).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DAILY_WORKOUT_SET;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DAILY_WORKOUT_SET;
        public final static String TABLE_NAME = "DailyWorkoutSets";
        public final static String DW_ID = "dw_id";
        public final static String DW_SET_ID = "dw_set_id";
        public final static String DW_DATE_TIME = "dw_date_time";
        public final static String DW_REPETITION = "dw_repetition";
        public final static String DW_USER_ID = "dw_user_id";
        public final static String SETS_DAILY_ID = "set_daily_id";

        public static final Uri dailyWorkoutUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class SetsRepetition implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SETS_REPETITION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_REPETITION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_REPETITION;
        public final static String TABLE_NAME = "SetsRepetition";
        public final static String REP_ID = "rep_id";
        public final static String REP_DW_ID = "work_id";
        public final static String REP_NO_REP = "no_of_rep";
        public final static String REP_MEASURE = "rep_measures";
        public final static String REP_DATE_TIME = "rep_date_time";

        public static final Uri setsRepUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class WorkCategory implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_CATEGORY).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORK_CATEGORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORK_CATEGORY;
        public final static String TABLE_NAME = "WorkCategory";
        public final static String CAT_ID = "category_id";
        public final static String CAT_NAME = "category_name";
        public final static String CAT_UNIT = "category_unit";
        public final static String CAT_MEASURE = "category_measure";

        public static final Uri categoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class DailyWorkout implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DAILY_WORK).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DAILY_WORK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_DAILY_WORK;
        public final static String TABLE_NAME = "DailyWorkout";
        public final static String DAILY_ID = "daily_id";
        public final static String WORK_ID = "work_id";
        public final static String DATE_TIME = "date_time";

        public static final Uri dailyWorkUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


    public static final class UsersBmi implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS_BMI).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USERS_BMI;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USERS_BMI;
        public final static String TABLE_NAME = "UsersBmi";
        public final static String BMI_ID = "bmi_id";
        public final static String BMI_RANGE = "bmi";
        public final static String BMI_WEIGHT = "weight";
        public final static String BMI_HEIGHT = "height";
        public final static String DATE_TIME = "bmi_date_time";

        public static final Uri usersBmiUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class WorkHistory implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_HISTORY).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORK_HISTORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WORK_HISTORY;
    }

    public static final class SetsHistory implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SETS_HISTORY).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_HISTORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_SETS_HISTORY;
    }

    public static final class UserDiet implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_USR_DIET).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USR_DIET;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USR_DIET;
        public final static String TABLE_NAME = "UserDiet";
        public final static String BMI_MIN = "ud_bmi_min";
        public final static String BMI_MAX = "ud_bmi_max";
        public final static String USER_DIET = "ud_diet";
        public final static String DIET_PRIORITY = "ud_diet_priority";
        public final static String USER_DIET_CATEGORY_ID = "ud_category_id";

        public static final Uri userDietUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class UserWorkoutAdvice implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_USR_WORKOUT_ADVICE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USR_WORKOUT_ADVICE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_USR_WORKOUT_ADVICE;
        public final static String TABLE_NAME = "UserWorkoutAdvice";
        public final static String ADVICE_ID = "advice_id";
        public final static String WORK_ID = "work_id";

        public static final Uri userWorkoutUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
