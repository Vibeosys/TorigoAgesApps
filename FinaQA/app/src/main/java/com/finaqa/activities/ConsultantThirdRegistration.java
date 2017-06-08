package com.finaqa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.finaqa.R;
import com.finaqa.adapter.SpinnerSimpleAdapter;
import com.finaqa.adapter.SubCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsultantThirdRegistration extends AppCompatActivity {
    private Spinner mCategory, mSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_third_registration);
        setTitle("Consultant Login");
        mCategory = (Spinner) findViewById(R.id.categoryMain);
        mSubCategory = (Spinner) findViewById(R.id.categorySub);
        ArrayList<String> categories = new ArrayList<String>();

        categories.add("Finance");
        categories.add("Stock market");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Personal");
        categories.add("Travel");

        SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(getApplicationContext(), categories);
        mCategory.setAdapter(categoryAdapter);

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(getApplicationContext(), categories);
        mSubCategory.setAdapter(subCategoryAdapter);
    }
}
