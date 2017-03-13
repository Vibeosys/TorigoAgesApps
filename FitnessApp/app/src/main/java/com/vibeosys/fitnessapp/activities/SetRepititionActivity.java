package com.vibeosys.fitnessapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.NoOfSetsdapter;
import com.vibeosys.fitnessapp.data.NoOfSetsData;
import com.vibeosys.fitnessapp.database.FitnessContract;
import com.vibeosys.fitnessapp.utils.Typefaces;

import java.util.Calendar;

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
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_repitition);
        bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        }
        counter = 0;
        context = this;
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
            counter = setCursor.getCount();
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
        edtNoOfRep.setText("" + counter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnMinus:
                if (counter > 0) {
                    counter = counter - 1;
                    final NoOfSetsData setsData = adapter.removeLastItem();
                    long _setId = 0;
                    try {
                        _setId = getContentResolver().delete(FitnessContract.SetsRepetition.CONTENT_URI,
                                FitnessContract.SetsRepetition.REP_ID + "=?", new String[]{String.valueOf(setsData.getRepId())});
                    } catch (SQLException e) {
                        Log.e(TAG, "Repetition  is not deleted " + e.toString());
                    }
                    if (_setId > 0) {

                        final Snackbar snackbar = Snackbar
                                .make(repetitionRecycler, getApplicationContext().getResources().getString(R.string.repition_deleted), Snackbar.LENGTH_LONG)
                                .setActionTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white))
                                .setAction(getApplicationContext().getResources().getString(R.string.str_undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adapter.addItem(setsData);
                                        insertIntoDb(setsData);

                                    }
                                });
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                        TextView tvSnackAction = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
                        tvSnack.setTextColor(Color.WHITE);
                        tvSnack.setTypeface(Typefaces.getRobotoMedium(context));
                        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(context));
                        snackbar.show();
                    } else {
                        final Snackbar snackbar = Snackbar
                                .make(repetitionRecycler, getApplicationContext().getResources().getString(R.string.repition_not_deleted), Snackbar.LENGTH_LONG)
                                .setActionTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));

                        adapter.addItem(setsData);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                        TextView tvSnackAction = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
                        tvSnack.setTextColor(Color.WHITE);
                        tvSnack.setTypeface(Typefaces.getRobotoMedium(context));
                        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(context));
                        snackbar.show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.str_rep_not_zero),
                            Toast.LENGTH_SHORT).show();
                edtNoOfRep.setText("" + counter);
                break;
            case R.id.btnPlus:
                if (counter < 15) {
                    counter = counter + 1;
                    NoOfSetsData newSetData = new NoOfSetsData(dailyWorkId, Calendar.getInstance().getTime().getTime());
                    long _newSetId = insertIntoDb(newSetData);
                    if (_newSetId > 0) {
                        newSetData.setRepId(_newSetId);
                        adapter.addItem(newSetData);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.str_rep_not_added), Toast.LENGTH_SHORT).show();
                        edtNoOfRep.setText("" + counter);
                    }
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.str_reach_limit), Toast.LENGTH_SHORT).show();
                edtNoOfRep.setText("" + counter);
                break;
            case R.id.doneSets:
                //saveData();
                sharedPrefManager.setSetId(0);
                sharedPrefManager.setDailyWorkId(0);
                finish();
                break;

        }

    }

    private long insertIntoDb(NoOfSetsData setsData) {
        long repId = 0;
        ContentValues clientValues = new ContentValues();
        if (setsData.getRepId() != 0)
            clientValues.put(FitnessContract.SetsRepetition.REP_ID, setsData.getRepId());
        clientValues.put(FitnessContract.SetsRepetition.REP_DW_ID, setsData.getWorkId());
        clientValues.put(FitnessContract.SetsRepetition.REP_NO_REP, setsData.getNoOfRep());
        clientValues.put(FitnessContract.SetsRepetition.REP_DATE_TIME, setsData.getDateTime());
        try {
            Uri insertRep = getContentResolver().insert(FitnessContract.SetsRepetition.CONTENT_URI, clientValues);
            repId = ContentUris.parseId(insertRep);
        } catch (SQLException e) {
            Log.e(TAG, "Repetition is not added " + e.toString());
        }
        return repId;
    }

    @Override
    public void onButtonClick(NoOfSetsData noOfSetsData, int position, View view) {
        int id = view.getId();
        int noOfRep = noOfSetsData.getNoOfRep();
        switch (id) {
            case R.id.btnPlus: {
                noOfSetsData.setNoOfRep(noOfSetsData.getNoOfRep() + 1);
                long _repId = updateRepData(noOfSetsData);
                if (_repId > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    if (noOfRep != 0) {
                        noOfSetsData.setNoOfRep(noOfSetsData.getNoOfRep() - 1);
                        adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.str_data_not_updated),
                            Toast.LENGTH_SHORT).show();
                }
            }

            break;
            case R.id.btnMinus: {
                if (noOfRep > 0) {
                    noOfSetsData.setNoOfRep(noOfSetsData.getNoOfRep() - 1);
                    long _repId = updateRepData(noOfSetsData);
                    if (_repId > 0) {
                        adapter.notifyDataSetChanged();
                    } else {
                        noOfSetsData.setNoOfRep(noOfSetsData.getNoOfRep() + 1);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), getString(R.string.str_data_not_updated),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.str_rep_not_zero),
                            Toast.LENGTH_SHORT).show();
                }
            }

            break;
        }

    }

    public long updateRepData(NoOfSetsData setsData) {
        long repId = 0;
        ContentValues clientValues = new ContentValues();
        clientValues.put(FitnessContract.SetsRepetition.REP_DW_ID, setsData.getWorkId());
        clientValues.put(FitnessContract.SetsRepetition.REP_NO_REP, setsData.getNoOfRep());
        clientValues.put(FitnessContract.SetsRepetition.REP_DATE_TIME, setsData.getDateTime());
        try {
            repId = getContentResolver().update(FitnessContract.SetsRepetition.CONTENT_URI, clientValues
                    , FitnessContract.SetsRepetition.REP_ID + "=?", new String[]{String.valueOf(setsData.getRepId())});
        } catch (SQLException e) {
            Log.e(TAG, "Repetition is not added " + e.toString());
        }
        return repId;
    }
}
