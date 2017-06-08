package com.finaqa.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.finaqa.R;


public class FinancialAssetActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mLiquid;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_asset);
        mButton = (Button) findViewById(R.id.next);
        mLiquid = (ImageView) findViewById(R.id.liquidImageView);
        linearLayout = (LinearLayout) findViewById(R.id.tooltipView);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinancialAdviceLoan.class);
                startActivity(intent);
            }
        });
    }


}
