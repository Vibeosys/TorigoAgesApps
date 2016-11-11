package com.vibeosys.paymybill.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.paymybill.MainActivity;
import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.BillDetailsDTO;
import com.vibeosys.paymybill.presenter.ExpensesPresenter;
import com.vibeosys.paymybill.util.AppConstants;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.Calendar;

public class ExpensesActivity extends BaseActivity implements View.OnClickListener {

    //private ImageView mImgBill;
    private static final int CAMERA_REQUEST = 100;
    private EditText mTxtDate, mTxtAmt, mTxtBillDesc;
    Calendar myCalendar = Calendar.getInstance();
    private Button mBtnSave;
    private TextView txtCurrencySymbol;
    private ExpensesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenceses);
        setTitle(getResources().getString(R.string.expenses_activity));
        mTxtDate = (EditText) findViewById(R.id.txtDate);
        //mImgBill = (ImageView) findViewById(R.id.imgBill);
        mTxtAmt = (EditText) findViewById(R.id.txtAmount);
        mTxtBillDesc = (EditText) findViewById(R.id.txtBillDesc);
        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnSave.setOnClickListener(this);
        presenter = new ExpensesPresenter(this, mDbRepository);
        txtCurrencySymbol = (TextView) findViewById(R.id.txtCurrencySymbol);
        txtCurrencySymbol.setText(mSessionManager.getUserCurrencySymbol());
        /*mImgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });*/

        mTxtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(ExpensesActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //mImgBill.setImageBitmap(photo);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        DateUtils dateUtils = new DateUtils();
        mTxtDate.setText(dateUtils.getLocalDateInReadableFormat(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
                saveBill();
                break;
        }
    }

    private void saveBill() {
        String strAmount = mTxtAmt.getText().toString();
        String strDate = mTxtDate.getText().toString();
        String strDesc = mTxtBillDesc.getText().toString();
        double billAmount = 0;

        mTxtAmt.setError(null);
        mTxtDate.setError(null);
        boolean cancelFlag = false;
        View focusView = null;
        if (TextUtils.isEmpty(strAmount)) {
            cancelFlag = true;
            focusView = mTxtAmt;
            mTxtAmt.setError(getResources().getString(R.string.str_err_enter_amt));
        } else if (TextUtils.isEmpty(strDate)) {
            cancelFlag = true;
            focusView = mTxtDate;
            mTxtDate.setError(getResources().getString(R.string.str_err_enter_date));
        } else if (!TextUtils.isEmpty(strAmount)) {
            try {
                billAmount = Double.parseDouble(strAmount);
            } catch (NumberFormatException e) {
                cancelFlag = true;
                focusView = mTxtAmt;
                mTxtAmt.setError(getResources().getString(R.string.str_number_error));
            }
        }
        if (cancelFlag) {
            focusView.requestFocus();
        } else {

            long billNo = myCalendar.getTime().getTime();
            int typeId = AppConstants.BILL_TYPE_EXPANSES;
            int currencyId = 0;
            int paidBy = Integer.parseInt(mSessionManager.getUserFriendId());

            BillDetailsDTO newBill = new BillDetailsDTO(billNo, strDate, billAmount, strDesc, typeId, currencyId, paidBy);
            int result = presenter.insertMyExpances(newBill);
            if (result == 1) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_expences), Toast.LENGTH_SHORT).show();
                Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
                iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iMain);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_err_expanses_add), Toast.LENGTH_SHORT).show();
                //Write function to rollback the data;
                presenter.deleteBillAndTransactions(newBill);
            }
            //Start new List activity
        }
    }
}
