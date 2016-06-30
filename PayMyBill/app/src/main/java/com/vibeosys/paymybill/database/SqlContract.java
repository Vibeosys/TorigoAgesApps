package com.vibeosys.paymybill.database;

/**
 * This class is use to identify the table names and the attributes
 * Created by akshay on 24-05-2016.
 */
public class SqlContract {
    public static String DATABASE_NAME="payMyBill";

    public abstract class SqlRegisterUser
    {
        public final static String TABLE_NAME ="registerUser";
        public final static String USER_ID="userId";
        public final static String USER_NAME ="userName";
        public final static String USER_EMAIL_ID="userEmailId";
        public final static String USER_PASSWORD="userPassword";
        public final static String USER_IMAGE="userImage";
        public final static String USER_SOURCE ="loginSource";
    }
}
