package com.finaqa.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.finaqa.fragments.FragmentAnswer;
import com.finaqa.fragments.FragmentMore;
import com.finaqa.fragments.FragmentPayment;
import com.finaqa.fragments.FragmentQuery;

import java.util.ArrayList;

/**
 * Created by shrinivas on 06-05-2017.
 */
public class DashBoardAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    // Tab Titles
    private String tabtitles[] = new String[]{"Answer", "Payment", "New Query", "More"};
    Context context;

    public DashBoardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                FragmentAnswer fragmentAnswer = new FragmentAnswer();
                return fragmentAnswer;

            // Open FragmentTab2.java
            case 1:
                FragmentPayment fragmentPayment = new FragmentPayment();
                return fragmentPayment;

            // Open FragmentTab3.java
            case 2:
                FragmentQuery fragmentQuery = new FragmentQuery();
                return fragmentQuery;
            case 3:
                FragmentMore fragmentMore = new FragmentMore();
                return fragmentMore;

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
