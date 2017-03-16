package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.BmiData;
import com.vibeosys.fitnessapp.data.SetsData;
import com.vibeosys.fitnessapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akshay on 09-03-2017.
 */
public class BmiAdapter extends RecyclerView.Adapter<BmiAdapter.ItemViewHolder> {


    private static ArrayList<BmiData> data = new ArrayList<>();
    private Context context;

    public BmiAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bmi_history, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final BmiData bmiData = data.get(position);
        holder.tvBmi.setText(String.format("%.2f", bmiData.getBmi()));
        holder.tvWeight.setText(String.format("%.2f", bmiData.getWeight()));
        holder.tvDate.setText(DateUtils.getReadDateInFormat(new Date(bmiData.getDateTime())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvWeight, tvBmi, tvDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvWeight = (TextView) itemView.findViewById(R.id.txt_weight);
            tvBmi = (TextView) itemView.findViewById(R.id.txt_Bmi);
            tvDate = (TextView) itemView.findViewById(R.id.txt_date);
        }
    }

    public void addItem(BmiData bmiData) {
        data.add(bmiData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }
}
