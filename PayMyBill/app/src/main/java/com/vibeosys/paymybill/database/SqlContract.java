package com.vibeosys.paymybill.database;

/**
 * This class is use to identify the table names and the attributes
 * Created by akshay on 24-05-2016.
 */
public class SqlContract {
    public static String DATABASE_NAME = "payMyBill";


    public abstract class SqlBillType {
        public final static String TABLE_NAME = "BillType";
        public final static String BILL_ID = "BillTypeId";
        public final static String BILL_TYPE_NAME = "BillTypeName";
    }

    public abstract class SqlCurrency {
        public final static String TABLE_NAME = "CurrencyType";
        public final static String CURRENCY_TYPE_ID = "CurrencyTypeId";
        public final static String CURRENCY_TYPE_NAME = "CurrencyTypeName";
        public final static String CURRENCY_SYMBOL = "CurrencySymbol";
    }

    public abstract class SqlLoginSource {
        public final static String TABLE_NAME = "LoginSourceType";
        public final static String LOGIN_SOURCE_ID = "LoginSourceId";
        public final static String LOGIN_SOURCE_NAME = "LoginSourceName";
    }

    public abstract class SqlRegisterUser {
        public final static String TABLE_NAME = "UserData";
        public final static String USER_ID = "UserId";
        public final static String USER_EMAIL_ID = "UserEmail";
        public final static String USER_PASSWORD = "Pwd";
        public final static String USER_FIRST_NAME = "FirstName";
        public final static String USER_LAST_NAME = "LastName";
        public final static String USER_PHONE = "Phone";
        public final static String USER_LOGIN_SOURCE = "LoginSource";
        public final static String USER_IMAGE_URL = "PhotoUrl";
        public final static String USER_LOGIN_SOURCE_KEY = "FbApiToken";
    }

    public abstract class SqlFriend {
        public final static String TABLE_NAME = "Friend";
        public final static String FRIEND_ID = "FriendId";
        public final static String FRIEND_NAME = "Name";
        public final static String FRIEND_CONTACT_NO = "ContactNo";
        public final static String FRIEND_EMAIL = "emailId";
        public final static String FRIEND_PHOTO = "ImageUrl";
    }


    public abstract class SqlBill {
        public final static String TABLE_NAME = "Bill";
        public final static String BILL_ID = "BillId";
        public final static String BILL_NO = "BillNo";
        public final static String BILL_DATE = "BillDate";
        public final static String BILL_AMOUNT = "BillAmount";
        public final static String BILL_DESC = "BillDesc";
        public final static String BILL_TYPE_ID = "BillTypeId";
        public final static String BILL_CURRENCY_ID = "CurrencyId";
        public final static String BILL_PAID_ID = "PaidBy";
    }

    public abstract class SqlTransaction {
        public final static String TABLE_NAME = "TransactionDetails";
        public final static String TRANSACTION_ID = "TransactionId";
        public final static String TRANSACTION_BILL_ID = "BillId";
        public final static String TRANSACTION_PERSON_ID = "PersonId";
        public final static String TRANSACTION_CREDIT_AMOUNT = "CreditAmt";
        public final static String TRANSACTION_DEBIT_AMOUNT = "DebitAmt";
        public final static String TRANSACTION_DESCRIPTION = "Desc";
        public final static String TRANSACTION_DATE = "TransactionDate";
        public final static String TRANSACTION_IS_SETTLE = "IsSettle";
    }


}
