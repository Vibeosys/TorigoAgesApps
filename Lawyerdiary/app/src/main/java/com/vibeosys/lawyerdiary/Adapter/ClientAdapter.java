package com.vibeosys.lawyerdiary.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;

import java.util.ArrayList;

/**
 * Created by akshay on 12-09-2016.
 */
public class ClientAdapter extends BaseAdapter {

    private ArrayList<Client> mData;
    private Context mContext;

    public ClientAdapter(ArrayList<Client> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            row = theLayoutInflator.inflate(R.layout.row_client_data, null);
            viewHolder = new ViewHolder();
            viewHolder.txtClientName = (TextView) row.findViewById(R.id.txtClientName);
            viewHolder.txtLogo = (TextView) row.findViewById(R.id.txtLogo);
            viewHolder.txtPhNO = (TextView) row.findViewById(R.id.txtPhNo);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        Client client = mData.get(position);
        String clientName = client.getName();
        String clientPhNo = client.getPhoneNumber();
        viewHolder.txtClientName.setText(clientName);
        viewHolder.txtPhNO.setText(clientPhNo);

        String[] logos = clientName.split(" ", 2);
        String logo1 = logos[0];
        String caseLogo = "" + logo1.charAt(0);
        if (logos.length > 1) {
            String logo2 = logos[1];
            if (!TextUtils.isEmpty(logo2)) {
                caseLogo = caseLogo + logo2.charAt(0);
            }
        }

        viewHolder.txtLogo.setText(caseLogo);
        return row;
    }

    private class ViewHolder {
        TextView txtClientName, txtLogo, txtPhNO;
    }
}