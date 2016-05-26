package com.vibeosys.paymybill.data;

/**
 * Created by akshay on 26-05-2016.
 */
public class FriendsDTO {

    private int mId;
    private String mName;
    private String mImgSource;
    private double mAmount;

    public FriendsDTO(int mId, String mName, String mImgSource, double mAmount) {
        this.mId = mId;
        this.mName = mName;
        this.mImgSource = mImgSource;
        this.mAmount = mAmount;
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
}
