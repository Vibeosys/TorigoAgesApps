package com.finaqa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.finaqa.R;
import com.finaqa.adapter.SubCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsultantThirdRegistration extends AppCompatActivity {
    private Spinner mCategory, mSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_third_registration);
        mCategory = (Spinner) findViewById(R.id.categoryMain);
        mSubCategory = (Spinner) findViewById(R.id.categorySub);
        List<String> categories = new ArrayList<String>();

        categories.add("Finance");
        categories.add("Stock market");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Personal");
        categories.add("Travel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mCategory.setAdapter(dataAdapter);

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(getApplicationContext(), categories);
        mSubCategory.setAdapter(subCategoryAdapter);
    }
}
