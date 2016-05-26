package com.vibeosys.paymybill.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vibeosys.paymybill.fragment.AllFriendFragment;
import com.vibeosys.paymybill.fragment.LentFragment;
import com.vibeosys.paymybill.fragment.OweFragment;

/**
 * Created by shrinivas on 26-05-2016.
 */
public class MainActivityAdapter  extends FragmentPagerAdapter {
    private int itemCount;

    public MainActivityAdapter(FragmentManager fm, int count) {
        super(fm);
        this.itemCount = count;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                AllFriendFragment mAllFriendFragment = new AllFriendFragment();
                return mAllFriendFragment;
            case 1:
                LentFragment mLentFragment = new LentFragment();
                return mLentFragment;
            case 2:
                OweFragment mOweFragment = new OweFragment();
                return mOweFragment;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return itemCount ;
    }
}
