package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PartnerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_profile);
        setTitle(getResources().getString(R.string.str_my_profile));
    }
}
