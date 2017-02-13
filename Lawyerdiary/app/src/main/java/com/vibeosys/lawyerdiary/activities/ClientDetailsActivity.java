package com.vibeosys.lawyerdiary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.fragments.CaseDetailFragment;
import com.vibeosys.lawyerdiary.fragments.ClientDetailFragment;

/**
 * Created by Vibeosys software on 27-04-2016.
 */

/**
 * This class is use to display client details.
 */
public class ClientDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            long clientId = getIntent().getExtras().getLong(ClientDetailFragment.DETAIL_URI, 0);
            Bundle arguments = new Bundle();
            arguments.putLong(ClientDetailFragment.DETAIL_URI, clientId);

            ClientDetailFragment fragment = new ClientDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.client_detail_container, fragment)
                    .commit();
        }
    }
}
