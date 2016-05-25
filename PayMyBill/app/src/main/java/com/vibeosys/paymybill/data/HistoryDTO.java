package com.vibeosys.paymybill.data;

/**
 * Created by akshay on 25-05-2016.
 */
public class HistoryDTO {

    private String mBillDesc;
    private String mBillType;
    private String mBillDate;
    private String mAmount;

    public HistoryDTO(String mBillDesc, String mBillType, String mBillDate, String mAmount) {
        this.mBillDesc = mBillDesc;
        this.mBillType = mBillType;
        this.mBillDate = mBillDate;
        this.mAmount = mAmount;
    }

    public String getBillDesc() {
        return mBillDesc;
    }

    public void setBillDesc(String mBillDesc) {
        this.mBillDesc = mBillDesc;
    }

    public String getBillType() {
        return mBillType;
    }

    public void setBillType(String mBillType) {
        this.mBillType = mBillType;
    }

    public String getBillDate() {
        return mBillDate;
    }

    public void setBillDate(String mBillDate) {
        this.mBillDate = mBillDate;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String mAmount) {
        this.mAmount = mAmount;
    }
}
