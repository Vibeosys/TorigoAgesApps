package com.groceryfood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.groceryfood.fragments.CakesFragment;
import com.groceryfood.fragments.LiveOrdersFragment;
import com.groceryfood.fragments.PastOrdersFragment;
import com.groceryfood.fragments.SaladFragment;

/**
 * Created by shrinivas on 20-12-2016.
 */
public class TrackOrdersAdapter extends FragmentPagerAdapter {
    private int itemCount;

    public TrackOrdersAdapter(FragmentManager fm, int count) {
        super(fm);
        this.itemCount = count;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LiveOrdersFragment liveOrdersFragment = new LiveOrdersFragment();
                return liveOrdersFragment;
            case 1:
                PastOrdersFragment pastOrders = new PastOrdersFragment();
                return pastOrders;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return itemCount;
    }
}
