package com.vibeosys.paymybill.data.FriendTransactions;

import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.database.DbRepository;

import java.util.ArrayList;

/**
 * Created by akshay on 23-08-2016.
 */
public class FriendTransactionHandler {

    private DbRepository dbRepository;

    public FriendTransactionHandler(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    public ArrayList<FriendTransactions> getFriendList() {
        ArrayList<FriendTransactions> friendTransactions = new ArrayList<>();
        friendTransactions = dbRepository.getFriendListTransaction();
        for (FriendTransactions friend : friendTransactions) {
            ArrayList<FriendBills> friendBills = getFriendBills(friend.getFriendId());
            friend.setBills(friendBills);
            for (FriendBills bill : friendBills) {
                ArrayList<FriendBillTransaction> transactions = getBillTransaction(bill.getBillId(), friend.getFriendId());
                bill.setTransaction(transactions);
            }
        }
        return friendTransactions;
    }

    private ArrayList<FriendBills> getFriendBills(int friendId) {
        // replace 1 with my user id
        ArrayList<FriendBills> friendBills = dbRepository.getBills(1, friendId);
        return friendBills;
    }

    private ArrayList<FriendBillTransaction> getBillTransaction(long billId, int friendId) {
        // replace 1 with my user id
        ArrayList<FriendBillTransaction> billTransaction = dbRepository.getTransactionByBill(billId, 1, friendId);
        return billTransaction;
    }
}
