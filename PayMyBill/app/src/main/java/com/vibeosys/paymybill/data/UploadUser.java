package com.vibeosys.paymybill.data;

/**
 * Created by mahesh on 10/23/2015.
 */
public class UploadUser extends BaseDTO {
    protected int userId;
    protected String emailId;
    protected String userName;
    protected String password;
    protected int restaurantId;
    protected String imei;
    protected String macId;

    public UploadUser() {

    }

    public UploadUser(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public UploadUser(int userId, int restaurantId, String password, String imei, String macId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.password = password;
        this.imei = imei;
        this.macId = macId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
