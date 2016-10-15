package com.vibeosys.paymybill.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.paymybill.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by akshay on 15-10-2016.
 */
public class CountryAdapter extends BaseAdapter {
    private Context mContext;
    List<Locale> locales = new ArrayList<>();

    public CountryAdapter(Context mContext) {
        this.mContext = mContext;
        Locale[] locale = Locale.getAvailableLocales();
        for (Locale loc : locale) {
            String country = loc.getDisplayCountry();
            if (country.length() > 0 && !locales.contains(loc)) {
                locales.add(loc);
            }
        }
    }

    @Override
    public int getCount() {
        return locales.size();
    }

    @Override
    public Object getItem(int position) {
        return locales.get(position);
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

            LayoutInflater theLayoutInflater = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflater.inflate(R.layout.country_spinner_row, null);
            viewHolder = new ViewHolder();
            viewHolder.spinnerChild = (TextView) row.findViewById(R.id.txtSpinnerChild);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        Locale locale = locales.get(position);
        viewHolder.spinnerChild.setText(locale.getDisplayCountry());

        return row;
    }

    private class ViewHolder {
        TextView spinnerChild;
    }
   /* public void addItem(final TypeDataDTO item) {
        mCategories.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        mCategories.clear();
    }*/
}