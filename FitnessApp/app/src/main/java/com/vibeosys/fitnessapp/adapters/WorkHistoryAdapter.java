package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.HistorySetsData;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.data.WorkoutData;

import java.util.ArrayList;

/**
 * Created by akshay on 14-03-2017.
 */
public class WorkHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> data = new ArrayList<>();
    private final int WORKOUT = 0, SETS = 1;
    private Context context;

    public WorkHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case WORKOUT:
                View workoutView = inflater.inflate(R.layout.row_history_workout, parent, false);
                viewHolder = new WorkOutViewHolder(workoutView);
                break;
            case SETS:
                View setView = inflater.inflate(R.layout.row_history_set, parent, false);
                viewHolder = new SetViewHolder(setView);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case WORKOUT:
                WorkOutViewHolder workOutViewHolder = (WorkOutViewHolder) holder;
                bindWorkoutView(workOutViewHolder, position);
                break;
            case SETS:
                SetViewHolder setViewHolder = (SetViewHolder) holder;
                bindSetViewHolder(setViewHolder, position);
                break;
            default:
                RecyclerViewSimpleTextViewHolder rSimpleText = (RecyclerViewSimpleTextViewHolder) holder;
                break;
        }
    }


    private void bindWorkoutView(WorkOutViewHolder workOutViewHolder, int position) {
        WorkoutData workoutData = (WorkoutData) data.get(position);
        if (workoutData != null)
            workOutViewHolder.tvWorkName.setText(workoutData.getWkName());
    }

    private void bindSetViewHolder(SetViewHolder setViewHolder, int position) {
        HistorySetsData historySetsData = (HistorySetsData) data.get(position);
        if (historySetsData != null) {
            setViewHolder.tvSetName.setText(historySetsData.getSetName());
            setViewHolder.layRepetition.removeAllViews();
            for (NoOfSetsData noOfSetsData : historySetsData.getRepetitionData()) {
                LayoutInflater theLayoutInflator = (LayoutInflater) context.getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                View row = theLayoutInflator.inflate(R.layout.row_history_set_details, null);
                TextView txtMeasure = (TextView) row.findViewById(R.id.txt_measures);
                TextView txtUnit = (TextView) row.findViewById(R.id.txt_unit);
                TextView repetition = (TextView) row.findViewById(R.id.txt_no_of_rep);
                txtMeasure.setText(String.format("%.2f", noOfSetsData.getMeasures()));
                //txtUnit.setText(String.valueOf(noOfSetsData.get()));
                repetition.setText("" + noOfSetsData.getNoOfRep());
                setViewHolder.layRepetition.addView(row);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof WorkoutData) {
            return WORKOUT;
        } else if (data.get(position) instanceof HistorySetsData) {
            return SETS;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    public class WorkOutViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvWorkName;

        public WorkOutViewHolder(View itemView) {
            super(itemView);
            tvWorkName = (TextView) itemView.findViewById(R.id.txt_workout_name);
        }
    }

    public class SetViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvSetName;
        protected LinearLayout layRepetition;

        public SetViewHolder(View itemView) {
            super(itemView);
            tvSetName = (TextView) itemView.findViewById(R.id.tvSetName);
            layRepetition = (LinearLayout) itemView.findViewById(R.id.sets_repetition);
        }
    }

    private class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewSimpleTextViewHolder(View v) {
            super(v);
        }
    }

    public void addItem(Object object) {
        data.add(object);
        notifyDataSetChanged();
    }
}
