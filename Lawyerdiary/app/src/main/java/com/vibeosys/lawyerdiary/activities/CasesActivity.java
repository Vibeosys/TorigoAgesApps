package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vibeosys.lawyerdiary.R;

public class CasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        setTitle("Cases");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddCase = new Intent(getApplicationContext(), NewCaseActivity.class);
                startActivity(iAddCase);
            }
        });
    }

    public void viewCase(View v) {
        Intent iCaseDetails = new Intent(getApplicationContext(), CaseDetailsActivity.class);
        startActivity(iCaseDetails);
    }

}
