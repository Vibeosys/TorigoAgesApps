package com.vibeosys.paymybill.data;

import java.io.Serializable;

/**
 * Created by akshay on 20-08-2016.
 */
public class TransactionDetailsDTO implements Serializable {

    private int personId;
    private double creditAmount = 0;
    private double debitAmount = 0;
    private String desc;
    private String transactionDate;

    public TransactionDetailsDTO(int personId, double creditAmount, double debitAmount,
                                 String desc, String transactionDate) {
        this.personId = personId;
        this.creditAmount = creditAmount;
        this.debitAmount = debitAmount;
        this.desc = desc;
        this.transactionDate = transactionDate;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
