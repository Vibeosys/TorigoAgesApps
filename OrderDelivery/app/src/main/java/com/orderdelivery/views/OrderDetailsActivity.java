package com.orderdelivery.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orderdelivery.R;

public class OrderDetailsActivity extends AppCompatActivity {
    private Button mStartTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        setTitle("Dashboard");
        mStartTrip = (Button) findViewById(R.id.start_trip);
        mStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewTripDetails.class);
                startActivity(intent);
            }
        });
    }
}
