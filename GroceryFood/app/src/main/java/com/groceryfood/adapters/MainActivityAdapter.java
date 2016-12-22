package com.groceryfood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.groceryfood.fragments.BiscuitFragment;
import com.groceryfood.fragments.CakesFragment;
import com.groceryfood.fragments.SaladFragment;

/**
 * Created by shrinivas on 15-12-2016.
 */
public class MainActivityAdapter extends FragmentPagerAdapter {
    private int itemCount;

    public MainActivityAdapter(FragmentManager fm, int count) {
        super(fm);
        this.itemCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SaladFragment saladFragment = new SaladFragment();
                return saladFragment;
            case 1:
                CakesFragment cakesFragment = new CakesFragment();
                return cakesFragment;
            case 2:
                BiscuitFragment biscuitFragment = new BiscuitFragment();
                return biscuitFragment;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return itemCount;
    }
}
