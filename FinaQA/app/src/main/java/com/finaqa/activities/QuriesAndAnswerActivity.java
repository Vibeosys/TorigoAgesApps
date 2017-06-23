package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.finaqa.R;

import java.util.ArrayList;
import java.util.List;

public class QuriesAndAnswerActivity extends AppCompatActivity {

    private Spinner mCategorySpinner, mSubCategorySpinner;
    private List<String> subCategory;
    private Button mPayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quries_and_answer);
        mCategorySpinner = (Spinner) findViewById(R.id.category);
        mPayBtn = (Button) findViewById(R.id.pay);
        List<String> categories = new ArrayList<String>();
        subCategory = new ArrayList<>();
        mSubCategorySpinner = (Spinner) findViewById(R.id.subCategory);
        categories.add("Finance");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Personal");
        categories.add("Travel");

        subCategory.add("Stock market");
        subCategory.add("Investment planning");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mCategorySpinner.setAdapter(dataAdapter);

        ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCategory);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubCategorySpinner.setAdapter(subCategoryAdapter);

        mSubCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSubCategorySpinner.getSelectedItem().toString().equals("Investment planning")) {
                    mPayBtn.setText("Next");
                } else {
                    mPayBtn.setText("Pay");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RiskProfileActivity.class);
                startActivity(intent);

            }
        });
    }
}
