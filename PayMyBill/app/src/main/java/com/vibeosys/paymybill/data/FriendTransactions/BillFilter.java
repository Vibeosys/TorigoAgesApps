package com.vibeosys.paymybill.data.FriendTransactions;

import java.util.ArrayList;

/**
 * Created by akshay on 23-08-2016.
 */
public interface BillFilter {
    public ArrayList<FriendBills> filterBills(ArrayList<FriendBills> bills, int personId);
}
