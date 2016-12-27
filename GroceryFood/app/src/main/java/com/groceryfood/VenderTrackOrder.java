package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class VenderTrackOrder extends AppCompatActivity {
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_track_order);
        setTitle(getResources().getString(R.string.str_order_status));
        mSpinner = (Spinner) findViewById(R.id.spinner_status);
        List<String> spineerData = new ArrayList<>();
        spineerData.add(getResources().getString(R.string.str_cooking));
        spineerData.add(getResources().getString(R.string.str_on_the_way));
        spineerData.add(getResources().getString(R.string.str_dilivered));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_spinner_item, spineerData);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(dataAdapter);
    }
}
