package com.vibeosys.fitnessapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.SelectWorkoutAdapter;
import com.vibeosys.fitnessapp.adapters.SetsAdapter;
import com.vibeosys.fitnessapp.data.SetsData;
import com.vibeosys.fitnessapp.database.FitnessContract;

import java.util.Calendar;

public class SelectSetActivity extends BaseActivity implements View.OnClickListener,
        SetsAdapter.OnItemSelectedListener {

    private static final String TAG = SelectSetActivity.class.getSimpleName();
    public static final String WKM_ID = "WorkoutId";
    private RecyclerView setsList;
    private SetsAdapter adapter;
    private long workoutId = 0;
    private Bundle bundle = null;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_set);
        setsList = (RecyclerView) findViewById(R.id.sets_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            workoutId = bundle.getLong(WKM_ID);
        }
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewCustomSetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(SelectSetActivity.WKM_ID, workoutId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
        btnFinish.setOnClickListener(this);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        setsList.setLayoutManager(llm);
        adapter = new SetsAdapter(getApplicationContext());
        adapter.setOnItemSelectedListener(this);
        setsList.setAdapter(adapter);
        //loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        if (appWorkoutData.getSetId() > 0) {
            Intent intent = new Intent(getApplicationContext(), SetRepititionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(SetRepititionActivity.SET_DATA, appWorkoutData.getSetId());
            bundle.putLong(SetRepititionActivity.DAILY_WORK_ID, appWorkoutData.getDailyWorkId());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private void loadData() {
        adapter.clear();
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsMaster.CONTENT_URI,
                new String[]{FitnessContract.SetsMaster.SET_ID, FitnessContract.SetsMaster.SET_NAME,
                        FitnessContract.SetsMaster.SET_DESC
                }, FitnessContract.SetsMaster.SET_WKM_ID + "=?",
                new String[]{String.valueOf(workoutId)
                }, null);

        if (setCursor.getCount() > 0) {
            setCursor.moveToFirst();
            do {
                long setId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsMaster.SET_ID));
                String name = setCursor.getString(setCursor.getColumnIndex(FitnessContract.SetsMaster.SET_NAME));
                String desc = setCursor.getString(setCursor.getColumnIndex(FitnessContract.SetsMaster.SET_DESC));
                Log.d(TAG, "## " + setId + ", " + name + ", " + desc);
                adapter.addWorkout(new SetsData(setId, name, desc));
            }
            while (setCursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_finish:
                sharedPrefManager.setLastDate(0);
                sharedPrefManager.setWorkId(0);
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(SetsData setsData, int position) {
        saveWorkOutSet(setsData, position);

    }

    private void saveWorkOutSet(SetsData setsData, int position) {
        ContentValues clientValues = new ContentValues();
        clientValues.put(FitnessContract.DailyWorkout.DW_SET_ID, setsData.getSetId());
        clientValues.put(FitnessContract.DailyWorkout.DW_DATE_TIME, "" + Calendar.getInstance().getTime().getTime());
        clientValues.put(FitnessContract.DailyWorkout.DW_REPETITION, 0);
        clientValues.put(FitnessContract.DailyWorkout.DW_USER_ID, 1);
        try {
            Uri insertCase = getContentResolver().insert(FitnessContract.DailyWorkout.CONTENT_URI, clientValues);
            long _dailyWorkId = ContentUris.parseId(insertCase);
            if (_dailyWorkId > 0) {
                sharedPrefManager.setSetId(setsData.getSetId());
                sharedPrefManager.setDailyWorkId(_dailyWorkId);
                Toast.makeText(getApplicationContext(), getString(R.string.
                        str_success_add_workout), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SetRepititionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(SetRepititionActivity.SET_DATA, setsData.getSetId());
                bundle.putLong(SetRepititionActivity.DAILY_WORK_ID, _dailyWorkId);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        } catch (SQLException e) {
            Log.e(TAG, "Daily Workout is not added " + e.toString());
        }
    }
}
