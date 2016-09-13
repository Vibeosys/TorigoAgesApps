package com.vibeosys.lawyerdiary.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

/**
 * Created by shrinivas on 26-04-2016.
 */
public class ClientActivity extends AppCompatActivity {
    private ListView clientList;
    private FloatingActionButton fab;
    private Context context = this;
    private ClientAdapter clientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_client);

        setContentView(R.layout.fab_layout_client);
        getSupportActionBar().setTitle("Client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab_client);
        clientList = (ListView) findViewById(R.id.client_listView);


        loadClientData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_Client = new Intent(getApplicationContext(), AddClientActivity.class);
                startActivity(add_Client);
            }
        });
        clientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.client_dialog);
                dialog.setTitle("smith sebastian");
                dialog.show();

            }
        });
    }

    private void loadClientData() {
        ArrayList<Client> clients = new ArrayList<>();
        Cursor clientCursor = getContentResolver().query(LawyerContract.Client.CONTENT_URI,
                new String[]{LawyerContract.Client._ID, LawyerContract.Client.NAME, LawyerContract.Client.PH_NUMBER
                }, null, null, null);

        if (clientCursor.getCount() > 0) {
            clientCursor.moveToFirst();
            do {
                long clientId = clientCursor.getLong(clientCursor.getColumnIndex(LawyerContract.Client._ID));
                String name = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.NAME));
                String phNumber = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.PH_NUMBER));
                clients.add(new Client(clientId, name, phNumber));
            }
            while (clientCursor.moveToNext());
        }
        clientAdapter = new ClientAdapter(clients, getApplicationContext());
        clientList.setAdapter(clientAdapter);
    }

}
