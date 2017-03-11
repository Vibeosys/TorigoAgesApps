package com.vibeosys.fitnessapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.NoOfSetsdapter;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.database.FitnessContract;

public class SetRepititionActivity extends BaseActivity implements View.OnClickListener, NoOfSetsdapter.OnButtonClickListener {
    public static final String SET_DATA = "setData";
    public static final String DAILY_WORK_ID = "dailyWorkId";
    private static final String TAG = SetRepititionActivity.class.getSimpleName();
    private Bundle bundle;
    private long setsDataId = 0;
    private long dailyWorkId = 0;
    private Button btnMinus, btnPlus, doneSets;
    private EditText edtNoOfRep;
    private RecyclerView repetitionRecycler;
    private static int counter = 0;
    private NoOfSetsdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_repitition);
        bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        }
        counter = 0;
        setsDataId = bundle.getLong(SET_DATA);
        dailyWorkId = bundle.getLong(DAILY_WORK_ID);
        setTitle(getString(R.string.str_sets_title));
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        doneSets = (Button) findViewById(R.id.doneSets);
        edtNoOfRep = (EditText) findViewById(R.id.edtNoOfRep);
        repetitionRecycler = (RecyclerView) findViewById(R.id.no_of_repitition);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        repetitionRecycler.setLayoutManager(llm);
        adapter = new NoOfSetsdapter(getApplicationContext());
        adapter.setOnButtonClickListener(this);
        repetitionRecycler.setAdapter(adapter);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        doneSets.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        adapter.clear();
        Cursor setCursor = getApplicationContext().getContentResolver().query(FitnessContract.SetsRepetition.CONTENT_URI,
                new String[]{FitnessContract.SetsRepetition.REP_ID, FitnessContract.SetsRepetition.REP_DW_ID,
                        FitnessContract.SetsRepetition.REP_NO_REP, FitnessContract.SetsRepetition.REP_DATE_TIME
                }, FitnessContract.SetsRepetition.REP_DW_ID + "=?",
                new String[]{String.valueOf(dailyWorkId)
                }, null);

        if (setCursor.getCount() > 0) {
            setCursor.moveToFirst();
            do {
                long repId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_ID));
                long dwId = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_DW_ID));
                int repition = setCursor.getInt(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_NO_REP));
                long date = setCursor.getLong(setCursor.getColumnIndex(FitnessContract.SetsRepetition.REP_DATE_TIME));
                Log.d(TAG, "## " + repId + ", " + dwId + ", " + repition);
                adapter.addItem(new NoOfSetsData(repId, dwId, repition, date));
            }
            while (setCursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnMinus:
                if (counter > 0) {
                    counter = counter - 1;
                    int lastId=adapter.removeLast();
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.str_rep_not_zero),
                            Toast.LENGTH_SHORT).show();
                edtNoOfRep.setText("" + counter);
                break;
            case R.id.btnPlus:
                if (counter < 15) {
                    counter = counter + 1;
                    adapter.addItem(new NoOfSetsData());
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.str_reach_limit), Toast.LENGTH_SHORT).show();
                edtNoOfRep.setText("" + counter);
                break;
            case R.id.doneSets:
                saveData();
                sharedPrefManager.setSetId(0);
                sharedPrefManager.setDailyWorkId(0);
                finish();
                break;

        }
    }

    private void saveData() {
        /*ContentValues clientValues = new ContentValues();
        clientValues.put(FitnessContract.SetsRepetition.DW_SET_ID, setsData.getSetId());
        clientValues.put(FitnessContract.DailyWorkout.DW_DATE_TIME, "" + Calendar.getInstance().getTime().getTime());
        clientValues.put(FitnessContract.DailyWorkout.DW_REPETITION, 0);
        clientValues.put(FitnessContract.DailyWorkout.DW_USER_ID, 1);
        try {
            Uri insertCase = getContentResolver().insert(FitnessContract.DailyWorkout.CONTENT_URI, clientValues);
            long _dailyWorkId = ContentUris.parseId(insertCase);
            if (_dailyWorkId > 0) {
                sharedPrefManager.setDailyWorkId(_dailyWorkId);
                Toast.makeText(getApplicationContext(), getString(R.string.
                        str_success_add_workout), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SetRepititionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(SetRepititionActivity.SET_DATA, setsData.getSetId());
                bundle.putLong(SetRepititionActivity.DAILY_WORK_ID, _dailyWorkId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Daily Workout is not added " + e.toString());
        }*/
    }

    @Override
    public void onButtonClick(NoOfSetsData noOfSetsData, int position, View view) {

    }
}
