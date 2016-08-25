package com.vibeosys.paymybill.data;

/**
 * Created by akshay on 25-05-2016.
 */
public class HistoryDTO {

    private int mBillId;
    private String mBillDesc;
    private String mBillDate;
    private double mAmount;

    public HistoryDTO(int mBillId, String mBillDesc, String mBillDate, double mAmount) {
        this.mBillId = mBillId;
        this.mBillDesc = mBillDesc;
        this.mBillDate = mBillDate;
        this.mAmount = mAmount;
    }

    public int getBillId() {
        return mBillId;
    }

    public void setBillId(int mBillId) {
        this.mBillId = mBillId;
    }

    public String getBillDesc() {
        return mBillDesc;
    }

    public void setBillDesc(String mBillDesc) {
        this.mBillDesc = mBillDesc;
    }

    public String getBillDate() {
        return mBillDate;
    }

    public void setBillDate(String mBillDate) {
        this.mBillDate = mBillDate;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double mAmount) {
        this.mAmount = mAmount;
    }
}
