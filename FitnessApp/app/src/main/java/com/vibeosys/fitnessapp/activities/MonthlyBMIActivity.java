package com.vibeosys.fitnessapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.BmiAdapter;
import com.vibeosys.fitnessapp.data.BmiData;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.BmiCalculation;

import java.util.Calendar;

public class MonthlyBMIActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MonthlyBMIActivity.class.getSimpleName();
    private RecyclerView bmiList;
    private BmiAdapter adapter;
    private EditText edtWeight, edtHeight;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_bmi);
        bmiList = (RecyclerView) findViewById(R.id.bmi_list);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        btnSave = (Button) findViewById(R.id.btnSave);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        bmiList.setLayoutManager(llm);
        adapter = new BmiAdapter(getApplicationContext());
        bmiList.setAdapter(adapter);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        adapter.clear();
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
                adapter.addItem(new BmiData(bmiId, bmi, weight, height, dateTime));
            }
            while (setCursor.moveToNext());
        }
    }

    private void insertBmi() {

        double height = 0, weight = 0;
        try {
            height = Double.parseDouble(edtHeight.getText().toString());
            weight = Double.parseDouble(edtWeight.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error to convert string into number" + e);
        }
        if (height == 0) {
            edtHeight.setError(getString(R.string.str_err_height_wrong));
            edtHeight.requestFocus();
        } else if (weight == 0) {
            edtWeight.setError(getString(R.string.str_err_weight_wrong));
            edtWeight.requestFocus();
        } else {
            ContentValues clientValues = new ContentValues();
            clientValues.put(FitnessContract.UsersBmi.BMI_RANGE, BmiCalculation.calculateBMI(height, weight));
            clientValues.put(FitnessContract.UsersBmi.BMI_WEIGHT, weight);
            clientValues.put(FitnessContract.UsersBmi.BMI_HEIGHT, height);
            clientValues.put(FitnessContract.UsersBmi.DATE_TIME, Calendar.getInstance().getTime().getTime());
            try {
                Uri insertSet = getContentResolver().insert(FitnessContract.UsersBmi.CONTENT_URI, clientValues);
                long _setId = ContentUris.parseId(insertSet);
                if (_setId > 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.
                            str_success_add_workout), Toast.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                Log.e(TAG, "Set is not added " + e.toString());
            }
            loadData();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
                insertBmi();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.monthly_bmi_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.graphs_data) {
            startActivity(new Intent(getApplicationContext(), BmiGraphReport.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
