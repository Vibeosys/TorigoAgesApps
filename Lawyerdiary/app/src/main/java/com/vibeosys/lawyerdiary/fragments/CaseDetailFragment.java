package com.vibeosys.lawyerdiary.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.activities.CaseDetailsActivity;
import com.vibeosys.lawyerdiary.activities.NewCaseActivity;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.services.ReminderReceiver;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by akshay on 13-09-2016.
 */
public class CaseDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = CaseDetailFragment.class.getSimpleName();
    public static final String DETAIL_URI = "URI";
    private EditText txtCaseName, txtClientName, txtOppositionName,
            txtCourtLocation, txtStatus, txtDate, txtKeyPoints, txtFiles, txtDescription;
    private Uri mUri;
    private DateUtils dateUtils = new DateUtils();
    private long _caseId;
    private Button btnRemindMe, btnEdit;
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_case_details, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            _caseId = arguments.getLong(CaseDetailFragment.DETAIL_URI);
            mUri = LawyerContract.Case.buildCaseUri(_caseId);
            txtCaseName = (EditText) rootView.findViewById(R.id.txtCaseName);
            txtClientName = (EditText) rootView.findViewById(R.id.txtClientName);
            txtOppositionName = (EditText) rootView.findViewById(R.id.txtOppositionName);
            txtCourtLocation = (EditText) rootView.findViewById(R.id.txtCourtLocation);
            txtStatus = (EditText) rootView.findViewById(R.id.txtStatus);
            txtDate = (EditText) rootView.findViewById(R.id.txtDate);
            txtKeyPoints = (EditText) rootView.findViewById(R.id.txtKeyPoints);
            txtFiles = (EditText) rootView.findViewById(R.id.txtFiles);
            txtDescription = (EditText) rootView.findViewById(R.id.txtDescription);
            btnRemindMe = (Button) rootView.findViewById(R.id.btnRemindMe);
            btnEdit = (Button) rootView.findViewById(R.id.btnEdit);
            alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            loadAndDisplayData();
            disableAll();
            btnEdit.setOnClickListener(this);
            btnRemindMe.setOnClickListener(this);
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    private void disableAll() {
        txtCaseName.setEnabled(false);
        txtClientName.setEnabled(false);
        txtOppositionName.setEnabled(false);
        txtCourtLocation.setEnabled(false);
        txtStatus.setEnabled(false);
        txtDate.setEnabled(false);
        txtKeyPoints.setEnabled(false);
        txtFiles.setEnabled(false);
        txtDescription.setEnabled(false);
    }

    private void loadAndDisplayData() {
        String[] projection = new String[]{LawyerContract.Case.TABLE_NAME + "." + LawyerContract.Case._ID,
                LawyerContract.Case.CASE_NAME, LawyerContract.Client.NAME, LawyerContract.Case.AGAINST,
                LawyerContract.Case.COURT_LOCATION, LawyerContract.Case.STATUS,
                LawyerContract.Case.CASE_DATE, LawyerContract.Case.KEY_POINTS,
                LawyerContract.Case.DESCRIPTION, LawyerContract.Case.CASE_TIME};
        Cursor caseCursor = null;
        if (mUri != null) {
            try {
                caseCursor = getContext().getContentResolver().query(mUri,
                        projection, null, null, null);
            } catch (SQLiteException e) {
                Log.e(TAG, "error in getting case details" + e.toString());
            }
            if (caseCursor.moveToFirst()) {
                String caseName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.CASE_NAME));
                String clientName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Client.NAME));
                String oppositionName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.AGAINST));
                String courtLocation = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.COURT_LOCATION));
                int status = caseCursor.getInt(caseCursor.getColumnIndex(LawyerContract.Case.STATUS));
                long date = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
                long time = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case.CASE_TIME));
                String keyPoints = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.KEY_POINTS));
                // String files = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
                String desc = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.DESCRIPTION));
                String strStatus = status == 1 ? "Active" : "Inactive";
                getActivity().setTitle(caseName + " Details");
                txtCaseName.setText(caseName);
                txtClientName.setText(clientName);
                txtOppositionName.setText(oppositionName);
                txtCourtLocation.setText(courtLocation);
                txtStatus.setText(strStatus);
                txtDate.setText(dateUtils.getLocalDateInReadableFormat(new Date(date)) + " "
                        + dateUtils.getLocalTimeInReadableFormat(new Date(time)));
                txtKeyPoints.setText(keyPoints);
                txtDescription.setText(desc);
            }
        }

        loadFileData();
    }

    private void loadFileData() {
        String[] projection = new String[]{LawyerContract.Document._ID, LawyerContract.Document.DOCUMENT_NAME,
                LawyerContract.Document.FILE_PATH};
        String selection = LawyerContract.Document.CASE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(_caseId)};
        Cursor documentCursor = null;
        String allFiles = "";
        try {
            documentCursor = getContext().getContentResolver().query(LawyerContract.Document.CONTENT_URI,
                    projection, selection, selectionArgs, null);
        } catch (SQLiteException e) {
            Log.e(TAG, "error in getting document details" + e.toString());
        }
        if (documentCursor.getCount() > 0) {
            documentCursor.moveToFirst();

            do {
                String fileName = documentCursor.getString(documentCursor.
                        getColumnIndex(LawyerContract.Document.DOCUMENT_NAME));
                String _id = documentCursor.
                        getString(documentCursor.getColumnIndex(LawyerContract.Document._ID));
                String filePath = documentCursor.
                        getString(documentCursor.getColumnIndex(LawyerContract.Document.FILE_PATH));
                if (TextUtils.isEmpty(allFiles))
                    allFiles = fileName;
                else
                    allFiles = allFiles + "\n" + fileName;
            } while (documentCursor.moveToNext());
        }

        txtFiles.setText(allFiles);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRemindMe:
                String dateNTime = txtDate.getText().toString();
                Date caseDate = dateUtils.convertTimeToDate(dateNTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(caseDate);
                Intent myIntent = new Intent(getContext(), ReminderReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);

                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                break;
            case R.id.btnEdit:
                if (btnEdit.getText().toString().equals("Edit")) {
                    editCase();
                    btnEdit.setText("Save");
                } else if (btnEdit.getText().toString().equals("Save")) {
                    updateCaseDate();
                    btnEdit.setText("Edit");
                    disableAll();
                }
                break;
        }
    }

    private void updateCaseDate() {
        View focusView = null;
        boolean cancelFlag = false;
        String caseName = txtCaseName.getText().toString();
        String location = txtCourtLocation.getText().toString();
        String keyPoints = txtKeyPoints.getText().toString();
        String decs = txtDescription.getText().toString();
        if (TextUtils.isEmpty(caseName)) {
            cancelFlag = true;
            focusView = txtCaseName;
            txtCaseName.setError(getResources().getString(R.string.str_err_case_name_required));
        } else if (TextUtils.isEmpty(location)) {
            cancelFlag = true;
            focusView = txtCourtLocation;
            txtCourtLocation.setError(getResources().getString(R.string.str_error_court_location_reqired));
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            ContentValues clientValues = new ContentValues();
            clientValues.put(LawyerContract.Case.CASE_NAME, caseName);
            clientValues.put(LawyerContract.Case.DESCRIPTION, decs);
            clientValues.put(LawyerContract.Case.COURT_LOCATION, location);
            clientValues.put(LawyerContract.Case.KEY_POINTS, keyPoints);
            try {
                int insertCase = getContext().getContentResolver().update(LawyerContract.Case.CONTENT_URI, clientValues,
                        LawyerContract.Case._ID + "=?", new String[]{String.valueOf(_caseId)});
                Log.d(TAG, "Case is updated " + insertCase);
            } catch (SQLException e) {
                Log.e(TAG, "Case is not updated " + e.toString());
            }
        }
    }

    private void editCase() {
        txtCaseName.setEnabled(true);
        txtCourtLocation.setEnabled(true);
        txtKeyPoints.setEnabled(true);
        txtDescription.setEnabled(true);
    }
}
