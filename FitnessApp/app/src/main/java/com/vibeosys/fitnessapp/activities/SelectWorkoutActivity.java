package com.vibeosys.fitnessapp.activities;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import com.vibeosys.fitnessapp.data.SetsData;
import com.vibeosys.fitnessapp.data.WorkoutData;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;

public class SelectWorkoutActivity extends BaseActivity implements
        SelectWorkoutAdapter.OnItemSelectedListener {

    private static final String TAG = SelectWorkoutActivity.class.getSimpleName();
    private RecyclerView workoutList;
    private SelectWorkoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);
        workoutList = (RecyclerView) findViewById(R.id.workout_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewCustomWorkoutActivity.class));
            }
        });*/

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        workoutList.setLayoutManager(llm);
        adapter = new SelectWorkoutAdapter(getApplicationContext());
        adapter.setOnItemSelectedListener(this);
        workoutList.setAdapter(adapter);

        //loadData();

    }


    private void loadData() {
        adapter.clear();
        Cursor clientCursor = getApplicationContext().getContentResolver().query(FitnessContract.WorkOutMaster.CONTENT_URI,
                new String[]{FitnessContract.WorkOutMaster.WKM_ID, FitnessContract.WorkOutMaster.WKM_NAME,
                        FitnessContract.WorkOutMaster.WKM_DESC
                }, null, null, null);

        if (clientCursor.getCount() > 0) {
            clientCursor.moveToFirst();
            do {
                long workId = clientCursor.getLong(clientCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_ID));
                String name = clientCursor.getString(clientCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_NAME));
                String desc = clientCursor.getString(clientCursor.getColumnIndex(FitnessContract.WorkOutMaster.WKM_DESC));
                Log.d(TAG, "## " + workId + ", " + name + ", " + desc);
                adapter.addWorkout(new WorkoutData(workId, name, desc));
            }
            while (clientCursor.moveToNext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onItemSelected(WorkoutData workoutData, int position) {
        insertDailyWork(workoutData);
    }


    protected void confirmationAlertDialog(final WorkoutData workoutData, long dailyId) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.str_confirm_workout_title))
                .setMessage(getString(R.string.str_confirm_workout_message))
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.setWorkId(workoutData.getWkId());
                        sharedPrefManager.setLastDate(Calendar.getInstance().getTime().getTime());
                        Intent intent = new Intent(getApplicationContext(), SelectSetActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong(SelectSetActivity.WKM_ID, workoutData.getWkId());
                        bundle.putLong(SelectSetActivity.DAILY_WORK_ID, workoutData.getWkId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).create().show();
    }

    private void insertDailyWork(WorkoutData workoutData) {
        long currentDate = 0;
        try {
            currentDate = DateUtils.dateWithoutTime(Calendar.getInstance().getTime()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor dailyWoCursor = getApplicationContext().getContentResolver().query(FitnessContract.DailyWorkout.CONTENT_URI,
                new String[]{FitnessContract.DailyWorkout.DAILY_ID
                }, FitnessContract.DailyWorkout.WORK_ID + "=? AND " + FitnessContract.DailyWorkout.DATE_TIME + "=?",
                new String[]{String.valueOf(workoutData.getWkId()), String.valueOf(currentDate)
                }, null);

        if (dailyWoCursor.getCount() > 0) {
            dailyWoCursor.moveToFirst();
            long dailyWoId = dailyWoCursor.getLong(dailyWoCursor.getColumnIndex(FitnessContract.DailyWorkout.DAILY_ID));
            confirmationAlertDialog(workoutData, dailyWoId);
        } else {
            saveWorkOutSet(workoutData);
        }
    }

    private void saveWorkOutSet(WorkoutData workoutData) {
        ContentValues clientValues = new ContentValues();
        clientValues.put(FitnessContract.DailyWorkout.WORK_ID, workoutData.getWkId());
        try {
            clientValues.put(FitnessContract.DailyWorkout.DATE_TIME, "" + DateUtils.dateWithoutTime(Calendar.getInstance().getTime()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Uri insertCase = getContentResolver().insert(FitnessContract.DailyWorkout.CONTENT_URI, clientValues);
            long _dailyWorkId = ContentUris.parseId(insertCase);
            if (_dailyWorkId > 0) {
                confirmationAlertDialog(workoutData, _dailyWorkId);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Daily Workout is not added " + e.toString());
        }
    }

}
