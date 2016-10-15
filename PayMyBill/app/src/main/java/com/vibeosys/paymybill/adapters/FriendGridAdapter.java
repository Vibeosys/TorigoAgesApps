package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 25-05-2016.
 */
public class FriendGridAdapter extends BaseAdapter {

    private final static String TAG = FriendGridAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<FriendsDTO> mFriends;

    public FriendGridAdapter(Context mContext, ArrayList<FriendsDTO> mFriendsDTOs) {
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
            row = theLayoutInflator.inflate(R.layout.row_friend_grid, null);
            viewHolder = new ViewHolder();
            viewHolder.imgProf = (ImageView) row.findViewById(R.id.imgProf);
            viewHolder.friendName = (TextView) row.findViewById(R.id.friendName);
            viewHolder.layout = (LinearLayout) row.findViewById(R.id.item);
            viewHolder.itemImg = (ImageView) row.findViewById(R.id.itemImg);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        FriendsDTO friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());
        if (friend.isFlagOwe()) {
            viewHolder.itemImg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.itemImg.setVisibility(View.GONE);
        }

        return row;
    }

    public void itemSelected(int position) {
        mFriends.get(position).setFlagOwe(!mFriends.get(position).isFlagOwe());
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView imgProf;
        TextView friendName;
        LinearLayout layout;
        ImageView itemImg;
    }
}