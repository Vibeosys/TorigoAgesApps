package com.vibeosys.paymybill.adapters;

import android.content.Context;
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

    public HistoryAdapter(Context mContext, ArrayList<HistoryDTO> historyDTOs) {
        this.mContext = mContext;
        this.historyDTOs = historyDTOs;
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
        viewHolder.txtBillDesc.setText(history.getBillDesc());
        viewHolder.txtDate.setText(history.getBillDate());
        viewHolder.txtAmount.setText(String.format("%.2f", history.getAmount()));
        return row;
    }

    private class ViewHolder {
        TextView txtBillDesc;
        TextView txtDate;
        TextView txtType;
        TextView txtAmount;
    }
}
