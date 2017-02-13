package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.lawyerdiary.Adapter.CaseAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.CaseData;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.fragments.CaseDetailFragment;
import com.vibeosys.lawyerdiary.fragments.CaseListFragment;

import java.util.ArrayList;

/**
 * Created by Vibeosys software on 27-04-2016.
 */

/**
 * Display the case details in list view.
 */
public class CasesActivity extends BaseActivity implements CaseListFragment.CallBackItem {

    private static final String TAG = CasesActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private static CasesActivity inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        setTitle(getResources().getString(R.string.str_cases_title));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddCase = new Intent(getApplicationContext(), NewCaseActivity.class);
                startActivity(iAddCase);
            }
        });

        if (findViewById(R.id.case_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.case_detail_container, new CaseDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        CaseListFragment caseListFragment = ((CaseListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_cases));

    }

    @Override
    public void onItemSelected(long caseId) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putLong(CaseDetailFragment.DETAIL_URI, caseId);

            CaseDetailFragment fragment = new CaseDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.case_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, CaseDetailsActivity.class);
            intent.putExtra(CaseDetailFragment.DETAIL_URI, caseId);
            startActivity(intent);
        }
    }


    /* public void viewCase(View v) {
         Intent iCaseDetails = new Intent(getApplicationContext(), CaseDetailsActivity.class);
         startActivity(iCaseDetails);
     }*/

    /**
     * This function will returns instance of the casesActivity class.
     * @return instance of the caseActivity.
     */
    public static CasesActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
}
