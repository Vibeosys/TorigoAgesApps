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
import com.vibeosys.paymybill.adapters.FriendGridAdapter;
import com.vibeosys.paymybill.adapters.FriendListAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactionHandler;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.FriendsDTO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by shrinivas on 26-05-2016.
 */
public class AllFriendFragment extends BaseFragment {
    ListView mFriendList;
    FriendListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_list, container, false);
        mFriendList = (ListView) view.findViewById(R.id.dashBoard_list);
        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), SettleActivity.class));
            }
        });
        createList();
        return view;
    }

    private void createList() {
       /* ArrayList<FriendsDTO> friendsDTOs = new ArrayList<>();
        friendsDTOs.add(new FriendsDTO(1, "Prakash Dhole", "prakash.jpg", 30.78, new java.util.Date(), true));
        friendsDTOs.add(new FriendsDTO(1, "Ganesh", "prakash.jpg", 30.78, new java.util.Date(), false));
        friendsDTOs.add(new FriendsDTO(1, "Rajesh Farande", "prakash.jpg", 30.78, new java.util.Date(), false));
        friendsDTOs.add(new FriendsDTO(1, "Vinayak", "prakash.jpg", 30.78, new java.util.Date(), true));
        friendsDTOs.add(new FriendsDTO(1, "Krushna", "prakash.jpg", 30.78, new java.util.Date(), true));*/
        FriendTransactionHandler friendTransactionHandler = new FriendTransactionHandler(mDbRepository);

        ArrayList<FriendTransactions> mFriends = friendTransactionHandler.getFriendList();
        Log.d(TAG, mFriends.toString());
        listAdapter = new FriendListAdapter(getContext(), mFriends);
        mFriendList.setAdapter(listAdapter);

    }
}
