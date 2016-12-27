package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitle(getResources().getString(R.string.str_add_new_product));
    }
}
