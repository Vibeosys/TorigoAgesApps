package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.SettleAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

public class SettleActivity extends AppCompatActivity {

    private ListView mListView;
    private SettleAdapter settleAdapter;
    private TextView txtFriendName, txtAmount, txtType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        setTitle(getResources().getString(R.string.settle_activity));
        mListView = (ListView) findViewById(R.id.settleList);
        txtFriendName = (TextView) findViewById(R.id.txtFriendName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtType = (TextView) findViewById(R.id.txtType);

        FriendTransactions friendTransactions = (FriendTransactions) getIntent().getExtras().getSerializable("data");
        /*ArrayList<HistoryDTO> historyDTOs = new ArrayList<>();
        historyDTOs.add(new HistoryDTO("Dog Food", "You Borrow", "25 May", "$30"));
        historyDTOs.add(new HistoryDTO("Rent", "You Lent", "23 May", "$250"));
        historyDTOs.add(new HistoryDTO("Dinner", "You Lent", "21 May", "$22"));*/

        settleAdapter = new SettleAdapter(getApplicationContext(), friendTransactions.getBills());
        mListView.setAdapter(settleAdapter);

        txtFriendName.setText(friendTransactions.getName());
        double amount = friendTransactions.getAmount();
        if (amount < 0) {
            txtAmount.setText("$ " + String.format("%.2f", -(amount)));
        } else {
            txtAmount.setText("$ " + String.format("%.2f", amount));
        }

        if (friendTransactions.getType() == BorrowType.YOU_OWE) {
            txtType.setText("You Owe");
            txtType.setTextColor(getResources().getColor(R.color.flatRed));
            txtAmount.setTextColor(getResources().getColor(R.color.flatRed));
        } else if (friendTransactions.getType() == BorrowType.OWES_YOU) {
            txtType.setText("Owes You");
            txtType.setTextColor(getResources().getColor(R.color.flatGreen));
            txtAmount.setTextColor(getResources().getColor(R.color.flatGreen));
        }
    }
}
