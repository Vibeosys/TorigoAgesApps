package com.vibeosys.lawyerdiary.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.lawyerdiary.Adapter.ClientAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.fragments.ClientDetailFragment;
import com.vibeosys.lawyerdiary.fragments.ClientListFragment;

import java.util.ArrayList;

/**
 * Created by Vibeosys software on 26-04-2016.
 *
 */

/**
 * ClientActivity is use to display the client list from local data base.
 * This class is use to inflate ClientDetails Fragment which is used to add client details.
 * click on fab button is use to new client using AddClientActivity.
 */
public class ClientActivity extends BaseActivity implements ClientListFragment.CallBack {

    private FloatingActionButton fab;
    private static final String TAG = ClientActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DCFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_layout_client);
        getSupportActionBar().setTitle(getResources().getString(R.string.str_clients));
        fab = (FloatingActionButton) findViewById(R.id.fab_client);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_Client = new Intent(getApplicationContext(), AddClientActivity.class);
                startActivity(add_Client);
            }
        });

        if (findViewById(R.id.client_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.client_detail_container, new ClientDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        ClientListFragment clientListFragment = ((ClientListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_client));
    }


    @Override
    public void onItemSelected(long clientId) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putLong(ClientDetailFragment.DETAIL_URI, clientId);

            ClientDetailFragment fragment = new ClientDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.client_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, ClientDetailsActivity.class);
            intent.putExtra(ClientDetailFragment.DETAIL_URI, clientId);
            startActivity(intent);
        }
    }
}
