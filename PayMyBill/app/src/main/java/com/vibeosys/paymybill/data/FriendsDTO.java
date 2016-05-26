package com.vibeosys.paymybill.data;

import java.util.Date;

/**
 * Created by akshay on 26-05-2016.
 */
public class FriendsDTO {

    private int mId;
    private String mName;
    private String mImgSource;
    private double mAmount;
    private Date mDate;
    private boolean flagOwe;

    public FriendsDTO(int mId, String mName, String mImgSource, double mAmount) {
        this.mId = mId;
        this.mName = mName;
        this.mImgSource = mImgSource;
        this.mAmount = mAmount;
    }

    public FriendsDTO(int mId, String mName, String mImgSource, double mAmount, Date mDate, boolean flagOwe) {
        this.mId = mId;
        this.mName = mName;
        this.mImgSource = mImgSource;
        this.mAmount = mAmount;
        this.mDate = mDate;
        this.flagOwe = flagOwe;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImgSource() {
        return mImgSource;
    }

    public void setImgSource(String mImgSource) {
        this.mImgSource = mImgSource;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isFlagOwe() {
        return flagOwe;
    }

    public void setFlagOwe(boolean flagOwe) {
        this.flagOwe = flagOwe;
    }
}
