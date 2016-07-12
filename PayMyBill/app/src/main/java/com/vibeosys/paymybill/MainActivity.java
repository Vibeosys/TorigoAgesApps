package com.vibeosys.paymybill;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.vibeosys.paymybill.activities.AddBillActivity;
import com.vibeosys.paymybill.activities.BaseActivity;
import com.vibeosys.paymybill.activities.ExpencesesActivity;
import com.vibeosys.paymybill.activities.HistoryActivity;
import com.vibeosys.paymybill.activities.LoginActivity;
import com.vibeosys.paymybill.activities.MyProfileActivity;
import com.vibeosys.paymybill.adapters.MainActivityAdapter;
import com.vibeosys.paymybill.util.UserAuth;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    LinearLayout fab1;
    LinearLayout fab2;
    FloatingActionButton fab;
    private boolean flagFabClick = false;
    TabLayout tab_layout;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!UserAuth.isUserLoggedIn())
        {
            callToLogin();
            return;
        }
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        fab1 = (LinearLayout) findViewById(R.id.fab_1);
        fab2 = (LinearLayout) findViewById(R.id.fab_2);
        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //getWindow().setExitTransition(new Explode());
//                //startActivity();
                if (flagFabClick) {
                    hideFloating();
                } else {
                    displayFloating();
                }

            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddBill();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddExpenses();
            }
        });
        tab_layout.addTab(tab_layout.newTab().setText("All Friends"));

        tab_layout.addTab(tab_layout.newTab().setText("I Owe"));

        tab_layout.addTab(tab_layout.newTab().setText("Friends Owe"));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setSelectedTabIndicatorHeight(4);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        final MainActivityAdapter mainActivityAdapteradapter =
                new MainActivityAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        viewPager.setAdapter(mainActivityAdapteradapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void callToLogin() {
        Intent loginactivity = new Intent(MainActivity.this,LoginActivity.class);
        loginactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginactivity);
        finish();
    }

    private void startAddBill() {
        Intent iAddBill = new Intent(getApplicationContext(), AddBillActivity.class);
        startActivity(iAddBill);
        hideFloating();
    }

    private void startAddExpenses() {
        Intent iAddBill = new Intent(getApplicationContext(), ExpencesesActivity.class);
        startActivity(iAddBill);
        hideFloating();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            /*startActivity(new Intent(getApplicationContext(), ProfileActivity.class));*/
            Intent signInIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
            startActivity(signInIntent);

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/ else if (id == R.id.sign_out) {
            Intent signInIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(signInIntent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFloating() {
        flagFabClick = true;
        mFrameLayout.setBackgroundColor(getResources().getColor(R.color.transperentWhite));
        Animation show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_rotate);
        fab.startAnimation(show_fab);
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams1.rightMargin += (int) (fab1.getWidth() * 0.20 / 2);
        layoutParams1.bottomMargin += (int) (fab1.getHeight() * 1.9 / 2);
        fab1.setLayoutParams(layoutParams1);
        Animation show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 0.15 / 2);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 3.4 / 2);
        fab2.setLayoutParams(layoutParams2);
        Animation show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

    }

    private void hideFloating() {
        flagFabClick = false;
        mFrameLayout.setBackgroundColor(getResources().getColor(R.color.transperent));
        Animation show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_rotate_reverse);
        fab.startAnimation(show_fab);
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams1.rightMargin -= (int) (fab1.getWidth() * 0.20 / 2);
        layoutParams1.bottomMargin -= (int) (fab1.getHeight() * 1.9 / 2);
        fab1.setLayoutParams(layoutParams1);
        Animation hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 0.15 / 2);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 3.4 / 2);
        fab2.setLayoutParams(layoutParams2);
        Animation hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);
    }
}
