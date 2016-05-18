package com.vibeosys.fitnessapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.PurchaseAdapter;
import com.vibeosys.fitnessapp.data.PurchaseProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrinivas on 13-05-2016.
 */
public class ServicesActivity extends AppCompatActivity implements PurchaseAdapter.CustomButtonListener {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_list_view);
        setTitle("Product List");
        ListView serviceList = (ListView) findViewById(R.id.service_list);
        List<PurchaseProduct> productList = new ArrayList<>();
        productList.add(new PurchaseProduct("Dedicated Trainer", "Trainer will train 1 hour per day per month", 1500));
        productList.add(new PurchaseProduct("Yoga Trainer", "Trainer will train 1 hour per day per month", 2000));
        productList.add(new PurchaseProduct("Optimum Nutrition", "Is a 100% pure creatine monohydrate powder", 899));
        productList.add(new PurchaseProduct("British Nutrition", "Recovery glutamine formula is a unique glutamine formula.", 767));
        productList.add(new PurchaseProduct("RiteBite Max Protein", "Rich in cocoa It's a meal replacement bar.", 600));
        productList.add(new PurchaseProduct("Dymatize Super", "DYMATIZE SUPER AMINO 6000 MG 180 CAPLETS - NEW !!!", 1300));

        PurchaseAdapter adapter = new PurchaseAdapter(productList, getApplicationContext());
        adapter.setCustomButtonListner(this);
        serviceList.setAdapter(adapter);
    }

    @Override
    public void onButtonClickListener(int id, int position, double value, PurchaseProduct product) {
        Intent intent = new Intent(getApplicationContext(), CardActivity.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDesc", product.getDesc());
        intent.putExtra("productPrice", product.getPrice());
        startActivity(intent);
       /* List<String> scopes = new ArrayList<>();
        scopes.add("Pay Pal");
        PaymentRequest paymentRequest = new PaymentRequest()
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJiZjQ3ZWM4MWRjYjMwZmQ5Zjc1MWE5YzQzZDZkYzRlZGY4YWJkNDE4N2U4NzE4ZDQ4ZDQ2OGRiZmM4Mzc3NjQ5fGNyZWF0ZWRfYXQ9MjAxNi0wNC0xMlQwNToyMjoxMS4wNDY0MjU1NzIrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=")
                .primaryDescription(product.getProductName())
                .secondaryDescription(product.getDesc())
                .amount("" + product.getPrice() + " " + getResources().getString(R.string.rupess_symbol))
                .submitButtonText("Purchase")
                .actionBarLogo(R.mipmap.ic_launcher);
        startActivityForResult(paymentRequest.getIntent(this), REQUEST_CODE);*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                        BraintreePaymentActivity.UI_MODE_SERVICE
                );
                String nonce = paymentMethodNonce.getNonce();
                // Send the nonce to your server.
            }
        }
    }*/
}
