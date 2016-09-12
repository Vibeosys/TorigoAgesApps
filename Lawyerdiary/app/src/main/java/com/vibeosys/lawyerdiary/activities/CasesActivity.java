package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.vibeosys.lawyerdiary.Adapter.CaseAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.CaseData;
import com.vibeosys.lawyerdiary.database.LawyerContract;

import java.util.ArrayList;

public class CasesActivity extends AppCompatActivity {

    private ListView listCases;
    private CaseAdapter caseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        setTitle(getResources().getString(R.string.str_cases_title));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listCases = (ListView) findViewById(R.id.listCases);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddCase = new Intent(getApplicationContext(), NewCaseActivity.class);
                startActivity(iAddCase);
            }
        });
        loadCaseData();
    }

    private void loadCaseData() {
        ArrayList<CaseData> caseList = new ArrayList<>();
        Cursor caseCursor = getContentResolver().query(LawyerContract.Case.CONTENT_URI,
                new String[]{LawyerContract.Case._ID, LawyerContract.Case.CASE_NAME, LawyerContract.Case.DESCRIPTION
                }, null, null, null);

        if (caseCursor.getCount() > 0) {
            caseCursor.moveToFirst();
            do {
                long caseId = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case._ID));
                String caseName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.CASE_NAME));
                String caseDesc = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.DESCRIPTION));
                caseList.add(new CaseData(caseId, caseName, caseDesc));
            }
            while (caseCursor.moveToNext());
        }
        caseAdapter = new CaseAdapter(caseList, getApplicationContext());
        listCases.setAdapter(caseAdapter);
    }

    /*public void viewCase(View v) {
        Intent iCaseDetails = new Intent(getApplicationContext(), CaseDetailsActivity.class);
        startActivity(iCaseDetails);
    }*/

}
