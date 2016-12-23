package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.groceryfood.helpers.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setTitle(getResources().getString(R.string.lang_setting));
        mSpinner = (Spinner) findViewById(R.id.spinner);
        List<String> spineerData = new ArrayList<>();
        spineerData.add(getResources().getString(R.string.str_english));
        spineerData.add(getResources().getString(R.string.str_arabic));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_spinner_item, spineerData);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    LocaleHelper.setLocale(getApplicationContext(), "en");
                    //rcreeate();
                } else if (position == 1) {
                    LocaleHelper.setLocale(getApplicationContext(), "ar");
                    //recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
