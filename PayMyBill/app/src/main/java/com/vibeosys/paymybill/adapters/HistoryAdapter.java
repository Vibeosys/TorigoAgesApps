package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 25-05-2016.
 */
public class HistoryAdapter extends BaseAdapter {

    private final static String TAG = HistoryAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<HistoryDTO> historyDTOs;
    private String currencySymbol;

    public HistoryAdapter(Context mContext, ArrayList<HistoryDTO> historyDTOs, String currencySymbol) {
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
        HistoryDTO history = historyDTOs.get(position);
        Log.d(TAG, history.toString());
        String billDesc = history.getBillDesc();
        if (TextUtils.isEmpty(billDesc)) {
            billDesc = "No description";
        }
        viewHolder.txtBillDesc.setText(billDesc);
        viewHolder.txtDate.setText(history.getBillDate());
        viewHolder.txtAmount.setText(String.format("%.2f", history.getAmount()));
        viewHolder.txtType.setText("You spend");
        return row;
    }

    private class ViewHolder {
        TextView txtBillDesc;
        TextView txtDate;
        TextView txtType;
        TextView txtAmount;
    }
}
