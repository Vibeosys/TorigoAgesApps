package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.TransactionListAdapter;
import com.vibeosys.paymybill.data.BillDetailsDTO;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.util.AppConstants;

public class TransactionDetailsActivity extends BaseActivity implements View.OnClickListener, TransactionListAdapter.AmountChangedListener {

    private static final String TAG = TransactionDetailsActivity.class.getSimpleName();
    private BillDetailsDTO billDetailsDTO;
    private int splitMode = 0;
    private ListView mListTransaction;
    private Button mBtnCancel, mBtnSave;
    private TransactionListAdapter adapter;
    private TextView mTxtTotal, mTxtRemains, mTxtError;
    private double mBillTotalAmount;
    private double mTotalFriendAmount = 0;
    private double mRemainingAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        billDetailsDTO = (BillDetailsDTO) getIntent().getExtras().getSerializable("BillDetails");
        splitMode = getIntent().getExtras().getInt("Split");

        mListTransaction = (ListView) findViewById(R.id.transactionList);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnSave = (Button) findViewById(R.id.btnSave);
        mTxtTotal = (TextView) findViewById(R.id.txtTotal);
        mTxtRemains = (TextView) findViewById(R.id.txtRemains);
        mTxtError = (TextView) findViewById(R.id.txtErrorAmount);
        mBillTotalAmount = billDetailsDTO.getAmount();
        calculateAndUpdateTotal();

        mBtnCancel.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);

        if (splitMode == AppConstants.EQUALLY_DIVIDED) {
            double sharedAmount = mBillTotalAmount / billDetailsDTO.getShareWith().size();
            for (FriendsDTO friend : billDetailsDTO.getShareWith()) {
                friend.setAmount(sharedAmount);
            }
            adapter = new TransactionListAdapter(getApplicationContext(), billDetailsDTO.getShareWith());
            adapter.setSpiltMode(AppConstants.EQUALLY_DIVIDED);
            calculateAndUpdateTotal();
        } else if (splitMode == AppConstants.UNEQUALLY_DIVIDED) {
            adapter = new TransactionListAdapter(getApplicationContext(), billDetailsDTO.getShareWith());
            adapter.setSpiltMode(AppConstants.UNEQUALLY_DIVIDED);
        }
        mListTransaction.setAdapter(adapter);
        adapter.setAmountChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnSave:
                //Save the bill in database with all transaction
                saveData();
                break;
        }
    }

    private void saveData() {
        boolean cancelFlag = false;
        View focusView = null;
        if (mRemainingAmount < 0) {
            cancelFlag = true;
            focusView = mTxtRemains;
            mTxtRemains.setError(getResources().getString(R.string.str_err_amount));
        } else if (mRemainingAmount > 0) {
            cancelFlag = true;
            focusView = mTxtRemains;
            mTxtRemains.setError(getResources().getString(R.string.str_err_bill_not_settel));
        } else if (mTotalFriendAmount != billDetailsDTO.getAmount()) {
            cancelFlag = true;
            focusView = mTxtTotal;
            mTxtTotal.setError(getResources().getString(R.string.str_err_bill_not_settel));
        }
        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            mDbRepository.insertBill(billDetailsDTO);
        }
    }

    @Override
    public void onAmountChanged(int id, String amount, FriendsDTO friendsDTO) {
        try {
            mTxtError.setVisibility(View.GONE);
            double friendAmount = Double.parseDouble(amount);
            friendsDTO.setAmount(friendAmount);
            calculateAndUpdateTotal();
        } catch (NumberFormatException e) {
            Log.e(TAG, "Number Exception in amount");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }

    private void calculateAndUpdateTotal() {
        //Logic to update the total Amount
        mTotalFriendAmount = 0;
        mRemainingAmount = 0;
        for (FriendsDTO friend : billDetailsDTO.getShareWith()) {
            mTotalFriendAmount = mTotalFriendAmount + friend.getAmount();
        }
        mRemainingAmount = mBillTotalAmount - mTotalFriendAmount;
        if (mRemainingAmount < 0) {
            mTxtRemains.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            mTxtRemains.setTextColor(getResources().getColor(R.color.secondaryText));
        }

        String strTotal = "Total:" + String.format("%.2f", mTotalFriendAmount) + " Of " + String.format("%.2f", mBillTotalAmount);
        mTxtTotal.setText(strTotal);
        String strRemains = "Remains:" + String.format("%.2f", mRemainingAmount);
        mTxtRemains.setText(strRemains);
    }
}
