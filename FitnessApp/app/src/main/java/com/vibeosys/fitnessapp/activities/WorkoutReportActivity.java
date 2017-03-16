package com.vibeosys.fitnessapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.WorkHistoryAdapter;
import com.vibeosys.fitnessapp.data.HistorySetsData;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.data.WorkoutCategory;
import com.vibeosys.fitnessapp.data.WorkoutData;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutReportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = WorkoutReportActivity.class.getSimpleName();
    private RecyclerView historyList;
    private WorkHistoryAdapter adapter;
    private static int daysCounter = 0;
    private Calendar calendar = Calendar.getInstance();
    private ImageView imgLeft, imgRight;
    private TextView txtNoRecords, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_report);
        daysCounter = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        historyList = (RecyclerView) findViewById(R.id.history_list);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        imgRight = (ImageView) findViewById(R.id.img_right);
        txtNoRecords = (TextView) findViewById(R.id.txt_no_records_for_today);
        txtDate = (TextView) findViewById(R.id.txtDate);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        historyList.setLayoutManager(llm);
        adapter = new WorkHistoryAdapter(getApplicationContext());
        historyList.setAdapter(adapter);
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        adapter.clear();
        long currentDate = 0;
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysCounter);
        if (daysCounter == 0) {
            txtDate.setText("Today");
        } else {
            txtDate.setText(DateUtils.getReadDateInFormat(calendar.getTime()));
        }
        try {
            currentDate = DateUtils.dateWithoutTime(calendar.getTime()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.WorkHistory.CONTENT_URI,
                new String[]{FitnessContract.DailyWorkout.DAILY_ID, FitnessContract.DailyWorkout.WORK_ID,
                        FitnessContract.DailyWorkout.DATE_TIME, FitnessContract.WorkOutMaster.WKM_NAME
                }, FitnessContract.DailyWorkout.DATE_TIME + "=?",
                new String[]{String.valueOf(currentDate)
                }, null);

        if (setCursor.getCount() > 0) {
            ArrayList<WorkoutData> workoutDatas = new ArrayList<>();
            setCursor.moveToFirst();
            do {
                long dailyId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkout.DAILY_ID));
                long workId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkout.WORK_ID));
                String name = setCursor.getString(setCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_NAME));
                workoutDatas.add(new WorkoutData(workId, dailyId, name));
            }
            while (setCursor.moveToNext());
            for (WorkoutData workoutData : workoutDatas) {
                adapter.addItem(workoutData);
                addSetsDetails(workoutData.getDwId());
            }
        } else {

        }

        if (adapter.getItemCount() == 0) {
            txtNoRecords.setVisibility(View.VISIBLE);
            historyList.setVisibility(View.GONE);
        } else {
            txtNoRecords.setVisibility(View.GONE);
            historyList.setVisibility(View.VISIBLE);
        }
    }

    private void addSetsDetails(long dailyId) {

        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsHistory.CONTENT_URI,
                new String[]{FitnessContract.DailyWorkoutSets.DW_ID, FitnessContract.DailyWorkoutSets.DW_SET_ID,
                        FitnessContract.DailyWorkoutSets.DW_REPETITION, FitnessContract.DailyWorkoutSets.DW_DATE_TIME,
                        FitnessContract.SetsMaster.SET_NAME
                }, FitnessContract.DailyWorkoutSets.SETS_DAILY_ID + "=?",
                new String[]{String.valueOf(dailyId)
                }, null);

        if (setCursor.getCount() > 0) {
            ArrayList<HistorySetsData> historyData = new ArrayList<>();
            setCursor.moveToFirst();
            do {
                long setsDailyId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkoutSets.DW_ID));
                long setId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkoutSets.DW_SET_ID));
                int repetition = setCursor.getInt(setCursor.getColumnIndex(FitnessContract.DailyWorkoutSets.DW_REPETITION));
                String name = setCursor.getString(setCursor.getColumnIndex(FitnessContract.SetsMaster.SET_NAME));
                historyData.add(new HistorySetsData(setsDailyId, name, repetition, setId));

            }
            while (setCursor.moveToNext());
            for (HistorySetsData historySets : historyData) {
                ArrayList<NoOfSetsData> repetitionData = getRepetitionData(historySets.getDailySetId());
                WorkoutCategory category = getCategory(historySets.getSetId());
                historySets.setCategory(category);
                historySets.setRepetitionData(repetitionData);
                adapter.addItem(historySets);
            }
        } else {

        }

    }

    private ArrayList<NoOfSetsData> getRepetitionData(long setsDailyId) {
        ArrayList<NoOfSetsData> noOfSetsDatas = new ArrayList<>();
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsRepetition.CONTENT_URI,
                new String[]{FitnessContract.SetsRepetition.REP_ID, FitnessContract.SetsRepetition.REP_NO_REP,
                        FitnessContract.SetsRepetition.REP_MEASURE, FitnessContract.SetsRepetition.REP_DATE_TIME,
                }, FitnessContract.SetsRepetition.REP_DW_ID + "=?",
                new String[]{String.valueOf(setsDailyId)
                }, null);

        if (setCursor.getCount() > 0) {
            noOfSetsDatas.clear();
            setCursor.moveToFirst();
            do {
                long repId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_ID));
                int repetition = setCursor.getInt(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_NO_REP));
                long dateTime = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_DATE_TIME));
                double measures = setCursor.getDouble(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_MEASURE));
                noOfSetsDatas.add(new NoOfSetsData(repId, setsDailyId, repetition, dateTime, measures));
            }
            while (setCursor.moveToNext());
        }
        return noOfSetsDatas;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_left:
                daysCounter = daysCounter - 1;
                loadData();
                break;
            case R.id.img_right:
                daysCounter = daysCounter + 1;
                loadData();
                break;
        }
    }

    public WorkoutCategory getCategory(long setsDataId) {
        long categoryId = 0;
        String categoryName = "", categoryUnit = "", categoryMeasures = "";
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsMaster.CONTENT_URI,
                new String[]{FitnessContract.SetsMaster.SET_CATEGORY_ID},
                FitnessContract.SetsMaster.SET_ID + "=?", new String[]{String.valueOf(setsDataId)
                }, null);
        if (setCursor.getCount() > 0) {
            setCursor.moveToFirst();
            categoryId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsMaster.SET_CATEGORY_ID));
            if (categoryId > 0) {
                Cursor categoryCursor = getApplicationContext().getContentResolver().query(FitnessContract.WorkCategory.CONTENT_URI,
                        new String[]{FitnessContract.WorkCategory.CAT_NAME,
                                FitnessContract.WorkCategory.CAT_UNIT, FitnessContract.WorkCategory.CAT_MEASURE
                        }, FitnessContract.WorkCategory.CAT_ID + "=?",
                        new String[]{String.valueOf(categoryId)
                        }, null);

                if (categoryCursor.getCount() > 0) {
                    categoryCursor.moveToFirst();
                    categoryName = categoryCursor.getString(categoryCursor.getColumnIndex(FitnessContract.WorkCategory.CAT_NAME));
                    categoryUnit = categoryCursor.getString(categoryCursor.getColumnIndex(FitnessContract.WorkCategory.CAT_UNIT));
                    categoryMeasures = categoryCursor.getString(categoryCursor.getColumnIndex(FitnessContract.WorkCategory.CAT_MEASURE));
                } else {
                    Log.d(TAG, "Category data is not found" + categoryId);
                }
            }
        } else {
            Log.d(TAG, "## Category not found for " + setsDataId);
        }


        return new WorkoutCategory(categoryId, categoryName, categoryUnit, categoryMeasures);
    }

}
