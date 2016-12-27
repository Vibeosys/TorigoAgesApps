package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PatnerRenewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patner_renew);
        setTitle(getString(R.string.str_renew));
    }
}
