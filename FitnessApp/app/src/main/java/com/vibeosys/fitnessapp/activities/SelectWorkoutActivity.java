package com.vibeosys.fitnessapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.SelectWorkoutAdapter;
import com.vibeosys.fitnessapp.data.WorkoutModel;
import com.vibeosys.fitnessapp.database.FitnessContract;

public class SelectWorkoutActivity extends AppCompatActivity implements SelectWorkoutAdapter.OnItemSelectedListener {

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewCustomWorkoutActivity.class));
            }
        });
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
                adapter.addWorkout(new WorkoutModel(workId, name, desc));
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
    public void onItemSelected(WorkoutModel workoutModel, int position) {
        Intent intent = new Intent(getApplicationContext(), SelectSetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(SelectSetActivity.WKM_ID, workoutModel.getWkId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
