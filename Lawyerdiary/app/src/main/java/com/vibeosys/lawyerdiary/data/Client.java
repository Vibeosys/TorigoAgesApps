package com.vibeosys.lawyerdiary.data;

/**
 * Created by Vibeosys software on 12-09-2016.
 */

/**
 * Client data object module which contains the client information
 */
public class Client {

    private long _Id;
    private String mName;
    private String mEmail;
    private String mPhoneNumber;
    private String mAddress;

    public Client(long _Id, String mName) {
        this._Id = _Id;
        this.mName = mName;
    }

    public Client(long _Id, String mName, String mPhoneNumber) {
        this._Id = _Id;
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
    }

    public Client(long _Id, String mName, String mEmail, String mPhoneNumber, String mAddress) {
        this._Id = _Id;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
        this.mAddress = mAddress;
    }

    public long get_Id() {
        return _Id;
    }

    public void set_Id(long _Id) {
        this._Id = _Id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}
