package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.FriendListAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.BillFilterByAmount;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactionHandler;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;

import java.util.ArrayList;

public class AllFriendsActivity extends BaseActivity {
    private static final String TAG = AllFriendsActivity.class.getSimpleName();
    private ListView mFriendList;
    private FriendListAdapter listAdapter;
    protected FriendTransactionHandler friendTransactionHandler;
    protected ArrayList<FriendTransactions> mFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_friends);
        setTitle(getResources().getString(R.string.str_my_friennd));
        mFriendList = (ListView) findViewById(R.id.dashBoard_list);
        createList();
        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendTransactions friendTransactions = (FriendTransactions) listAdapter.getItem(position);
                Intent iSettle = new Intent(getApplicationContext(), SettleActivity.class);
                iSettle.putExtra("data", friendTransactions);
                startActivity(iSettle);
            }
        });
    }

    private void createList() {
        friendTransactionHandler = new FriendTransactionHandler(mDbRepository);
        BillFilterByAmount zeroAmount = new FilterMyName();
        mFriends = zeroAmount.filterBillsByAmount(friendTransactionHandler.getFriendList());
        listAdapter = new FriendListAdapter(getApplicationContext(), mFriends, mSessionManager.getUserCurrencySymbol());
        mFriendList.setAdapter(listAdapter);

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
