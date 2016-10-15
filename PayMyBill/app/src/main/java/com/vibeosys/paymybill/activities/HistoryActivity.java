package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.HistoryAdapter;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity {

    private ListView mListView;
    private HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle(getResources().getString(R.string.add_history_activity));
        mListView = (ListView) findViewById(R.id.listHistory);
        ArrayList<HistoryDTO> historyDTOs = new ArrayList<>();
        historyDTOs = mDbRepository.getExpensesList();
        mHistoryAdapter = new HistoryAdapter(getApplicationContext(), historyDTOs, mSessionManager.getUserCurrencySymbol());
        mListView.setAdapter(mHistoryAdapter);
    }
}
