package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class OrderFinishActivity extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.place_order));
        setContentView(R.layout.activity_oreder_finish);
        layout = (LinearLayout) findViewById(R.id.bottom_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinalPaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
