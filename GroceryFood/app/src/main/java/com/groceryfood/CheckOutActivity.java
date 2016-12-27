package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckOutActivity extends AppCompatActivity {

    TextView addMore, checkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setTitle(getResources().getString(R.string.str_my_cart));
        addMore = (TextView) findViewById(R.id.txtAddMore);
        checkOut = (TextView) findViewById(R.id.checkOut);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderFinishActivity.class);
                startActivity(intent);
            }
        });
    }
}
