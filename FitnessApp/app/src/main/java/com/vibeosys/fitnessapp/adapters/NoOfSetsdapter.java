package com.vibeosys.fitnessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.vibeosys.fitnessapp.data.WorkoutCategory;
import com.vibeosys.fitnessapp.data.WorkoutData;

import java.util.ArrayList;

/**
 * Created by akshay on 09-03-2017.
 */
public class NoOfSetsdapter extends RecyclerView.Adapter<NoOfSetsdapter.ItemViewHolder> {


    private static final String TAG = NoOfSetsdapter.class.getSimpleName();
    private static ArrayList<NoOfSetsData> data = new ArrayList<>();
    private Context context;
    private OnButtonClickListener onButtonClickListener;
    private WorkoutCategory category;

    public NoOfSetsdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_no_of_rep, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final NoOfSetsData noOfSetsData = data.get(position);
        holder.info.setText(category.getCategoryMeasure() + " in " + category.getCategoryUnit());
        holder.edtWeight.setText("" + noOfSetsData.getMeasures());
//        notifyDataSetChanged();
        holder.edtNoOfRep.setText("" + noOfSetsData.getNoOfRep());
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    noOfSetsData.setMeasures(Double.parseDouble(holder.edtWeight.getText().toString()));
                    onButtonClickListener.onButtonClick(noOfSetsData, position, v);
                }
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    noOfSetsData.setMeasures(Double.parseDouble(holder.edtWeight.getText().toString()));
                    onButtonClickListener.onButtonClick(noOfSetsData, position, v);
                }
            }
        });
        /*holder.edtWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    noOfSetsData.setMeasures(Double.parseDouble(holder.edtWeight.getText().toString()));
                    if (onButtonClickListener != null) {
                        onButtonClickListener.onButtonClick(noOfSetsData, position, holder.edtWeight);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "## error to convert into doble and save it");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public NoOfSetsData removeLastItem() throws ArrayIndexOutOfBoundsException {
        NoOfSetsData setsData = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        notifyDataSetChanged();
        return setsData;
    }

    public void setCategory(WorkoutCategory category) {
        this.category = category;
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
