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

public class NewCustomSetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = NewCustomSetActivity.class.getSimpleName();
    private EditText edtName, edtDesc;
    public static final String WKM_ID = "WorkoutId";
    private Button btnAddWorkout;
    private long workoutId = 0;
    private Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_set);
        edtName = (EditText) findViewById(R.id.edt_set_name);
        edtDesc = (EditText) findViewById(R.id.edt_set_desc);
        btnAddWorkout = (Button) findViewById(R.id.btn_add_set);
        btnAddWorkout.setOnClickListener(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            //isNewProduct = bundle.getBoolean(IS_NEW_PRODUCT);
            workoutId = bundle.getLong(WKM_ID);
            //showData();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_set:
                String setName = edtName.getText().toString();
                String desc = edtDesc.getText().toString();

                if (setName.isEmpty()) {
                    edtName.setError(getResources().getString(R.string.str_err_setname));
                    edtName.requestFocus();
                } else if (desc.isEmpty()) {
                    edtDesc.setError(getResources().getString(R.string.str_err_setdesc));
                    edtDesc.requestFocus();
                } else {
                    ContentValues clientValues = new ContentValues();
                    clientValues.put(FitnessContract.SetsMaster.SET_NAME, setName);
                    clientValues.put(FitnessContract.SetsMaster.SET_DESC, desc);
                    clientValues.put(FitnessContract.SetsMaster.SET_WKM_ID, workoutId);
                    try {
                        Uri insertSet = getContentResolver().insert(FitnessContract.SetsMaster.CONTENT_URI, clientValues);
                        long _setId = ContentUris.parseId(insertSet);
                        if (_setId > 0) {
                            Toast.makeText(getApplicationContext(), getString(R.string.
                                    str_success_add_workout), Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, "Set is not added " + e.toString());
                    }
                }
                break;
        }
    }
}
