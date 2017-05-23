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

public class FinancialAdviceActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_advice);
        mSpinner = (Spinner) findViewById(R.id.spinner2);
        mNext = (Button) findViewById(R.id.next);
        List<String> list = new ArrayList<String>();
        list.add("--Please select occupation--");
        list.add("Salaried");
        list.add("Freelance");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinancialAssetActivity.class);
                startActivity(intent);
            }
        });
    }
}
