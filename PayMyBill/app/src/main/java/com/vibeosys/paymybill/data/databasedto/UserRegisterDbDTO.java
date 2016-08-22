package com.vibeosys.paymybill.data.databasedto;

/**
 * Created by shrinivas on 22-08-2016.
 */
public class UserRegisterDbDTO {
    String mUserEmailId;
    String mUserPassword;
    String mFirstName;
    String mLastName;
    String mPhoneNumber;
    String mLoginSource;
    String mFbTokenId;
    String mPhotoUrl;

    public UserRegisterDbDTO(String mUserEmailId, String mUserPassword, String mFirstName,
                             String mLastName, String mPhoneNumber, String mLoginSource,
                             String mFbTokenId, String mPhotoUrl) {
        this.mUserEmailId = mUserEmailId;
        this.mUserPassword = mUserPassword;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mPhoneNumber = mPhoneNumber;
        this.mLoginSource = mLoginSource;
        this.mFbTokenId = mFbTokenId;
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getUserEmailId() {
        return mUserEmailId;
    }

    public void setUserEmailId(String mUserEmailId) {
        this.mUserEmailId = mUserEmailId;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getLoginSource() {
        return mLoginSource;
    }

    public void setLoginSource(String mLoginSource) {
        this.mLoginSource = mLoginSource;
    }

    public String getFbTokenId() {
        return mFbTokenId;
    }

    public void setFbTokenId(String mFbTokenId) {
        this.mFbTokenId = mFbTokenId;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }
}
