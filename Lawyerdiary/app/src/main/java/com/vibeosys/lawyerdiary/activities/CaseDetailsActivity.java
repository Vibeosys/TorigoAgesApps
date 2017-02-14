package com.vibeosys.lawyerdiary.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.fragments.CaseDetailFragment;

/**
 *  Created by Vibeosys software on 27-04-2016.
 */

/**
 * CaseDetailsActivity is use to display clients case details.
 * This activity is container for the CaseDetails Fragment.
 * Fragment is inflated from frame layout of this activity.
 */
public class CaseDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment_container);

        // mCaseId = getIntent().getExtras().getLong(LawyerContract.Case._ID);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putLong(CaseDetailFragment.DETAIL_URI,
                    getIntent().getExtras().getLong(CaseDetailFragment.DETAIL_URI));

            CaseDetailFragment fragment = new CaseDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.case_detail_container, fragment)
                    .commit();
        }

    }



}
