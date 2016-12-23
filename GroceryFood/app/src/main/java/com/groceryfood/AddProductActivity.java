package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddProductActivity extends AppCompatActivity {
    LinearLayout mLinearLayout;
    Button mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setTitle("Product List");
        mLinearLayout = (LinearLayout) findViewById(R.id.addProductTitle);
        mEdit = (Button) findViewById(R.id.edit_btn);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
