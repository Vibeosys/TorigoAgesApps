package com.vibeosys.lawyerdiary.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vibeosys.lawyerdiary.Adapter.ClientAutoCompleteAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;
import com.vibeosys.lawyerdiary.database.LawyerContract;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewCaseActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = NewCaseActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private EditText txtCaseName, txtOppositionName, txtDate, txtTime, txtCourtLocation,
            txtDescription, txtStatus, txtKeyPoints;
    private Button btnCancel, btnSave;
    private AutoCompleteTextView txtClientName;

    private ArrayList<Client> clientList = new ArrayList<>();
    private long _clientId = -1;
    private ClientAutoCompleteAdapter clientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        setTitle(getResources().getString(R.string.add_new_case_title));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestitial_banner_home_footer));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
        // mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // super.onAdLoaded();
                showIntestititalCase();
            }
        });

        txtCaseName = (EditText) findViewById(R.id.txtCaseName);
        txtClientName = (AutoCompleteTextView) findViewById(R.id.txtClientName);
        txtOppositionName = (EditText) findViewById(R.id.txtOppositionName);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtCourtLocation = (EditText) findViewById(R.id.txtCourtLocation);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtStatus = (EditText) findViewById(R.id.txtStatus);
        txtKeyPoints = (EditText) findViewById(R.id.txtKeyPoints);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtDate.setEnabled(false);
        txtTime.setEnabled(false);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtClientName.setOnItemClickListener(this);
        loadClientData();
    }

    private void loadClientData() {

        Cursor clientsCursor = getContentResolver().query(LawyerContract.Client.CONTENT_URI,
                new String[]{LawyerContract.Client._ID, LawyerContract.Client.NAME
                }, null, null, null);

        if (clientsCursor.getCount() > 0) {
            clientsCursor.moveToFirst();
            do {
                long clientId = clientsCursor.getLong(clientsCursor.getColumnIndex(LawyerContract.Client._ID));
                String clientName = clientsCursor.getString(clientsCursor.getColumnIndex(LawyerContract.Client.NAME));
                clientList.add(new Client(clientId, clientName));
            }
            while (clientsCursor.moveToNext());
        }
        clientAdapter = new ClientAutoCompleteAdapter(this, R.layout.row_autocomplete_client, clientList);
        txtClientName.setAdapter(clientAdapter);
    }

    public void showIntestititalCase() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
                long _caseId = saveCaseData();
                if (_caseId > 0) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.new_case_added),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else if (_caseId == 0) {
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.new_case_not_added),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.txtDate:
                break;
            case R.id.txtTime:
                break;
        }

    }

    private long saveCaseData() {
        long _caseId = 0;
        boolean cancelFlag = false;
        View focusView = null;
        txtCaseName.setError(null);
        txtClientName.setError(null);
        txtOppositionName.setError(null);
        txtCourtLocation.setError(null);

        String caseName = txtCaseName.getText().toString();
        String clientName = txtClientName.getText().toString();
        String oppositionName = txtOppositionName.getText().toString();
        String strDate = txtDate.getText().toString();
        String strTime = txtTime.getText().toString();
        String courtLocation = txtCourtLocation.getText().toString();
        String description = txtDescription.getText().toString();
        String status = txtStatus.getText().toString();
        String keyPoints = txtKeyPoints.getText().toString();


        if (TextUtils.isEmpty(caseName)) {
            cancelFlag = true;
            focusView = txtCaseName;
            txtCaseName.setError(getResources().getString(R.string.str_err_case_name_required));
        } else if (TextUtils.isEmpty(clientName)) {
            cancelFlag = true;
            focusView = txtClientName;
            txtClientName.setError(getResources().getString(R.string.str_error_client_name_reqired));
        } else if (TextUtils.isEmpty(oppositionName)) {
            cancelFlag = true;
            focusView = txtOppositionName;
            txtOppositionName.setError(getResources().getString(R.string.str_error_opposition_name_reqired));
        } else if (TextUtils.isEmpty(courtLocation)) {
            cancelFlag = true;
            focusView = txtCourtLocation;
            txtCourtLocation.setError(getResources().getString(R.string.str_error_court_location_reqired));
        } else if (_clientId == -1) {
            Client client = clientAdapter.contains(clientName);
            if (client != null) {
                _clientId = client.get_Id();
            } else {
                cancelFlag = true;
                focusView = txtClientName;
                txtClientName.setError(getResources().getString(R.string.str_error_add_client_in_list));
            }
        }

        if (cancelFlag) {
            focusView.requestFocus();
            _caseId = 0;
        } else {
            if (TextUtils.isEmpty(strDate)) {
                strDate = null;
            }
            if (TextUtils.isEmpty(description)) {
                description = null;
            }
            if (TextUtils.isEmpty(status)) {
                status = null;
            }
            if (TextUtils.isEmpty(keyPoints)) {
                keyPoints = null;
            }

            ContentValues clientValues = new ContentValues();
            clientValues.put(LawyerContract.Case.CASE_NAME, caseName);
            clientValues.put(LawyerContract.Case.CLIENT_ID, _clientId);
            clientValues.put(LawyerContract.Case.AGAINST, oppositionName);
            clientValues.put(LawyerContract.Case.CASE_DATE, strDate);
            clientValues.put(LawyerContract.Case.COURT_LOCATION, courtLocation);
            clientValues.put(LawyerContract.Case.DESCRIPTION, description);
            clientValues.put(LawyerContract.Case.STATUS, status);
            clientValues.put(LawyerContract.Case.KEY_POINTS, keyPoints);
            try {
                Uri insertCase = getContentResolver().insert(LawyerContract.Case.CONTENT_URI, clientValues);
                _caseId = ContentUris.parseId(insertCase);
            } catch (SQLException e) {
                Log.e(TAG, "Case is not added " + e.toString());
            }

        }
        return _caseId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Client client = (Client) txtClientName.getAdapter().getItem(position);
        _clientId = client.get_Id();
    }
}
