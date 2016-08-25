package com.vibeosys.paymybill.data.databasedto;

/**
 * Created by shrinivas on 25-08-2016.
 */
public class UpdateUser {
    String userName;
    String userEmailId;
    String userPhoneNo;
    String userPassword;
    String userCountry;

    public UpdateUser(String userName, String userEmailId, String userPhoneNo, String userPassword, String userCountry) {
        this.userName = userName;
        this.userEmailId = userEmailId;
        this.userPhoneNo = userPhoneNo;
        this.userPassword = userPassword;
        this.userCountry = userCountry;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
