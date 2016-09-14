package com.vibeosys.lawyerdiary.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vibeosys.lawyerdiary.Adapter.ClientAutoCompleteAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.AppDataConstant;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class NewCaseActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = NewCaseActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private EditText txtCaseName, txtOppositionName, txtDate, txtTime, txtCourtLocation,
            txtDescription, txtKeyPoints, txtFiles;

    Switch swhStatus;
    private Button btnCancel, btnSave;
    private AutoCompleteTextView txtClientName;

    private Calendar mCaseCalender = Calendar.getInstance();
    private ArrayList<Client> clientList = new ArrayList<>();
    private long _clientId = -1;
    private ClientAutoCompleteAdapter clientAdapter;
    private DateUtils dateUtils = new DateUtils();
    private ArrayList<String> filePaths = new ArrayList<String>();
    private int SELECT_FILES = 19;

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
        swhStatus = (Switch) findViewById(R.id.swhStatus);
        txtKeyPoints = (EditText) findViewById(R.id.txtKeyPoints);
        txtFiles = (EditText) findViewById(R.id.txtFiles);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        updateLabel();
        updateTime();
        txtCaseName.requestFocus();
        //txtTime.setEnabled(false);
        swhStatus.setChecked(true);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtClientName.setOnItemClickListener(this);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(NewCaseActivity.this, date, mCaseCalender
                            .get(Calendar.YEAR), mCaseCalender.get(Calendar.MONTH),
                            mCaseCalender.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new TimePickerDialog(NewCaseActivity.this, time, mCaseCalender.
                            get(Calendar.HOUR_OF_DAY), mCaseCalender.get(Calendar.MINUTE), false).show();
                }
            }
        });
        txtFiles.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    requestGrantPermission();
                else
                    showFileSelectionDialog();
            }
        });
        loadClientData();
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            mCaseCalender.set(Calendar.YEAR, year);
            mCaseCalender.set(Calendar.MONTH, monthOfYear);
            mCaseCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        txtDate.setText(dateUtils.getLocalDateInReadableFormat(mCaseCalender.getTime()));
        txtTime.requestFocus();
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCaseCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCaseCalender.set(Calendar.MINUTE, minute);
            updateTime();
        }
    };

    private void updateTime() {

        txtTime.setText(dateUtils.getLocalTimeInReadableFormat(mCaseCalender.getTime()));
        txtCourtLocation.requestFocus();
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
                    if (filePaths.size() != 0) {
                        AsyncSaveFiles saveFile = new AsyncSaveFiles();
                        saveFile.execute(_caseId);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.new_case_added),
                                Toast.LENGTH_SHORT).show();
                    }

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
        boolean status = swhStatus.isChecked();
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
            if (TextUtils.isEmpty(keyPoints)) {
                keyPoints = null;
            }
            int statusValue = status == true ? 1 : 0;
            ContentValues clientValues = new ContentValues();
            clientValues.put(LawyerContract.Case.CASE_NAME, caseName);
            clientValues.put(LawyerContract.Case.CLIENT_ID, _clientId);
            clientValues.put(LawyerContract.Case.AGAINST, oppositionName);
            clientValues.put(LawyerContract.Case.CASE_DATE, dateUtils.getLongDate(strDate));
            clientValues.put(LawyerContract.Case.CASE_TIME, dateUtils.getLongTime(strTime));
            clientValues.put(LawyerContract.Case.COURT_LOCATION, courtLocation);
            clientValues.put(LawyerContract.Case.DESCRIPTION, description);
            clientValues.put(LawyerContract.Case.STATUS, String.valueOf(statusValue));
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

    //Custom File picker
    private void showFileSelectionDialog() {
        FilePickerBuilder.getInstance().setMaxCount(10)
                .setSelectedFiles(filePaths)
                .setActivityTheme(R.style.AppTheme)
                .pickDocument(this);
    }

    private void requestGrantPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                SELECT_FILES);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SELECT_FILES && grantResults[1] == 0) {
            showFileSelectionDialog();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    String fileNames = "";
                    filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS);
                    //use them anywhere
                    for (String strPath : filePaths) {
                        String fileName = Uri.parse(strPath).getLastPathSegment();
                        if (TextUtils.isEmpty(fileNames))
                            fileNames = fileName;
                        else
                            fileNames = fileNames + "\n" + fileName;
                    }
                    txtFiles.setText(fileNames);
                }
        }
    }

    private class AsyncSaveFiles extends AsyncTask<Long, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Long... params) {
            long caseId = params[0];

            for (String strFilePath : filePaths) {
                long documentId = 0;
                boolean fileFlag = false;
                String sourcePath = strFilePath;
                File sourceFile = new File(sourcePath);
                String fileName = Uri.parse(strFilePath).getLastPathSegment();
                String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        AppDataConstant.DIR_PATH + caseId + "/" + fileName;
                File destination = new File(destinationPath);
                try {
                    FileUtils.copyFile(sourceFile, destination);
                    fileFlag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (fileFlag) {
                    ContentValues documentValues = new ContentValues();
                    documentValues.put(LawyerContract.Document.DOCUMENT_NAME, fileName);
                    documentValues.put(LawyerContract.Document.FILE_PATH, destinationPath);
                    documentValues.put(LawyerContract.Document.CASE_ID, caseId);
                    documentValues.put(LawyerContract.Document.LAST_UPDATED_DATE, mCaseCalender.getTime().getTime());

                    try {
                        Uri insertCase = getContentResolver().insert(LawyerContract.Document.CONTENT_URI, documentValues);
                        documentId = ContentUris.parseId(insertCase);
                    } catch (SQLException e) {
                        Log.e(TAG, "Document is not added " + e.toString());
                    }
                }
                if (documentId > 0) {
                    Log.d(TAG, "Document added successfully" + documentId);
                } else
                    Log.e(TAG, "Document not added" + documentId);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.new_case_added),
                    Toast.LENGTH_SHORT).show();

        }
    }
}
