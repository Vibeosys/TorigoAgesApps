package com.vibeosys.lawyerdiary.activities;

import android.content.ContentUris;
import android.content.ContentValues;
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

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.Validator;

/**
 * Created by shrinivas on 27-04-2016.
 */
public class AddClientActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddClientActivity.class.getSimpleName();
    private EditText mTxtName, mTxtPhNo, mTxtEmail, mTxtAddress;
    private Button mBtnCancel, mBtnSave;
    private Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
        setTitle(getResources().getString(R.string.str_new_client));

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

    private long addNewClient() {
        long clientId;
        String name = mTxtName.getText().toString();
        String phoneNumber = mTxtPhNo.getText().toString();
        String email = mTxtEmail.getText().toString();
        String address = mTxtAddress.getText().toString();

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
}
