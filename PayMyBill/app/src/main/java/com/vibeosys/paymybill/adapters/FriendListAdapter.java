package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by akshay on 26-05-2016.
 */
public class FriendListAdapter extends BaseAdapter {

    private final static String TAG = FriendListAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<FriendTransactions> mFriends;

    public FriendListAdapter(Context mContext, ArrayList<FriendTransactions> mFriendsDTOs) {
        this.mContext = mContext;
        this.mFriends = mFriendsDTOs;
    }

    @Override
    public int getCount() {
        return mFriends.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;

        if (row == null) {
            LayoutInflater theLayoutInflator = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflator.inflate(R.layout.row_friend_list, null);
            viewHolder = new ViewHolder();
            viewHolder.imgProf = (ImageView) row.findViewById(R.id.imgProf);
            viewHolder.friendName = (TextView) row.findViewById(R.id.friendName);
            viewHolder.billDate = (TextView) row.findViewById(R.id.billDate);
            viewHolder.billAmount = (TextView) row.findViewById(R.id.billAmount);
            viewHolder.billOwed = (TextView) row.findViewById(R.id.billOwed);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        FriendTransactions friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());
        DateUtils dateUtils = new DateUtils();
        //viewHolder.billDate.setText(dateUtils.getLocalDateInReadableFormat(friend.getDate()));
        double amount = Math.round(friend.getAmount());
        if (amount < 0) {
            viewHolder.billAmount.setText("$ " + String.format("%.2f", -(amount)));
        } else if (amount > 0) {
            viewHolder.billAmount.setText("$ " + String.format("%.2f", amount));
        }
        if (friend.getType() == BorrowType.YOU_OWE) {
            viewHolder.billOwed.setText("You Owe");
            viewHolder.billOwed.setTextColor(mContext.getResources().getColor(R.color.flatRed));
            viewHolder.billAmount.setTextColor(mContext.getResources().getColor(R.color.flatRed));
        } else if (friend.getType() == BorrowType.OWES_YOU) {
            viewHolder.billOwed.setText("Owes You");
            viewHolder.billOwed.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
            viewHolder.billAmount.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
        }
        if (amount == 0) {
            viewHolder.billOwed.setText("All Bills");
            viewHolder.billAmount.setText("Settled");
            viewHolder.billOwed.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
            viewHolder.billAmount.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
        }
        return row;
    }

    private class ViewHolder {
        ImageView imgProf;
        TextView friendName;
        TextView billDate;
        TextView billAmount;
        TextView billOwed;
    }
}