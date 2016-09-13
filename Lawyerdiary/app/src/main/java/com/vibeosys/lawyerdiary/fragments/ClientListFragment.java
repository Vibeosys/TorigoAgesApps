package com.vibeosys.lawyerdiary.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.lawyerdiary.Adapter.ClientAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;
import com.vibeosys.lawyerdiary.database.LawyerContract;

import java.util.ArrayList;

/**
 * Created by akshay on 13-09-2016.
 */
public class ClientListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView clientList;
    private ClientAdapter clientAdapter;

    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    public ClientListFragment() {
    }

    public interface CallBack {
        public void onItemSelected(long clientId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_list_fragment, container, false);
        clientList = (ListView) rootView.findViewById(R.id.client_listView);
        clientList.setOnItemClickListener(this);
        loadClientData();
        return rootView;
    }

    private void loadClientData() {
        ArrayList<Client> clients = new ArrayList<>();
        Cursor clientCursor = getContext().getContentResolver().query(LawyerContract.Client.CONTENT_URI,
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
        clientAdapter = new ClientAdapter(clients, getContext());
        clientList.setAdapter(clientAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Client client = (Client) clientAdapter.getItem(position);
        long client_Id = client.get_Id();
        ((CallBack) getActivity())
                .onItemSelected(client_Id);
        mPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
