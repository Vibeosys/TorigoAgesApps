package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.SetsModel;
import com.vibeosys.fitnessapp.data.WorkoutModel;

import java.util.ArrayList;

/**
 * Created by akshay on 09-03-2017.
 */
public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.ItemViewHolder> {


    private static ArrayList<SetsModel> data = new ArrayList<>();
    private Context context;

    public SetsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sets, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SetsModel setsModel = data.get(position);
        holder.tvSetName.setText(setsModel.getSetName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvSetName;
        protected RelativeLayout relativeReorder;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvSetName = (TextView) itemView.findViewById(R.id.txt_set_name);
            relativeReorder = (RelativeLayout) itemView.findViewById(R.id.relativeReorder);
        }
    }

    public void addWorkout(SetsModel setsModel) {
        data.add(setsModel);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }
}
