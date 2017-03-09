package com.vibeosys.fitnessapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.database.FitnessContract;

public class NewCustomWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = NewCustomWorkoutActivity.class.getSimpleName();
    private EditText edtName, edtDesc;
    private Button btnAddWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_workout);
        edtName = (EditText) findViewById(R.id.edt_workout_name);
        edtDesc = (EditText) findViewById(R.id.edt_workout_desc);
        btnAddWorkout = (Button) findViewById(R.id.btn_add_workout);
        btnAddWorkout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_workout:
                String workoutName = edtName.getText().toString();
                String desc = edtDesc.getText().toString();

                if (workoutName.isEmpty()) {
                    edtName.setError(getResources().getString(R.string.str_err_workoutname));
                    edtName.requestFocus();
                } else if (desc.isEmpty()) {
                    edtDesc.setError(getResources().getString(R.string.str_err_workdesc));
                    edtDesc.requestFocus();
                } else {
                    ContentValues clientValues = new ContentValues();
                    clientValues.put(FitnessContract.WorkOutMaster.WKM_NAME, workoutName);
                    clientValues.put(FitnessContract.WorkOutMaster.WKM_DESC, desc);
                    try {
                        Uri insertCase = getContentResolver().insert(FitnessContract.WorkOutMaster.CONTENT_URI, clientValues);
                        long _workOutId = ContentUris.parseId(insertCase);
                        if (_workOutId > 0) {
                            Toast.makeText(getApplicationContext(), getString(R.string.
                                    str_success_add_workout), Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, "Workout is not added " + e.toString());
                    }
                }
                break;
        }
    }
}
