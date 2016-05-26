package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.HistoryAdapter;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

public class SettleActivity extends AppCompatActivity {

    private ListView mListView;
    private HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        setTitle(getResources().getString(R.string.settle_activity));
        mListView = (ListView) findViewById(R.id.settleList);
        ArrayList<HistoryDTO> historyDTOs = new ArrayList<>();
        historyDTOs.add(new HistoryDTO("Dog Food", "You Borrow", "25 May", "$30"));
        historyDTOs.add(new HistoryDTO("Rent", "You Lent", "23 May", "$250"));
        historyDTOs.add(new HistoryDTO("Dinner", "You Lent", "21 May", "$22"));
        mHistoryAdapter = new HistoryAdapter(getApplicationContext(), historyDTOs);
        mListView.setAdapter(mHistoryAdapter);
    }
}
