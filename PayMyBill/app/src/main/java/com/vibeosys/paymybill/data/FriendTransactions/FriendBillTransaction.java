package com.vibeosys.paymybill.data.FriendTransactions;

import java.io.Serializable;

/**
 * Created by akshay on 23-08-2016.
 */
public class FriendBillTransaction implements Serializable {

    private int transactionId;
    private int personId;
    private double creditAmount;
    private double debitAmount;
    private String transactionDate;

    public FriendBillTransaction() {
    }

    public FriendBillTransaction(int transactionId, int personId, double creditAmount, double debitAmount, String transactionDate) {
        this.transactionId = transactionId;
        this.personId = personId;
        this.creditAmount = creditAmount;
        this.debitAmount = debitAmount;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        transactionId = transactionId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "FriendBillTransaction{" +
                "transactionId=" + transactionId +
                ", personId=" + personId +
                ", creditAmount=" + creditAmount +
                ", debitAmount=" + debitAmount +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}
