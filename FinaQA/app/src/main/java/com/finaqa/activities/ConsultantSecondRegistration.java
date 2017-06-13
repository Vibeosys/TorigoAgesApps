package com.finaqa.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.finaqa.R;
import com.finaqa.adapter.SpinnerSimpleAdapter;
import com.finaqa.database.DbRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ConsultantSecondRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button mNextBtn;
    private Spinner mCitySpinner, mCountrySpinner;
    ArrayList<String> cityList, countryList;
    EditText mCityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_second_registration);
        setTitle("Consultant registration");
        mNextBtn = (Button) findViewById(R.id.consult_second);
        mCitySpinner = (Spinner) findViewById(R.id.citySpinner);
        mCountrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        mCityEditText = (EditText) findViewById(R.id.cityEditText);
        cityList = new ArrayList<>();
        countryList = new ArrayList<>();
        countryList.add("India");
        mNextBtn.setOnClickListener(this);
        DbRepository dbRepository = new DbRepository(getApplicationContext());
        cityList = dbRepository.getCityList();
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }
        System.out.println("# countries found: " + countries.size());
        SpinnerSimpleAdapter countryAdt = new SpinnerSimpleAdapter(ConsultantSecondRegistration.this, countries);
        mCountrySpinner.setAdapter(countryAdt);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCountrySpinner.getSelectedItem().toString().equals("India")) {
                    mCitySpinner.setVisibility(View.VISIBLE);
                    mCityEditText.setVisibility(View.GONE);
                    SpinnerSimpleAdapter occup = new SpinnerSimpleAdapter(ConsultantSecondRegistration.this, cityList);
                    mCitySpinner.setAdapter(occup);

                } else if (!mCountrySpinner.getSelectedItem().toString().equals("India")) {
                    mCitySpinner.setVisibility(View.GONE);
                    mCityEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.consult_second:
                Intent intent = new Intent(getApplicationContext(), ConsultantThirdRegistration.class);
                startActivity(intent);
                break;
        }

    }
}
