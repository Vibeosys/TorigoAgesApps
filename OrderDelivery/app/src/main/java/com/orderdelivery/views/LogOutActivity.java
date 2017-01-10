package com.orderdelivery.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.orderdelivery.R;
import com.orderdelivery.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

public class LogOutActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private Button btnSave;
    private String lang = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        setTitle(getResources().getString(R.string.action_settings));
        mSpinner = (Spinner) findViewById(R.id.spinner);
        btnSave = (Button) findViewById(R.id.btn_save);
        List<String> spineerData = new ArrayList<>();
        spineerData.add(getResources().getString(R.string.str_english));
        spineerData.add(getResources().getString(R.string.str_arabic));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_list_item_1, spineerData);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lang = "en";

                } else if (position == 1) {
                    lang = "ar";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleHelper.setLocale(getApplicationContext(), lang);
                recreate();
            }
        });
    }
}
