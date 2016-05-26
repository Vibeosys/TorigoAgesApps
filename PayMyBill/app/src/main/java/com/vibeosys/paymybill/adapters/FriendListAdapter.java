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
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by akshay on 26-05-2016.
 */
public class FriendListAdapter extends BaseAdapter {

    private final static String TAG = HistoryAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<FriendsDTO> mFriends;

    public FriendListAdapter(Context mContext, ArrayList<FriendsDTO> mFriendsDTOs) {
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
        FriendsDTO friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());
        DateUtils dateUtils = new DateUtils();
        viewHolder.billDate.setText(dateUtils.getLocalDateInReadableFormat(friend.getDate()));
        viewHolder.billAmount.setText("$ " + String.format("%.2f", friend.getAmount()));
        if (friend.isFlagOwe()) {
            viewHolder.billOwed.setText("You Owe");
            viewHolder.billOwed.setTextColor(mContext.getResources().getColor(R.color.flatRed));
            viewHolder.billAmount.setTextColor(mContext.getResources().getColor(R.color.flatRed));
        } else {
            viewHolder.billOwed.setText("Owes You");
            viewHolder.billOwed.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
            viewHolder.billAmount.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
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