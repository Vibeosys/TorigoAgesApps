package com.vibeosys.fitnessapp.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.BmiData;
import com.vibeosys.fitnessapp.database.FitnessContract;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class BmiGraphReport extends AppCompatActivity {
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 0;
    private ArrayList<BmiData> bmiDatas = new ArrayList<>();
    float[][] randomNumbersTab;// = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_graph_report);
        chart = (LineChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        chart.setViewportCalculationEnabled(false);
        bmiDatas.clear();
        loadData();
        resetViewport();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void loadData() {

        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.UsersBmi.CONTENT_URI,
                new String[]{FitnessContract.UsersBmi.BMI_ID, FitnessContract.UsersBmi.BMI_RANGE,
                        FitnessContract.UsersBmi.BMI_WEIGHT, FitnessContract.UsersBmi.BMI_HEIGHT,
                        FitnessContract.UsersBmi.DATE_TIME
                }, null, null, null);

        if (setCursor.getCount() > 0) {
            setCursor.moveToFirst();
            do {
                long bmiId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.UsersBmi.BMI_ID));
                double bmi = setCursor.getDouble(setCursor.getColumnIndex(FitnessContract.UsersBmi.BMI_RANGE));
                double weight = setCursor.getDouble(setCursor.getColumnIndex(FitnessContract.UsersBmi.BMI_WEIGHT));
                double height = setCursor.getDouble(setCursor.getColumnIndex(FitnessContract.UsersBmi.BMI_HEIGHT));
                long dateTime = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.UsersBmi.DATE_TIME));
                //Log.d(TAG, "## " + bmiId + ", " + name + ", " + desc);
                bmiDatas.add(new BmiData(bmiId, bmi, weight, height, dateTime));
            }
            while (setCursor.moveToNext());
        }
        numberOfPoints = bmiDatas.size();
        randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) bmiDatas.get(j).getBmi();
            }
        }
        generateData();
    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getApplicationContext(), "Bmi: " + String.format("%.2f", value.getY()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }

}
