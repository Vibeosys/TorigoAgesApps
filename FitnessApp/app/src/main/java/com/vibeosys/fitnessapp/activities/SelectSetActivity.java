package com.vibeosys.fitnessapp.activities;

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
import com.vibeosys.fitnessapp.adapters.SetsAdapter;
import com.vibeosys.fitnessapp.data.SetsModel;
import com.vibeosys.fitnessapp.data.WorkoutModel;
import com.vibeosys.fitnessapp.database.FitnessContract;

public class SelectSetActivity extends AppCompatActivity {

    private static final String TAG = SelectSetActivity.class.getSimpleName();
    public static final String WKM_ID = "WorkoutId";
    private RecyclerView setsList;
    private SetsAdapter adapter;
    private long workoutId = 0;
    private Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_set);
        setsList = (RecyclerView) findViewById(R.id.sets_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            //isNewProduct = bundle.getBoolean(IS_NEW_PRODUCT);
            workoutId = bundle.getLong(WKM_ID);
            //showData();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        setsList.setLayoutManager(llm);
        adapter = new SetsAdapter(getApplicationContext());
        setsList.setAdapter(adapter);
        //loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        adapter.clear();
        Cursor clientCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsMaster.CONTENT_URI,
                new String[]{FitnessContract.SetsMaster.SET_ID, FitnessContract.SetsMaster.SET_NAME,
                        FitnessContract.SetsMaster.SET_DESC
                }, FitnessContract.SetsMaster.SET_WKM_ID + "=?",
                new String[]{String.valueOf(workoutId)
                }, null);

        if (clientCursor.getCount() > 0) {
            clientCursor.moveToFirst();
            do {
                long setId = clientCursor.getLong(clientCursor.getColumnIndex(FitnessContract.SetsMaster.SET_ID));
                String name = clientCursor.getString(clientCursor.getColumnIndex(FitnessContract.SetsMaster.SET_NAME));
                String desc = clientCursor.getString(clientCursor.getColumnIndex(FitnessContract.SetsMaster.SET_DESC));
                Log.d(TAG, "## " + setId + ", " + name + ", " + desc);
                adapter.addWorkout(new SetsModel(setId, name, desc));
            }
            while (clientCursor.moveToNext());
        }
    }

}
