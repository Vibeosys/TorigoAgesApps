package com.vibeosys.lawyerdiary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vibeosys.lawyerdiary.R;

public class RemoveAdsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_ads);
        setTitle(getResources().getString(R.string.str_remove_ads));

    }
}
