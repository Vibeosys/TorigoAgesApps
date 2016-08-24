package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.SettleAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

public class SettleActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private SettleAdapter settleAdapter;
    private TextView txtFriendName, txtAmount, txtType;
    private Button btnSettle;
    private FriendTransactions friendTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        setTitle(getResources().getString(R.string.settle_activity));
        mListView = (ListView) findViewById(R.id.settleList);
        txtFriendName = (TextView) findViewById(R.id.txtFriendName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtType = (TextView) findViewById(R.id.txtType);
        btnSettle = (Button) findViewById(R.id.btnSettle);
        btnSettle.setOnClickListener(this);
        friendTransactions = (FriendTransactions) getIntent().getExtras().getSerializable("data");

        settleAdapter = new SettleAdapter(getApplicationContext(), friendTransactions.getFilterBills());
        mListView.setAdapter(settleAdapter);

        txtFriendName.setText(friendTransactions.getName());
        double amount = friendTransactions.getAmount();
        amount = amount < 0 ? -(amount) : amount;
        txtAmount.setText(String.format("%.2f", amount));
        if (friendTransactions.getType() == BorrowType.YOU_OWE) {
            txtType.setText("You Owe");
            txtType.setTextColor(getResources().getColor(R.color.flatRed));
            txtAmount.setTextColor(getResources().getColor(R.color.flatRed));
        } else if (friendTransactions.getType() == BorrowType.OWES_YOU) {
            txtType.setText("Owes You");
            txtType.setTextColor(getResources().getColor(R.color.flatGreen));
            txtAmount.setTextColor(getResources().getColor(R.color.flatGreen));
        }

        if (amount == 0) {
            btnSettle.setVisibility(View.INVISIBLE);
            txtType.setText("All Bills are");
            txtAmount.setText("Settled");
            txtType.setTextColor(getResources().getColor(R.color.secondaryText));
            txtAmount.setTextColor(getResources().getColor(R.color.secondaryText));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSettle:
                settleBills();
                break;
        }
    }

    private void settleBills() {
        mDbRepository.settleAllBills(friendTransactions);
    }
}
