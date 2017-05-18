package com.finaqa;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.finaqa.adapter.DashBoardAdapter;
import com.finaqa.fragments.FragmentAnswer;
import com.finaqa.fragments.FragmentMore;
import com.finaqa.fragments.FragmentPayment;
import com.finaqa.fragments.FragmentQuery;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private LinearLayout mHomeLay, mSearchLay, mHostLay, mMoreLay;
    private static final String HOME_FRAGMENT = "home";
    private static final String SEARCH_FRAGMENT = "search";
    private static final String HOST_FRAGMENT = "host";
    private static final String MORE_FRAGMENT = "more";
    private static final String USER_FRAGMENT = "user";
    private static final String USER_PROFILE = "viewProfile";
    private static final String PURCHASE_FRAGMENT = "purchase";
    private static final String USER_LIST_FRAGEMNT = "user_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setUpFragment(R.id.homeLay);
        mHomeLay = (LinearLayout) findViewById(R.id.homeLay);
        mSearchLay = (LinearLayout) findViewById(R.id.searchLay);
        mHostLay = (LinearLayout) findViewById(R.id.hostLay);
        mMoreLay = (LinearLayout) findViewById(R.id.moreLay);
        mHomeLay.setOnClickListener(this);
        mSearchLay.setOnClickListener(this);
        mHostLay.setOnClickListener(this);
        mMoreLay.setOnClickListener(this);

    }

    private void setUpFragment(int i) {
        switch (i) {
            case R.id.homeLay:
                FragmentAnswer fragmentAnswer = new FragmentAnswer();
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragmentAnswer, HOME_FRAGMENT).commit();
                break;
            case R.id.searchLay:
                FragmentPayment fragmentPayment = new FragmentPayment();
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragmentPayment, SEARCH_FRAGMENT).commit();
                break;
            case R.id.hostLay:
                FragmentQuery fragmentQuery = new FragmentQuery();
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragmentQuery, HOST_FRAGMENT).commit();
                break;
            case R.id.moreLay:
                FragmentMore fragmentMore = new FragmentMore();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame_lay, fragmentMore, USER_PROFILE).commit();


                break;
            default:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        setUpFragment(id);
    }
}
