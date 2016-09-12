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
        /*clientData = new ArrayList<ClientData>();
        clientData.add(new ClientData("smith barney","PIL","Criminal",R.drawable.ic_crime_client,"21-Apr-14"));
        clientData.add(new ClientData("johnson flep","Commercial","Civil",R.drawable.ic_civil_client,"2-Feb-10"));
        clientData.add(new ClientData("smith sebastian","Criminal","Criminal",R.drawable.ic_crime_client,"12-Dec-10"));
        clientData.add(new ClientData("kait williams","PIL","Civil",R.drawable.ic_civil_client,"23-Jan-13"));
        clientData.add(new ClientData("robert pattinson","Commercial","Civil",R.drawable.ic_civil_client,"2-Feb-15"));
        clientData.add(new ClientData("aston martin","Commercial","Civil",R.drawable.ic_civil_client,"5-Oct-10"));
        ClientDetailsAdapter adapter = new ClientDetailsAdapter(getApplicationContext(),clientData);
        client.setAdapter(adapter);*/

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
        Cursor caseCursor = getContentResolver().query(LawyerContract.Client.CONTENT_URI,
                new String[]{LawyerContract.Client._ID, LawyerContract.Client.NAME, LawyerContract.Client.PH_NUMBER
                }, null, null, null);

        if (caseCursor.getCount() > 0) {
            caseCursor.moveToFirst();
            do {
                long clientId = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Client._ID));
                String name = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Client.NAME));
                String phNumber = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Client.PH_NUMBER));
                clients.add(new Client(clientId, name, phNumber));
            }
            while (caseCursor.moveToNext());
        }
        clientAdapter = new ClientAdapter(clients, getApplicationContext());
        clientList.setAdapter(clientAdapter);
    }

}
