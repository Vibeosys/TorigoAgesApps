package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            viewHolder.friendName = (TextView) row.findViewById(R.id.friendName);
            viewHolder.friendLogo = (TextView) row.findViewById(R.id.friendLogo);
            viewHolder.layout = (LinearLayout) row.findViewById(R.id.item);
            viewHolder.itemImg = (ImageView) row.findViewById(R.id.itemImg);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        FriendsDTO friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());
        String name = friend.getName();
        if (friend.isFlagOwe()) {
            viewHolder.itemImg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.itemImg.setVisibility(View.GONE);
        }
        String[] logos = name.split(" ", 2);
        String logo1 = logos[0];
        String caseLogo = "" + logo1.charAt(0);
        if (logos.length > 1) {
            String logo2 = logos[1];
            if (!TextUtils.isEmpty(logo2)) {
                caseLogo = caseLogo + logo2.charAt(0);
            }
        }
        viewHolder.friendLogo.setText(caseLogo);
        return row;
    }

    public void itemSelected(int position) {
        mFriends.get(position).setFlagOwe(!mFriends.get(position).isFlagOwe());
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView friendName, friendLogo;
        LinearLayout layout;
        ImageView itemImg;
    }
}