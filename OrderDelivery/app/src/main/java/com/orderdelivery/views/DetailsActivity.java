package com.orderdelivery.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orderdelivery.R;

public class DetailsActivity extends AppCompatActivity {
    private Button mAccept, mReject;
    private TextView mDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Order details");
        mAccept = (Button) findViewById(R.id.acceptBtn);
        mReject = (Button) findViewById(R.id.rejectBtn);
        mDetails = (TextView) findViewById(R.id.details_1);

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
        mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
