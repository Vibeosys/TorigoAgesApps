package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.WorkoutModel;

import java.util.ArrayList;

/**
 * Created by akshay on 09-03-2017.
 */
public class SelectWorkoutAdapter extends RecyclerView.Adapter<SelectWorkoutAdapter.ItemViewHolder> {


    private static ArrayList<WorkoutModel> data = new ArrayList<>();
    private Context context;
    private OnItemSelectedListener onItemSelectedListener;

    public SelectWorkoutAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_workout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final WorkoutModel workoutModel = data.get(position);
        holder.tvWorkName.setText(workoutModel.getWkName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(workoutModel, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvWorkName;
        protected RelativeLayout relativeReorder;
        protected RelativeLayout container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvWorkName = (TextView) itemView.findViewById(R.id.txt_workout_name);
            relativeReorder = (RelativeLayout) itemView.findViewById(R.id.relativeReorder);
            container = (RelativeLayout) itemView.findViewById(R.id.container);
        }
    }

    public void addWorkout(WorkoutModel workout) {
        data.add(workout);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(WorkoutModel workoutModel, int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
