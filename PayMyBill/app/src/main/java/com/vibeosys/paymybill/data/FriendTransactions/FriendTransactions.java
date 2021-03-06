package com.vibeosys.paymybill.data.FriendTransactions;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by akshay on 23-08-2016.
 */
public class FriendTransactions implements Serializable {
    private int friendId;
    private String name;
    private String image;
    private double amount;
    private BorrowType type;
    ArrayList<FriendBills> bills;
    private String date;

    public FriendTransactions() {
    }

    public FriendTransactions(int friendId, String name, String image) {
        this.friendId = friendId;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getAmount() {
        return calculateAmount();
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public ArrayList<FriendBills> getBills() {
        return bills;
    }

    public void setBills(ArrayList<FriendBills> bills) {
        this.bills = bills;
    }

    public BorrowType getType() {
        if (getAmount() < 0)
            type = BorrowType.YOU_OWE;
        else
            type = BorrowType.OWES_YOU;
        return type;
    }
/*
    public void setType(BorrowType type) {
        this.type = type;
    }*/

    @Override
    public String toString() {
        return "FriendTransactions{" +
                "friendId=" + friendId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", bills=" + bills +
                '}';
    }

    public ArrayList<FriendBills> getFilterBills() {
        ArrayList<FriendBills> friendBills = new ArrayList<>();
        for (FriendBills friendBill : getBills()) {
            if (friendBill.getFriendBillAmount() != 0) {
                friendBills.add(friendBill);
            }
        }
        return friendBills;
    }

    private double calculateAmount() {
        MyBills myBills = new MyBills();
        ArrayList<FriendBills> ownBills = myBills.filterBills(this.bills, 1);
        ArrayList<FriendBills> hisBills = myBills.filterBills(this.bills, this.friendId);
        double ownAmount = calculateBillAmount(ownBills);
        double hisAmount = calculateBillAmount(hisBills);
        return ownAmount - hisAmount;
    }


    private double calculateBillAmount(ArrayList<FriendBills> bills) {
        double billAmount = 0;
        for (FriendBills friendBills : bills) {
            if (friendBills.getTransaction().size() == 0) {

            } else {
                billAmount = billAmount + friendBills.getFriendBillAmount();
            }

        }
        return billAmount;
    }

    private class MyBills implements BillFilter {

        @Override
        public ArrayList<FriendBills> filterBills(ArrayList<FriendBills> bills, int personId) {
            ArrayList<FriendBills> filterBills = new ArrayList<FriendBills>();
            for (FriendBills bill : bills) {
                if (bill.getPaidBy() == personId) {
                    filterBills.add(bill);
                }
            }
            return filterBills;
        }
    }

    public String getDate() {
        if (this.getFilterBills().size() <= 0) {
            this.date = null;
            return this.date;
        } else {
            FriendBills bills = this.getFilterBills().get(this.getFilterBills().size() - 1);
            this.date = bills.getDate();
            return this.date;
        }

    }
}
