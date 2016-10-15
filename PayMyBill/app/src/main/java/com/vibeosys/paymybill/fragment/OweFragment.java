package com.vibeosys.paymybill.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.activities.SettleActivity;
import com.vibeosys.paymybill.adapters.FriendListAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.BillFilterByAmount;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;

import java.util.ArrayList;

/**
 * Created by shrinivas on 26-05-2016.
 */
public class OweFragment extends FriendListBaseFragment {
    private ListView mFriendList;
    private FriendListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_list, container, false);
        mFriendList = (ListView) view.findViewById(R.id.dashBoard_list);
        createList();
        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendTransactions friendTransactions = (FriendTransactions) listAdapter.getItem(position);
                Intent iSettle = new Intent(getContext(), SettleActivity.class);
                iSettle.putExtra("data", friendTransactions);
                startActivity(iSettle);
            }
        });
        return view;
    }

    private void createList() {
        Log.d(TAG, mFriends.toString());
        FriendOweFilter friendOweFilter = new FriendOweFilter();

        listAdapter = new FriendListAdapter(getContext(), friendOweFilter.filterBillsByAmount(mFriends), mSessionManager.getUserCurrencySymbol());
        mFriendList.setAdapter(listAdapter);

    }

    private class FriendOweFilter implements BillFilterByAmount {
        ArrayList<FriendTransactions> filterData = new ArrayList<>();

        @Override
        public ArrayList<FriendTransactions> filterBillsByAmount(ArrayList<FriendTransactions> bills) {
            for (FriendTransactions friendBill : bills) {
                if (friendBill.getAmount() > 0) {
                    filterData.add(friendBill);
                } else {

                }
            }
            return filterData;
        }
    }
}
