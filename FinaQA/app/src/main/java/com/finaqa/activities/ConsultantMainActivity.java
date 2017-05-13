package com.finaqa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.finaqa.R;
import com.finaqa.fragments.ConsultantHistoryFragment;
import com.finaqa.fragments.ConsultantMoreFragment;
import com.finaqa.fragments.ConsultantPaymentFragment;
import com.finaqa.fragments.ConsultantQueryFragment;

public class ConsultantMainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mQueryLayout, mHistoryLayout, mPaymentLayout, mMoreLay;
    private static final String HOME_FRAGMENT = "home";
    private static final String SEARCH_FRAGMENT = "search";
    private static final String HOST_FRAGMENT = "host";
    private static final String USER_PROFILE = "viewProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_consultant_main);
        mQueryLayout = (LinearLayout) findViewById(R.id.queryLayout);
        mHistoryLayout = (LinearLayout) findViewById(R.id.historyLayout);
        mPaymentLayout = (LinearLayout) findViewById(R.id.PaymentLayout);
        mMoreLay = (LinearLayout) findViewById(R.id.ConsultantMoreLayout);
        setUpFragment(R.id.queryLayout);
        mQueryLayout.setOnClickListener(this);
        mHistoryLayout.setOnClickListener(this);
        mPaymentLayout.setOnClickListener(this);
        mMoreLay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        setUpFragment(id);
    }

    private void setUpFragment(int id) {
        switch (id) {
            case R.id.queryLayout:
                ConsultantQueryFragment consultantQueryFragment = new ConsultantQueryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_fragment, consultantQueryFragment, HOME_FRAGMENT).commit();
                break;
            case R.id.historyLayout:
                ConsultantHistoryFragment consultantHistoryFragment = new ConsultantHistoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_fragment, consultantHistoryFragment, SEARCH_FRAGMENT).commit();
                break;
            case R.id.PaymentLayout:
                ConsultantPaymentFragment consultantPaymentFragment = new ConsultantPaymentFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_fragment, consultantPaymentFragment, HOST_FRAGMENT).commit();
                break;
            case R.id.ConsultantMoreLayout:
                ConsultantMoreFragment consultantMoreFragment = new ConsultantMoreFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_fragment, consultantMoreFragment, USER_PROFILE).commit();
                break;
        }
    }
}
