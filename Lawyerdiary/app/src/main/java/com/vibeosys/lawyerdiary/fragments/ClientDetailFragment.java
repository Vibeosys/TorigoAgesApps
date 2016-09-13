package com.vibeosys.lawyerdiary.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;

/**
 * Created by akshay on 13-09-2016.
 */
public class ClientDetailFragment extends BaseFragment {

    private static final String TAG = ClientDetailFragment.class.getSimpleName();
    public static final String DETAIL_URI = "URI";
    private TextView txtClientName, txtClientPhNo, txtEmailId,
            txtAddress;
    private long clientId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.client_details_fragment, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            clientId = arguments.getLong(ClientDetailFragment.DETAIL_URI);
            txtClientName = (TextView) rootView.findViewById(R.id.txtClientName);
            txtClientPhNo = (TextView) rootView.findViewById(R.id.txtPhNo);
            txtEmailId = (TextView) rootView.findViewById(R.id.txtEmail);
            txtAddress = (TextView) rootView.findViewById(R.id.txtAddress);

            loadAndDisplayData();

            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    private void loadAndDisplayData() {
        String[] projection = new String[]{LawyerContract.Client._ID, LawyerContract.Client.NAME,
                LawyerContract.Client.PH_NUMBER, LawyerContract.Client.EMAIL,
                LawyerContract.Client.ADDRESS};
        Cursor clientCursor = null;
        if (clientId != 0) {
            try {
                clientCursor = getContext().getContentResolver().query(LawyerContract.Client.CONTENT_URI,
                        projection, LawyerContract.Client._ID + "=?", new String[]{String.valueOf(clientId)
                        }, null);
            } catch (SQLiteException e) {
                Log.e(TAG, "error in getting client details" + e.toString());
            }
            if (clientCursor.moveToFirst()) {
                String clientName = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.NAME));
                String clientPhNo = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.PH_NUMBER));
                String email = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.EMAIL));
                String address = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.ADDRESS));

                getActivity().setTitle(clientName + " Details");
                txtClientName.setText(clientName);
                txtClientPhNo.setText(clientPhNo);
                txtEmailId.setText(email);
                txtAddress.setText(address);

            }
        }
    }
}
