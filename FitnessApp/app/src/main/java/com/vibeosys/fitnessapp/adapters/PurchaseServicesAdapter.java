package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.PurchaseProduct;

import java.util.List;

/**
 * Created by akshay on 13-05-2016.
 */
public class PurchaseServicesAdapter extends BaseAdapter {

    List<PurchaseProduct> products;
    private Context mContext;

    public PurchaseServicesAdapter(List<PurchaseProduct> products, Context context) {
        this.products = products;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (products != null) return products.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;
        if (row == null) {
            LayoutInflater theLayoutInflator = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflator.inflate(R.layout.row_purchase_service, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) row.findViewById(R.id.service_Type);
            viewHolder.txtDescription = (TextView) row.findViewById(R.id.service_description);
            viewHolder.txtPrice = (TextView) row.findViewById(R.id.service_rupee);

            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        final PurchaseProduct product = products.get(position);
        viewHolder.txtTitle.setText(product.getProductName());
        viewHolder.txtDescription.setText(product.getDesc());
        viewHolder.txtPrice.setText(mContext.getResources().getString(R.string.rupess_symbol) + " " + String.format("%.2f", product.getPrice()));

        return row;
    }

    private class ViewHolder {
        TextView txtTitle, txtDescription, txtPrice;
    }


}
