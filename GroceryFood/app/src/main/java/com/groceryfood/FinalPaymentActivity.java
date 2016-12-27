package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FinalPaymentActivity extends AppCompatActivity {

    LinearLayout payLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_payment);
        setTitle(getResources().getString(R.string.confirm_and_pay));
        payLay = (LinearLayout) findViewById(R.id.payLay);

        payLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
