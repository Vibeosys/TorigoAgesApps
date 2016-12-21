package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayTrackOrdersActivity extends AppCompatActivity {
    TextView mOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_track_orders);
        setTitle(getString(R.string.str_track_order_title));
        mOrderDetails = (TextView) findViewById(R.id.orderDetails);
        mOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
