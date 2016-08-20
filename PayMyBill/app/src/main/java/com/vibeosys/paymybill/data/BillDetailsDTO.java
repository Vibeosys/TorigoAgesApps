package com.vibeosys.paymybill.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by akshay on 20-08-2016.
 */
public class BillDetailsDTO implements Serializable {

    private long billNo;
    private String billDate;
    private double amount;
    private String description;
    private int typeId;
    private int currencyId;
    private int paidBy;
    private List<FriendsDTO> shareWith;

    public BillDetailsDTO(long billNo, String billDate, double amount, String description,
                          int typeId, int currencyId, int paidBy) {
        this.billNo = billNo;
        this.billDate = billDate;
        this.amount = amount;
        this.description = description;
        this.typeId = typeId;
        this.currencyId = currencyId;
        this.paidBy = paidBy;
    }

    public long getBillNo() {
        return billNo;
    }

    public void setBillNo(long billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public int getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(int paidBy) {
        this.paidBy = paidBy;
    }

    public List<FriendsDTO> getShareWith() {
        return shareWith;
    }

    public void setShareWith(List<FriendsDTO> shareWith) {
        this.shareWith = shareWith;
    }
}
