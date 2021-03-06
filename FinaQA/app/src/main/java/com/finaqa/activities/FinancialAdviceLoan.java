package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.finaqa.R;

import java.util.ArrayList;
import java.util.List;

public class FinancialAdviceLoan extends AppCompatActivity {

    private Spinner mSpinner, mTenure;
    private Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_advice_loan);
        mSpinner = (Spinner) findViewById(R.id.spinner3);
        mTenure = (Spinner) findViewById(R.id.spinner4);
        mNext = (Button) findViewById(R.id.nextToTotal);
        List<String> list = new ArrayList<String>();
        list.add("Home loan");
        list.add("Car loan");
        list.add("Personal loan");
        list.add("Credit card loan");
        list.add("Educational loan");
        List<String> yearList = new ArrayList<String>();
        for (int i = 1; i <= 20; i++) {
            yearList.add("" + i);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTenure.setAdapter(yearAdapter);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TotalLoanActivity.class);
                startActivity(intent);
            }
        });
    }
}
