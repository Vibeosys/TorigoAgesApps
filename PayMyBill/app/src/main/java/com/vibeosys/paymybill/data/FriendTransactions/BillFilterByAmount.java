package com.vibeosys.paymybill.data.FriendTransactions;

import java.util.ArrayList;

/**
 * Created by akshay on 24-08-2016.
 */
public interface BillFilterByAmount {
    public ArrayList<FriendTransactions> filterBillsByAmount(ArrayList<FriendTransactions> bills);
}
