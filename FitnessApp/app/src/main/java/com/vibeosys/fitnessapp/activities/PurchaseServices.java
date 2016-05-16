package com.vibeosys.fitnessapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.PurchaseAdapter;
import com.vibeosys.fitnessapp.adapters.PurchaseServicesAdapter;
import com.vibeosys.fitnessapp.data.PurchaseProduct;

import java.util.ArrayList;
import java.util.List;

public class PurchaseServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_services);
        ListView serviceList = (ListView) findViewById(R.id.service_list);
        List<PurchaseProduct> productList = new ArrayList<>();
        setTitle("Services availed for 2016");
        productList.add(new PurchaseProduct("Dedicated Trainer", "20 Mar 2016 to 19 Apr 2016.", 1500));
        productList.add(new PurchaseProduct("British Nutrition", "Recovery glutamine.", 767));
        productList.add(new PurchaseProduct("Dymatize Super", "6000 MG 180 CAPLETS.", 1300));

        PurchaseServicesAdapter adapter = new PurchaseServicesAdapter(productList, getApplicationContext());
        serviceList.setAdapter(adapter);
    }

    public void serviceOpen(View v) {
        startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
    }
}
