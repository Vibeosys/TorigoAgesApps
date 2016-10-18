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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 25-05-2016.
 */
public class SelectedFriendAdapter extends BaseAdapter {

    private final static String TAG = SelectedFriendAdapter.class.getSimpleName();
    private Context mContext;
    private List<FriendsDTO> mFriends;

    public SelectedFriendAdapter(Context mContext, List<FriendsDTO> mFriendsDTOs) {
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
            row = theLayoutInflator.inflate(R.layout.row_selected_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.friendLogo = (TextView) row.findViewById(R.id.friendLogo);
            viewHolder.imgTickMark = (ImageView) row.findViewById(R.id.imgIsSelected);
            viewHolder.friendName = (TextView) row.findViewById(R.id.friendName);
            viewHolder.layout = (LinearLayout) row.findViewById(R.id.item);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        FriendsDTO friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());

        if (friend.isSelected()) {
            viewHolder.imgTickMark.setVisibility(View.VISIBLE);
        } else if (!friend.isSelected()) {
            viewHolder.imgTickMark.setVisibility(View.GONE);
        }
        String name = friend.getName();
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

    private class ViewHolder {
        ImageView /*imgProf,*/ imgTickMark;
        TextView friendName, friendLogo;
        LinearLayout layout;
    }

    public void selectOneUser(int position) {
        for (int i = 0; i < mFriends.size(); i++) {
            FriendsDTO friend = mFriends.get(i);
            if (i == position) {
                friend.setSelected(true);
            } else {
                friend.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}