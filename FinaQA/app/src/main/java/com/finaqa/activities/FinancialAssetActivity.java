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
import android.widget.TextView;

import com.finaqa.R;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;


public class FinancialAssetActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mLiquid;
    LinearLayout linearLayout;
    TextView mTooltip, label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_asset);
        mButton = (Button) findViewById(R.id.next);
        mLiquid = (ImageView) findViewById(R.id.liquidImageView);
        linearLayout = (LinearLayout) findViewById(R.id.tooltipView);
        label = (TextView) findViewById(R.id.label123);
        mLiquid.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                   .anchorView(mLiquid)
                                                   .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                                                           " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam")
                                                   .gravity(Gravity.END)
                                                   .animated(true)
                                                   .transparentOverlay(false)
                                                   .build()
                                                   .show();
                                       }
                                   }
        );


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinancialAdviceLoan.class);
                startActivity(intent);
            }
        });
    }


}
