package com.vibeosys.paymybill;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.login.LoginManager;
import com.vibeosys.paymybill.activities.AddBillActivity;
import com.vibeosys.paymybill.activities.AddFriendActivity;
import com.vibeosys.paymybill.activities.AllFriendsActivity;
import com.vibeosys.paymybill.activities.BaseActivity;
import com.vibeosys.paymybill.activities.ExpensesActivity;
import com.vibeosys.paymybill.activities.HistoryActivity;
import com.vibeosys.paymybill.activities.LoginActivity;
import com.vibeosys.paymybill.activities.MyProfileActivity;
import com.vibeosys.paymybill.adapters.MainActivityAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactionHandler;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.util.UserAuth;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    LinearLayout fab1;
    LinearLayout fab2;
    FloatingActionButton fab;
    private boolean flagFabClick = false;
    TabLayout tab_layout;
    private FrameLayout mFrameLayout;
    private TextView mNavigationUserEmailId, mNavigationUserName;
    private ImageView mUserProfile;
    private String mImageUri;
    private TextView txtOwesYouAmount, txtYouOwesAmount;
    private RelativeLayout adViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        boolean test = UserAuth.isUserLoggedIn();
        if (!test) {
            callToLogin();
            return;
        }
        txtOwesYouAmount = (TextView) findViewById(R.id.txtOwesYouAmount);
        txtYouOwesAmount = (TextView) findViewById(R.id.txtYouOwesAmount);
        calculateAmounts();
//        mDbRepository.insertFriend(new FriendDbDTO(1, "Abcd", "1234567890", "abc@gmail.com", "abc.jpg"));
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        fab1 = (LinearLayout) findViewById(R.id.fab_1);
        fab2 = (LinearLayout) findViewById(R.id.fab_2);
        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
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
        tab_layout.addTab(tab_layout.newTab().setText("All Bills"));

        tab_layout.addTab(tab_layout.newTab().setText("I Owe"));

        tab_layout.addTab(tab_layout.newTab().setText("Friends Owe"));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setSelectedTabIndicatorHeight(4);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        final MainActivityAdapter mainActivityAdapteradapter =
                new MainActivityAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        viewPager.setAdapter(mainActivityAdapteradapter);

        AdView adView = new AdView(getApplicationContext(), "1134020779954080_1216169785072512", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        //AdSettings.addTestDevice("HASHED ID");
        adView.loadAd();

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
        /*Drawer Menu initialization */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mNavigationUserEmailId = (TextView) headerView.findViewById(R.id.userEmailId);
        mNavigationUserName = (TextView) headerView.findViewById(R.id.userName);
        //mImageUri = mDbRepository.getUserProfileImage(mSessionManager.getUserEmailId());
        mImageUri = mSessionManager.getUserProfileImage();
        mUserProfile = (ImageView) headerView.findViewById(R.id.userProfile);
        mNavigationUserEmailId.setText("" + mSessionManager.getUserEmailId());
        mNavigationUserName.setText("" + mSessionManager.getUserName());

        if (!TextUtils.isEmpty(mImageUri)) {
            try {
                DownloadImage downloadImage = new DownloadImage();
                downloadImage.execute(mImageUri);


            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }


    }

    private void calculateAmounts() {
        double owesYouAmount = 0;
        double youOwesAmount = 0;
        FriendTransactionHandler friendTransactionHandler = new FriendTransactionHandler(mDbRepository);
        ArrayList<FriendTransactions> mFriends = friendTransactionHandler.getFriendList();
        for (FriendTransactions friendTransactions : mFriends) {
            double amount = friendTransactions.getAmount();
            if (amount < 0) {
                youOwesAmount = youOwesAmount + amount;
            } else if (amount > 0) {
                owesYouAmount = owesYouAmount + amount;
            }
        }
        txtOwesYouAmount.setText(mSessionManager.getUserCurrencySymbol() + " " + String.format("%.2f", owesYouAmount));
        youOwesAmount = youOwesAmount < 0 ? -(youOwesAmount) : youOwesAmount;
        txtYouOwesAmount.setText(String.format(mSessionManager.getUserCurrencySymbol() + " " + "%.2f", youOwesAmount));

    }

    private void callToLogin() {
        Intent loginactivity = new Intent(MainActivity.this, LoginActivity.class);
        /*loginactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        loginactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        /*loginactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        startActivity(loginactivity);
        // moveTaskToBack(true);
        finish();

    }

    private void startAddBill() {
        Intent iAddBill = new Intent(getApplicationContext(), AddBillActivity.class);
        startActivity(iAddBill);
        hideFloating();
    }

    private void startAddExpenses() {
        Intent iAddBill = new Intent(getApplicationContext(), ExpensesActivity.class);
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
            finish();
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
        if (id == R.id.add_friend) {
            Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
            startActivity(intent);
            //  finish();
        }

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
            Intent myProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
            startActivity(myProfile);

        } else if (id == R.id.nav_my_friends) {
            /*startActivity(new Intent(getApplicationContext(), ProfileActivity.class));*/
            Intent allFriend = new Intent(getApplicationContext(), AllFriendsActivity.class);
            startActivity(allFriend);

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/ else if (id == R.id.sign_out) {
            if (mSessionManager.getLoginSource().equals("1")) {
                try {
                    FacebookSdk.sdkInitialize(getApplicationContext());
                    LoginManager.getInstance().logOut();
                } catch (FacebookException e) {
                    Log.e(TAG, e.toString());
                }

                boolean returnVal = mDbRepository.deleteAllUserFriendRecords();
                UserAuth.CleanAuthenticationInfo();
            }
            if (mSessionManager.getLoginSource().equals("2")) {
                boolean returnVal = mDbRepository.deleteAllUserFriendRecords();
                UserAuth.CleanAuthenticationInfo();

            }
            if (mSessionManager.getLoginSource().equals("3")) {
                boolean returnVal = mDbRepository.deleteAllUserFriendRecords();
                UserAuth.CleanAuthenticationInfo();

            }
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

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {


            Bitmap bitmap = null;

            String imgStr = URL[0].toString();
            try {

                bitmap = BitmapFactory.decodeFile(imgStr);
            } catch (Exception e) {
                e.printStackTrace();
                bitmap = null;
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                mUserProfile.setImageBitmap(result);
            } /*else {
                mUserProfile.setImageResource(R.drawable.avatar_profile);
            }*/
        }
    }
}
