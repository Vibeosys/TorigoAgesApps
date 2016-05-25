package com.vibeosys.paymybill.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vibeosys.paymybill.R;

public class AddBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle(getResources().getString(R.string.add_bill_activity));
    }
}
