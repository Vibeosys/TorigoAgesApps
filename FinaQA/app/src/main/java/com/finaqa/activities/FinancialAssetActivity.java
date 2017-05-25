package com.finaqa.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finaqa.R;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;

public class FinancialAssetActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mLiquid;
    ToolTipRelativeLayout toolTipRelativeLayout;
    ToolTip toolTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_asset);
        mButton = (Button) findViewById(R.id.next);
        mLiquid = (ImageView) findViewById(R.id.liquidImageView);
        toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipRelativeLayout1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinancialAdviceLoan.class);
                startActivity(intent);
            }
        });
        toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipRelativeLayout1);


        mLiquid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolTip = new ToolTip()
                        .withText("A beautiful View")
                        .withColor(Color.RED)
                        .withShadow()
                        .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW);
                toolTipRelativeLayout.showToolTipForView(toolTip, findViewById(R.id.liquidImageView));

            }

        });
    }


}
