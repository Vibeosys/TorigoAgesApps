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

public class CaseDetailsActivity extends AppCompatActivity {

    private static final String TAG = CaseDetailsActivity.class.getSimpleName();
    private long mCaseId;
    private TextView txtCaseName, txtClientName, txtOppositionName,
            txtCourtLocation, txtStatus, txtDate, txtKeyPoints, txtFiles, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);

        mCaseId = getIntent().getExtras().getLong(LawyerContract.Case._ID);

        txtCaseName = (TextView) findViewById(R.id.txtCaseName);
        txtClientName = (TextView) findViewById(R.id.txtClientName);
        txtOppositionName = (TextView) findViewById(R.id.txtOppositionName);
        txtCourtLocation = (TextView) findViewById(R.id.txtCourtLocation);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtKeyPoints = (TextView) findViewById(R.id.txtKeyPoints);
        txtFiles = (TextView) findViewById(R.id.txtFiles);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        loadAndDisplayData();
    }

    private void loadAndDisplayData() {
        String[] projection = new String[]{LawyerContract.Case.TABLE_NAME + "." + LawyerContract.Case._ID,
                LawyerContract.Case.CASE_NAME, LawyerContract.Client.NAME, LawyerContract.Case.AGAINST,
                LawyerContract.Case.COURT_LOCATION, LawyerContract.Case.STATUS,
                LawyerContract.Case.CASE_DATE, LawyerContract.Case.KEY_POINTS, LawyerContract.Case.DESCRIPTION};
        Uri uri = LawyerContract.Case.buildCaseUri(mCaseId);
        Cursor clientCursor = null;
        try {
            clientCursor = getContentResolver().query(uri,
                    projection, null, null, null);
        } catch (SQLiteException e) {
            Log.e(TAG, "error in getting case details" + e.toString());
        }

        if (clientCursor.moveToFirst()) {
            String caseName = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.CASE_NAME));
            String clientName = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.NAME));
            String oppositionName = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.AGAINST));
            String courtLocation = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.COURT_LOCATION));
            int status = clientCursor.getInt(clientCursor.getColumnIndex(LawyerContract.Case.STATUS));
            String date = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
            String keyPoints = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.KEY_POINTS));
            // String files = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
            String desc = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.DESCRIPTION));

            setTitle(caseName + " Details");
            txtCaseName.setText(caseName);
            txtClientName.setText(clientName);
            txtOppositionName.setText(oppositionName);
            txtCourtLocation.setText(courtLocation);
            txtStatus.setText(String.valueOf(status));
            txtDate.setText(date);
            txtKeyPoints.setText(keyPoints);
            txtDescription.setText(desc);
        }

    }
}
