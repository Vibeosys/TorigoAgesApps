package com.vibeosys.paymybill.database;

/**
 * This class is use to identify the table names and the attributes
 * Created by akshay on 24-05-2016.
 */
public class SqlContract {
    public static String DATABASE_NAME="payMyBill";

    public abstract class SqlRegisterUser
    {
        public final static String TABLE_NAME ="user";
        public final static String USER_ID="userId";
        public final static String USER_NAME ="userName";
        public final static String USER_EMAIL_ID="userEmailId";
        public final static String USER_PASSWORD="userPassword";
        public final static String USER_IMAGE="userImage";
        public final static String USER_SOURCE ="userLoginSource";
        public final static String USER_LOGIN_SOURCE_KEY="userLoginSourceKey";
        public final static String USER_ROLE_ID="userRoleId";
        public final static String USER_ACTIVE="userActive";
    }
    public abstract class SqlCurrency
    {
        public final static String TABLE_NAME="currency";
        public final static String CURRENCY_ID="currencyId";
        public final static String CURRENCY="currency";
    }
    public abstract class SqlBillType
    {
        public final static String TABLE_NAME="billType";
        public final static String BILL_ID="billId";
        public final static String BILL_TYPE="billType";
    }
    public abstract class SqlBillShared
    {
        public final static String TABLE_NAME="billShared";
        public final static String BILL_SHARED_ID="billSharedId";
        public final static String BILL_TRANSACTION_ID ="transactionId";
        public final static String FRIEND_ID="friendId";
    }
    public abstract  class SqlTransaction
    {
        public final static String TABLE_NAME="billTransaction";
        public final static String TRANSACTION_ID="billTransactionId";
        public final static String TRANSACTION_CURRENCY="billCurrency";
        public final static String TRANSACTION_IN_AMOUNT="billInAmount";
        public final static String TRANSACTION_OUT_AMOUNT="billOutAmount";
        public final static String TRANSACTION_PAID_ON_DATE="billPaidOnDate";
        public final static String TRANSACTION_GENERATED_DATE="billGeneratedDate";
        public final static String TRANSACTION_DESCRIPTION="billDescription";
        public final static String TRANSACTION_BILL_TYPE="billTransactionType";
    }

}
