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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.finaqa.R;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;


public class FinancialAssetActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mLiquid, mLiquid2, mLiquid3, mLiquid4, mLiquid5;
    LinearLayout linearLayout;
    TextView mTooltip, label;
    private Spinner mDepend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_asset);
        mButton = (Button) findViewById(R.id.next);
        mDepend = (Spinner) findViewById(R.id.spinner2);
        mTooltip = (TextView) findViewById(R.id.label123);
        linearLayout = (LinearLayout) findViewById(R.id.MasterView);
        mLiquid = (ImageView) findViewById(R.id.liquidImageView);
        mLiquid2 = (ImageView) findViewById(R.id.tooltip2);
        mLiquid3 = (ImageView) findViewById(R.id.tooltip3);
        mLiquid4 = (ImageView) findViewById(R.id.tooltip4);
        mLiquid5 = (ImageView) findViewById(R.id.tooltip5);
        linearLayout = (LinearLayout) findViewById(R.id.tooltipView);
        label = (TextView) findViewById(R.id.label123);
        List<String> mDependent = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mDependent.add("" + i);
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(FinancialAssetActivity.this,
                android.R.layout.simple_spinner_item, mDependent);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepend.setAdapter(dataAdapter1);
        mLiquid.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                   .anchorView(linearLayout)
                                                   .text("Cash Assets - (Total of:-Saving A/c, Current a/c, Fixed Deposit (term<1yr), Recurring Deposit)")
                                                   .gravity(Gravity.END)
                                                   .animated(true)
                                                   .transparentOverlay(false)
                                                   .build()
                                                   .show();
                                       }
                                   }
        );
        mLiquid2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                    .anchorView(mLiquid2)
                                                    .text("Equity - ( Total of:-value of Shares, Fund Value of Ulips, Equity Mutual Funds, Balance Funds (equity oriented) etc..)")
                                                    .gravity(Gravity.END)
                                                    .animated(true)
                                                    .transparentOverlay(false)
                                                    .build()
                                                    .show();
                                        }
                                    }
        );

        mLiquid3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                    .anchorView(mLiquid3)
                                                    .text("Debt - (Total of:- Bonds, Fixed Deposits (term > 1 yr), Empolyee Provident Fund, Post office schemes, Public Provident Fund, Pension Funds, Debt Funds etc. )")
                                                    .gravity(Gravity.END)
                                                    .animated(true)
                                                    .transparentOverlay(false)
                                                    .build()
                                                    .show();
                                        }
                                    }
        );
        mLiquid4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                    .anchorView(mLiquid4)
                                                    .text("Real Estate - (Total Market values of:- 2nd House, Land, shop, office space, REITs  etc.)")
                                                    .gravity(Gravity.END)
                                                    .animated(true)
                                                    .transparentOverlay(false)
                                                    .build()
                                                    .show();
                                        }
                                    }
        );

        mLiquid5.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new SimpleTooltip.Builder(FinancialAssetActivity.this)
                                                    .anchorView(mLiquid5)
                                                    .text("Precious Metals - (Total value of:- Gold Exchange Traded Funds, Bar, Rings, Silver coins/ biscuits etc. )")
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
