package com.orderdelivery.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orderdelivery.R;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        setTitle("Order details");
    }
}
