package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.util.AppConstants;

import java.util.List;

/**
 * Created by akshay on 25-05-2016.
 */
public class TransactionListAdapter extends BaseAdapter {

    private final static String TAG = HistoryAdapter.class.getSimpleName();
    private Context mContext;
    private List<FriendsDTO> mFriends;
    private AmountChangedListener amountChangedListener;
    private int spiltMode = 0;

    public TransactionListAdapter(Context mContext, List<FriendsDTO> mFriendsDTOs) {
        this.mContext = mContext;
        this.mFriends = mFriendsDTOs;
    }

    public void setSpiltMode(int splitMode) {
        this.spiltMode = splitMode;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;

        if (row == null) {
            LayoutInflater theLayoutInflator = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflator.inflate(R.layout.row_trasaction_list, null);
            viewHolder = new ViewHolder();
            viewHolder.friendName = (TextView) row.findViewById(R.id.friendName);
            viewHolder.txtAmount = (EditText) row.findViewById(R.id.txtAmount);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        final FriendsDTO friend = mFriends.get(position);
        Log.d(TAG, friend.toString());
        viewHolder.friendName.setText(friend.getName());
        viewHolder.txtAmount.setText(String.format("%.2f", friend.getAmount()));
        if (spiltMode == AppConstants.EQUALLY_DIVIDED) {
            viewHolder.txtAmount.setEnabled(false);
        } else if (spiltMode == AppConstants.UNEQUALLY_DIVIDED) {
            viewHolder.txtAmount.setEnabled(true);
        }
        viewHolder.txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    amountChangedListener.onAmountChanged(friend.getId(), s.toString(), friend);
                else
                    amountChangedListener.onAmountChanged(friend.getId(), "0", friend);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return row;
    }

    private class ViewHolder {
        TextView friendName;
        EditText txtAmount;
    }

    public void setAmountChangedListener(AmountChangedListener amountChangedListener) {
        this.amountChangedListener = amountChangedListener;
    }

    public interface AmountChangedListener {
        public void onAmountChanged(int id, String amount, FriendsDTO friendsDTO);
    }
}