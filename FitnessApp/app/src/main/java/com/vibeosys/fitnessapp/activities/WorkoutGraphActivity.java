package com.vibeosys.fitnessapp.activities;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.HistorySetsData;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.data.WorkoutCategory;
import com.vibeosys.fitnessapp.data.WorkoutData;
import com.vibeosys.fitnessapp.data.WorkoutGraphData;
import com.vibeosys.fitnessapp.database.FitnessContract;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class WorkoutGraphActivity extends AppCompatActivity {

    private PieChartView chart;
    private PieChartData data;
    private ArrayList<WorkoutGraphData> graphDatas;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = true;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private TextView txtNoRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_graph);
        chart = (PieChartView) findViewById(R.id.chart);
        txtNoRecords = (TextView) findViewById(R.id.txt_no_records_for_today);

    }


    @Override
    protected void onResume() {
        super.onResume();
        graphDatas = new ArrayList<>();
        loadData();

    }

    private void generateGraph() {
        int numValues = graphDatas.size();
        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelsOutside) {
            chart.setCircleFillRatio(0.7f);
        } else {
            chart.setCircleFillRatio(1.0f);
        }
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < graphDatas.size(); ++i) {
            SliceValue sliceValue = new SliceValue(graphDatas.get(i).getRepetition(), ChartUtils.pickColor());
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        chart.setPieChartData(data);

    }

    private void loadData() {
        ArrayList<Long> workIds = new ArrayList<>();
        Cursor clientCursor = getApplicationContext().getContentResolver().query(FitnessContract.WorkOutMaster.CONTENT_URI,
                new String[]{FitnessContract.WorkOutMaster.WKM_ID}, null, null, null);

        if (clientCursor.getCount() > 0) {
            clientCursor.moveToFirst();
            do {
                long workId = clientCursor.getLong(clientCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_ID));
                workIds.add(workId);
            }
            while (clientCursor.moveToNext());
        }

        for (long workId : workIds) {
            getData(workId);
        }
    }

    public void getData(long workOutId) {
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.WorkHistory.CONTENT_URI,
                new String[]{FitnessContract.DailyWorkout.DAILY_ID, FitnessContract.DailyWorkout.WORK_ID,
                        FitnessContract.DailyWorkout.DATE_TIME, FitnessContract.WorkOutMaster.WKM_NAME
                }, FitnessContract.DailyWorkout.WORK_ID + "=?",
                new String[]{String.valueOf(workOutId)
                }, null);

        if (setCursor.getCount() > 0) {

            setCursor.moveToFirst();
            do {
                long dailyId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkout.DAILY_ID));
                long workId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.DailyWorkout.WORK_ID));
                String name = setCursor.getString(setCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_NAME));
                graphDatas.add(new WorkoutGraphData(dailyId, name));
            }
            while (setCursor.moveToNext());
            setCursor.close();
        }

        if (graphDatas.size() > 0) {
            for (WorkoutGraphData workoutData : graphDatas) {
                addSetsDetails(workoutData);
            }
            chart.setVisibility(View.VISIBLE);
            txtNoRecords.setVisibility(View.GONE);
            generateGraph();
        } else {
            chart.setVisibility(View.GONE);
            txtNoRecords.setVisibility(View.VISIBLE);
        }


    }


    private void addSetsDetails(WorkoutGraphData workoutGraphData) {

        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsHistory.CONTENT_URI,
                new String[]{FitnessContract.DailyWorkoutSets.DW_ID, FitnessContract.DailyWorkoutSets.DW_SET_ID,
                        FitnessContract.DailyWorkoutSets.DW_REPETITION, FitnessContract.DailyWorkoutSets.DW_DATE_TIME,
                        FitnessContract.SetsMaster.SET_NAME
                }, FitnessContract.DailyWorkoutSets.SETS_DAILY_ID + "=?",
                new String[]{String.valueOf(workoutGraphData.getWkId())
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
                workoutGraphData.setRepetition(workoutGraphData.getRepetition() + repetitionData.size());
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

}
