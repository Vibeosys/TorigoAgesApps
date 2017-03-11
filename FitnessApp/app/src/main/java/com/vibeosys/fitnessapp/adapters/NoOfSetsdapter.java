package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.data.SetsData;
import com.vibeosys.fitnessapp.data.WorkoutData;

import java.util.ArrayList;

/**
 * Created by akshay on 09-03-2017.
 */
public class NoOfSetsdapter extends RecyclerView.Adapter<NoOfSetsdapter.ItemViewHolder> {


    private static ArrayList<NoOfSetsData> data = new ArrayList<>();
    private Context context;
    private OnButtonClickListener onButtonClickListener;

    public NoOfSetsdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_no_of_rep, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final NoOfSetsData noOfSetsData = data.get(position);
        holder.edtWeight.setText(0);
        holder.edtNoOfRep.setText(noOfSetsData.getNoOfRep());
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(noOfSetsData, position, v);
                }
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(noOfSetsData, position, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public long removeLast() {
        long id = data.get(data.size()).getRepId();
        data.remove(data.size());
        return id;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected EditText edtWeight, edtNoOfRep;
        protected Button btnMinus, btnPlus;
        protected TextView info;

        public ItemViewHolder(View itemView) {
            super(itemView);
            edtWeight = (EditText) itemView.findViewById(R.id.edtWeight);
            edtNoOfRep = (EditText) itemView.findViewById(R.id.edtNoOfRep);
            btnMinus = (Button) itemView.findViewById(R.id.btnMinus);
            btnPlus = (Button) itemView.findViewById(R.id.btnPlus);
            info = (TextView) itemView.findViewById(R.id.info);
        }
    }

    public void addItem(NoOfSetsData noOfSetsData) {
        data.add(noOfSetsData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

    public interface OnButtonClickListener {
        void onButtonClick(NoOfSetsData noOfSetsData, int position, View view);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
