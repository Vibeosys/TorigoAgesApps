package com.groceryfood;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groceryfood.adapters.MainActivityAdapter;
import com.groceryfood.adapters.TrackOrdersAdapter;

public class TrackOrdersActivity extends AppCompatActivity {
    TabLayout trackOrder_Tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        setTitle("");

        trackOrder_Tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        trackOrder_Tab_layout.addTab(trackOrder_Tab_layout.newTab().setText(getResources().getString(R.string.str_live_track)));

        trackOrder_Tab_layout.addTab(trackOrder_Tab_layout.newTab().setText(getResources().getString(R.string.str_past_orders)));

        trackOrder_Tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        trackOrder_Tab_layout.setSelectedTabIndicatorHeight(4);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        final TrackOrdersAdapter trackOrdersAdapter =
                new TrackOrdersAdapter(getSupportFragmentManager(), trackOrder_Tab_layout.getTabCount());
        viewPager.setAdapter(trackOrdersAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(trackOrder_Tab_layout));
        trackOrder_Tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
