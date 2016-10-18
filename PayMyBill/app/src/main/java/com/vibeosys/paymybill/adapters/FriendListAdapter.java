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
    private String currencySymbol;

    public FriendListAdapter(Context mContext, ArrayList<FriendTransactions> mFriendsDTOs, String currencySymbol) {
        this.mContext = mContext;
        this.mFriends = mFriendsDTOs;
        this.currencySymbol = currencySymbol;
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

        String imagePath = friend.getImage();
        if (imagePath != null || !TextUtils.isEmpty(imagePath)) {
            //viewHolder.imgProf.setImageURI(Uri.parse(imagePath));
            viewHolder.imgProf.setImageBitmap(decodeSampledBitmapFromPath(imagePath, 50, 50));
        } else {
            viewHolder.imgProf.setImageResource(R.drawable.ic_avtar);
        }

        //viewHolder.billDate.setText(dateUtils.getLocalDateInReadableFormat(friend.getDate()));
        double amount = Math.round(friend.getAmount());
        if (amount < 0) {
            viewHolder.billAmount.setText(currencySymbol + " " + String.format("%.2f", -(amount)));
        } else if (amount > 0) {
            viewHolder.billAmount.setText(currencySymbol + " " + String.format("%.2f", amount));
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
        String date = friend.getDate();
        if (date != null) {
            viewHolder.billDate.setText(mContext.getResources().getString(R.string.hint_bill_date) + " " + date);
        } else {
            viewHolder.billDate.setText(mContext.getResources().getString(R.string.hint_no_transcaion));
        }
        if (friend.getFilterBills().size() == 0) {
            viewHolder.billOwed.setText("No Bills");
            viewHolder.billAmount.setText("No Transactions Yet");
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

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth,
                                                     int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}