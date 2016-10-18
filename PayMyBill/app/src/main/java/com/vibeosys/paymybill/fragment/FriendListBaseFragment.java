package com.vibeosys.paymybill.fragment;

import android.os.Bundle;

import com.vibeosys.paymybill.data.FriendTransactions.BillFilterByAmount;
import com.vibeosys.paymybill.data.FriendTransactions.FriendBills;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactionHandler;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;

import java.util.ArrayList;

/**
 * Created by akshay on 24-08-2016.
 */
public class FriendListBaseFragment extends BaseFragment {

    protected FriendTransactionHandler friendTransactionHandler;
    protected ArrayList<FriendTransactions> mFriends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendTransactionHandler = new FriendTransactionHandler(mDbRepository);
        BillFilterByAmount filterMyName = new FilterMyName();
        mFriends = filterMyName.filterBillsByAmount(friendTransactionHandler.getFriendList());
        BillFilterByAmount zeroAmount = new FilterZeroAmount();
        mFriends = zeroAmount.filterBillsByAmount(mFriends);
    }

    private class FilterZeroAmount implements BillFilterByAmount {
        ArrayList<FriendTransactions> filterData = new ArrayList<>();

        @Override
        public ArrayList<FriendTransactions> filterBillsByAmount(ArrayList<FriendTransactions> bills) {
            for (FriendTransactions friendBill : bills) {
                if (friendBill.getAmount() != 0) {
                    filterData.add(friendBill);
                } else {

                }
            }
            return filterData;
        }
    }

    private class FilterMyName implements BillFilterByAmount {

        ArrayList<FriendTransactions> filterData = new ArrayList<>();

        @Override
        public ArrayList<FriendTransactions> filterBillsByAmount(ArrayList<FriendTransactions> bills) {
            int userId = Integer.parseInt(mSessionManager.getUserFriendId());
            for (FriendTransactions friendBill : bills) {
                if (friendBill.getFriendId() != userId) {
                    filterData.add(friendBill);
                } else {

                }
            }
            return filterData;
        }
    }

}
