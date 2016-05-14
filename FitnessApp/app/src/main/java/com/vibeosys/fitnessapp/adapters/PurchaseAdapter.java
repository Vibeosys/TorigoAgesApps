package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.PurchaseProduct;

import java.util.List;

/**
 * Created by akshay on 13-05-2016.
 */
public class PurchaseAdapter extends BaseAdapter {

    List<PurchaseProduct> products;
    private Context mContext;
    CustomButtonListener customButtonListener;

    public PurchaseAdapter(List<PurchaseProduct> products, Context context) {
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
            row = theLayoutInflator.inflate(R.layout.service_child_element, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) row.findViewById(R.id.service_Type);
            viewHolder.txtDescription = (TextView) row.findViewById(R.id.service_description);
            viewHolder.txtPrice = (TextView) row.findViewById(R.id.service_rupee);
            viewHolder.btnPay = (Button) row.findViewById(R.id.loginbtn);
            row.setTag(viewHolder);

        } else viewHolder = (ViewHolder) row.getTag();
        final PurchaseProduct product = products.get(position);
        viewHolder.txtTitle.setText(product.getProductName());
        viewHolder.txtDescription.setText(product.getDesc());
        viewHolder.txtPrice.setText(mContext.getResources().getString(R.string.rupess_symbol) + " " + String.format("%.2f", product.getPrice()));
        viewHolder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null)
                    customButtonListener.onButtonClickListener(v.getId(), position, product.getPrice(), product);
            }
        });
        return row;
    }

    private class ViewHolder {
        TextView txtTitle, txtDescription, txtPrice;
        Button btnPay;
    }

    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customButtonListener = listener;
    }

    public interface CustomButtonListener {
        public void onButtonClickListener(int id, int position, double value, PurchaseProduct product);
    }
}
