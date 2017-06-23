package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finaqa.R;
import com.finaqa.fragments.FragmentPayment;

public class ShortTermRiskActivity extends AppCompatActivity {

    private Button mPay;
    private String SEARCH_FRAGMENT = "Payment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_term_risk);
        mPay = (Button) findViewById(R.id.pay);
        /*mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShortTermRiskActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
