package com.vibeosys.lawyerdiary.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.Validator;

import java.util.Calendar;

/**
 * Created by Vibeosys software on 27-04-2016.
 */

/**
 * AddClientActivity is use to add the client details into local data base.
 * This activity also have InterstitialAd from ad mob
 */
public class AddClientActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = AddClientActivity.class.getSimpleName();
    private EditText mTxtName, mTxtPhNo, mTxtEmail, mTxtAddress;
    private Button mBtnCancel, mBtnSave;
    /**
     * Validator class is used for validation purpose.This class validate email id and phone number.
     */
    private Validator validator = new Validator();
    InterstitialAd mInterstitialAd;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
        setTitle(getResources().getString(R.string.str_new_client));
        if (mSessionManager.getInAppPurchase() != 1) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.intestitial_banner_home_footer));
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // super.onAdLoaded();
                    showIntestititalAd();
                }
            });
        }
       /* mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestitial_banner_home_footer));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // super.onAdLoaded();
                showIntestititalAd();
            }
        });*/
        mTxtName = (EditText) findViewById(R.id.txtName);
        mTxtPhNo = (EditText) findViewById(R.id.txtPhNo);
        mTxtEmail = (EditText) findViewById(R.id.txtEmail);
        mTxtAddress = (EditText) findViewById(R.id.txtAddress);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnSave = (Button) findViewById(R.id.btnSave);

        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
                long _id = addNewClient();
                if (_id > 0) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.new_client_added),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else if (_id == 0) {
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.new_client_not_added),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * This function is used for add new client in to local data base using form field and also perform validation.
     *
     * @return latest client id
     */
    private long addNewClient() {
        long clientId;
        String name = mTxtName.getText().toString();
        String phoneNumber = mTxtPhNo.getText().toString();
        String email = mTxtEmail.getText().toString();
        String address = mTxtAddress.getText().toString();
        Uri uri = LawyerContract.Client.buildClientPhMatchUri(phoneNumber);
        Cursor clientCursor = getContentResolver().query(uri,
                new String[]{LawyerContract.Client._ID}, null,
                null, null);

        View focusView = null;
        boolean flag = false;
        mTxtName.setError(null);
        mTxtPhNo.setError(null);
        mTxtEmail.setError(null);

        if (TextUtils.isEmpty(name)) {
            flag = true;
            focusView = mTxtName;
            mTxtName.setError(getResources().getString(R.string.str_error_name_reqired));
        } else if (TextUtils.isEmpty(phoneNumber)) {
            flag = true;
            focusView = mTxtPhNo;
            mTxtPhNo.setError(getResources().getString(R.string.str_err_ph_no));
        } else if (!validator.isValidPhone(phoneNumber)) {
            flag = true;
            focusView = mTxtPhNo;
            mTxtPhNo.setError(getResources().getString(R.string.str_err_ph_no_not_valid));
        } else if (!validator.isValidMail(email)) {
            flag = true;
            focusView = mTxtEmail;
            mTxtEmail.setError(getResources().getString(R.string.str_err_email_not_valid));
        } else if (clientCursor.moveToFirst()) {
            int clientIdIndex = clientCursor.getColumnIndex(LawyerContract.Client._ID);
            clientId = clientCursor.getLong(clientIdIndex);
            flag = true;
            mTxtPhNo.setError(getResources().getString(R.string.str_ph_no_is_present));
            focusView = mTxtPhNo;
        }
        if (flag) {
            focusView.requestFocus();
            clientId = 0;
        } else {
            ContentValues values = new ContentValues();
            values.put(LawyerContract.Client.NAME, name);
            values.put(LawyerContract.Client.PH_NUMBER, phoneNumber);
            values.put(LawyerContract.Client.EMAIL, email);
            values.put(LawyerContract.Client.ADDRESS, address);
            values.put(LawyerContract.Client.DATE_TIME, calendar.getTime().getTime());
            try {
                Uri insertClient = getContentResolver().insert(LawyerContract.Client.CONTENT_URI, values);
                clientId = ContentUris.parseId(insertClient);
            } catch (SQLException e) {
                clientId = -1;
                Log.e(TAG, "Error at inserting client record");
            }

        }
        return clientId;
    }

    /**
     * This function loads the interstitial advertise from ad mob integration.
     */
    public void showIntestititalAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
