package com.finaqa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.finaqa.R;

import java.util.ArrayList;

/**
 * Created by shrinivas on 08-06-2017.
 */
public class SpinnerSimpleAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mString;

    public SpinnerSimpleAdapter(Context mContext, ArrayList<String> mString) {
        this.mContext = mContext;
        this.mString = mString;
    }

    @Override
    public int getCount() {
        return mString.size();
    }

    @Override
    public Object getItem(int i) {
        return mString.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {

            view = inflater.inflate(R.layout.spinner_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mTextView = (TextView) view
                    .findViewById(R.id.spinnerItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTextView.setText("" + mString.get(i).toString());
        return view;
    }

    private class ViewHolder {
        private TextView mTextView;
    }
}
