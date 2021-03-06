package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendBills;
import com.vibeosys.paymybill.data.HistoryDTO;
import com.vibeosys.paymybill.util.AppConstants;

import java.util.ArrayList;

/**
 * Created by akshay on 25-05-2016.
 */
public class SettleAdapter extends BaseAdapter {

    private final static String TAG = SettleAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<FriendBills> historyDTOs;
    private String currencySymbol;

    public SettleAdapter(Context mContext, ArrayList<FriendBills> historyDTOs, String currencySymbol) {
        this.mContext = mContext;
        this.historyDTOs = historyDTOs;
        this.currencySymbol = currencySymbol;
    }

    @Override
    public int getCount() {
        return historyDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return historyDTOs.get(position);
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
            row = theLayoutInflator.inflate(R.layout.row_history_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.txtBillDesc = (TextView) row.findViewById(R.id.txtBillDesc);
            viewHolder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            viewHolder.txtType = (TextView) row.findViewById(R.id.txtType);
            viewHolder.txtAmount = (TextView) row.findViewById(R.id.txtAmount);

            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        FriendBills friendBills = historyDTOs.get(position);
        Log.d(TAG, friendBills.toString());
        String billDesc = friendBills.getBillDesc();
        if (TextUtils.isEmpty(billDesc)) {
            billDesc = "No description";
        }
        viewHolder.txtBillDesc.setText(billDesc);
        viewHolder.txtDate.setText(friendBills.getDate());
        double amount = friendBills.getFriendBillAmount();
        amount = amount < 0 ? -(amount) : amount;

        viewHolder.txtAmount.setText(String.format("%.2f", amount));
        if (friendBills.getBillType() == AppConstants.BILL_TYPE_SETTLEMENT) {
            viewHolder.txtType.setText("Settled with");
            viewHolder.txtType.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
            viewHolder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
        } else {
            if (friendBills.getType() == BorrowType.YOU_OWE) {
                viewHolder.txtType.setText("You Owe");
                viewHolder.txtType.setTextColor(mContext.getResources().getColor(R.color.flatRed));
                viewHolder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.flatRed));
            } else if (friendBills.getType() == BorrowType.OWES_YOU) {
                viewHolder.txtType.setText("Owes You");
                viewHolder.txtType.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
                viewHolder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.flatGreen));
            }
        }


        return row;
    }

    private class ViewHolder {
        TextView txtBillDesc;
        TextView txtDate;
        TextView txtType;
        TextView txtAmount;
    }
}
