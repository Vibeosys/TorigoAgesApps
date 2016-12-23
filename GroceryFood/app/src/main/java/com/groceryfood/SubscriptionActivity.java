package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        setTitle(getResources().getString(R.string.str_subscribe_btn));
    }
}
