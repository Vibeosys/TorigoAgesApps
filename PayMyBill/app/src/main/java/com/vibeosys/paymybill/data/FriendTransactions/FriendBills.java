package com.vibeosys.paymybill.data.FriendTransactions;

import java.util.ArrayList;

/**
 * Created by akshay on 23-08-2016.
 */
public class FriendBills {
    private long billId;
    private String billDesc;
    private String date;
    private double billAmount;
    private int paidBy;
    private double friendBillAmount;
    private BorrowType type;
    ArrayList<FriendBillTransaction> transaction;

    public FriendBills() {
    }

    public FriendBills(long billId, String billDesc, String date, double billAmount, int paidBy) {
        this.billId = billId;
        this.billDesc = billDesc;
        this.date = date;
        this.billAmount = billAmount;
        this.paidBy = paidBy;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillDesc() {
        return billDesc;
    }

    public void setBillDesc(String billDesc) {
        this.billDesc = billDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public int getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(int paidBy) {
        this.paidBy = paidBy;
    }

    public double getFriendBillAmount() {
        return calculateFriendBillAmt();
    }

    private double calculateFriendBillAmt() {
        double debitAmount = 0;
        double creditAmount = 0;
        for (FriendBillTransaction transaction : this.getTransaction()) {
            debitAmount = debitAmount + transaction.getDebitAmount();
            creditAmount = creditAmount + transaction.getCreditAmount();
        }
        return debitAmount - creditAmount;
    }

    public void setFriendBillAmount(double friendBillAmount) {
        this.friendBillAmount = friendBillAmount;
    }

    public ArrayList<FriendBillTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(ArrayList<FriendBillTransaction> transaction) {
        this.transaction = transaction;
    }

    public BorrowType getType() {
        if (friendBillAmount < 0) {
            this.type = BorrowType.YOU_OWE;
        } else {
            this.type = BorrowType.OWES_YOU;
        }
        return type;
    }

    @Override
    public String toString() {
        return "FriendBills{" +
                "billId=" + billId +
                ", billDesc='" + billDesc + '\'' +
                ", date='" + date + '\'' +
                ", billAmount=" + billAmount +
                ", paidBy=" + paidBy +
                ", friendBillAmount=" + friendBillAmount +
                ", transaction=" + transaction +
                '}';
    }
}
