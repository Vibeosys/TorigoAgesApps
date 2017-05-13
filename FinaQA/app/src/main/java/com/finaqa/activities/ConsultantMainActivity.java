package com.finaqa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.finaqa.R;

public class ConsultantMainActivity extends AppCompatActivity  implements View.OnClickListener{
    private LinearLayout mHomeLay, mSearchLay, mHostLay, mMoreLay;
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
        mHomeLay = (LinearLayout) findViewById(R.id.homeLay);
        mSearchLay = (LinearLayout) findViewById(R.id.searchLay);
        mHostLay = (LinearLayout) findViewById(R.id.hostLay);
        mMoreLay = (LinearLayout) findViewById(R.id.moreLay);
        mHomeLay.setOnClickListener(this);
        mSearchLay.setOnClickListener(this);
        mHostLay.setOnClickListener(this);
        mMoreLay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        setUpFragment(id);
    }

    private void setUpFragment(int id) {
    }
}
