package com.orderdelivery.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orderdelivery.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Order details");
    }
}
