package com.vibeosys.lawyerdiary.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.CaseData;

import java.util.ArrayList;

/**
 * Created by akshay on 12-09-2016.
 */
public class CaseAdapter extends BaseAdapter {

    private ArrayList<CaseData> mData;
    private Context mContext;

    public CaseAdapter(ArrayList<CaseData> mData, Context mContext) {
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
            row = theLayoutInflator.inflate(R.layout.row_case_data, null);
            viewHolder = new ViewHolder();
            viewHolder.txtCaseName = (TextView) row.findViewById(R.id.txtCaseName);
            viewHolder.txtCaseLogo = (TextView) row.findViewById(R.id.txtLogo);
            viewHolder.txtCaseDesc = (TextView) row.findViewById(R.id.txtDescription);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        CaseData caseData = mData.get(position);
        String caseName = caseData.getCaseName();
        String caseDesc = caseData.getCaseDate();
        viewHolder.txtCaseName.setText(caseName);
        if (TextUtils.isEmpty(caseDesc)) {
            viewHolder.txtCaseDesc.setText(mContext.getResources().getString(R.string.str_no_case_desc_found));
        } else {
            viewHolder.txtCaseDesc.setText(caseName);
        }
        String[] logos = caseName.split(" ", 2);
        String logo1 = logos[0];
        String caseLogo = "" + logo1.charAt(0);
        if (logos.length > 1) {
            String logo2 = logos[1];
            if (!TextUtils.isEmpty(logo2)) {
                caseLogo = caseLogo + logo2.charAt(0);
            }
        }

        viewHolder.txtCaseLogo.setText(caseLogo);
        return row;
    }

    private class ViewHolder {
        TextView txtCaseName, txtCaseLogo, txtCaseDesc;
    }
}
