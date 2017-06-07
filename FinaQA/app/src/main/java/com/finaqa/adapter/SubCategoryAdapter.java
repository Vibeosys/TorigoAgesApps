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
import java.util.List;

/**
 * Created by shrinivas on 06-06-2017.
 */
public class SubCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mSubCategory;

    public SubCategoryAdapter(Context mContext, List<String> mSubCategory) {
        this.mContext = mContext;
        this.mSubCategory = mSubCategory;
    }

    @Override
    public int getCount() {
        return mSubCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return mSubCategory.indexOf(i);
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

            view = inflater.inflate(R.layout.layout_spineer_sub_category, viewGroup, false);
            holder = new ViewHolder();
            holder.mTextView = (TextView) view
                    .findViewById(R.id.textViewSubCategory);
            holder.mCheckBox = (CheckBox) view
                    .findViewById(R.id.checkboxSubCategory);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTextView.setText("" + mSubCategory.get(i).toString());
        return view;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
