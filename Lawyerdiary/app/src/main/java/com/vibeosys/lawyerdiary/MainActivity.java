package com.vibeosys.lawyerdiary;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.vibeosys.lawyerdiary.Adapter.NewsFeedAdapter;
import com.vibeosys.lawyerdiary.activities.BaseActivity;
import com.vibeosys.lawyerdiary.activities.CalenderViewActivity;
import com.vibeosys.lawyerdiary.activities.CasesActivity;
import com.vibeosys.lawyerdiary.activities.ClientActivity;
import com.vibeosys.lawyerdiary.activities.EventDetailsActivity;
import com.vibeosys.lawyerdiary.activities.FeedbackActivity;
import com.vibeosys.lawyerdiary.activities.LoginActivity;
import com.vibeosys.lawyerdiary.activities.RemoveAdsActivity;
import com.vibeosys.lawyerdiary.activities.SettingsActivity;
import com.vibeosys.lawyerdiary.activities.SheduleActivity;
import com.vibeosys.lawyerdiary.data.NewsFeedData;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.AppConstant;
import com.vibeosys.lawyerdiary.utils.DateUtils;
import com.vibeosys.lawyerdiary.utils.UserAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * MainActivity is launching activity of the application.
 * NewsFeedAdapter is use to display all latest event list to the user.
 * This activity also uses service connection to check whether user have purchase billing service or not.
 * It also contain banner advertise from ad mob.
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView;
    private GridView gridNews;
    private NewsFeedAdapter adapter;
    private DateUtils dateUtils = new DateUtils();
    Calendar todayCalender = Calendar.getInstance();
    private TextView mUserNameNavigation, mUserEmailIdNavigation;
    IInAppBillingService mService;
    ServiceConnection mServiceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mServiceConnection  = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService= IInAppBillingService.Stub.asInterface(service);
                isPurchase();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                mService=null;
            }
        };

        if(mSessionManager.getInAppPurchase()!=1)
        {
            int test  = mSessionManager.getInAppPurchase();
            Log.d("TAG","TAG");
            Log.d("TAG","TAG");
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
            mAdView.loadAd(adRequest);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridNews = (GridView) findViewById(R.id.newsFeedGrid);
        boolean checkLogin = UserAuth.isUserLoggedIn();
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        getApplicationContext().bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        if (!checkLogin) {
            callToLogin();
            return;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        adapter = new NewsFeedAdapter(retrieveData(), getApplicationContext());
        gridNews.setAdapter(adapter);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mUserNameNavigation = (TextView) headerView.findViewById(R.id.nav_userName);
        mUserEmailIdNavigation = (TextView) headerView.findViewById(R.id.nav_userEmailId);
            /*Check for purchase item*/
        if(mSessionManager.getInAppPurchase()==AppConstant.ITEM_PURCHASED)
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_subscriber);
        }
        else
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
        mUserNameNavigation.setText("" + mSessionManager.gerUserName());
        mUserEmailIdNavigation.setText("" + mSessionManager.getUserEmailId());

        gridNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsFeedData data = (NewsFeedData) adapter.getItem(position);
                Intent iSchedule = new Intent(getApplicationContext(), EventDetailsActivity.class);
                iSchedule.putExtra(EventDetailsActivity.EVENT_ID, data.get_id());
                startActivity(iSchedule);
            }
        });
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
        if (id == R.id.action_log_out) {
            UserAuth.CleanAuthenticationInfo();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_calendar) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(), CalenderViewActivity.class);
            startActivity(i);

        } else if (id == R.id.clients) {
            Intent client_intent = new Intent(getApplicationContext(), ClientActivity.class);
            startActivity(client_intent);


        } else if (id == R.id.my_cases) {
            Intent i = new Intent(getApplicationContext(), CasesActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);

        } else if(id==R.id.nav_removeAds)
        {
            Intent removeAd = new Intent(getApplicationContext(), RemoveAdsActivity.class);
            startActivity(removeAd);

        }
        else if(id== R.id.nav_feedback)
        {
            Intent feedback = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(feedback);
        }
        else if(id==R.id.nav_log_out)
        {
            UserAuth.CleanAuthenticationInfo();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            ///finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void viewCase(View v) {

    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {

        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    /**
     * retrieveData() function is use to fetch event list from local data base.
     * This function is having event details like event start date,time ,location and event name.
     * @return Array list having NewsFeedData class as data type.
     */
    private ArrayList<NewsFeedData> retrieveData() {
        ArrayList<NewsFeedData> events = new ArrayList<>();
        Cursor eventCursor = getApplicationContext().getContentResolver().query(LawyerContract.Reminder.CONTENT_URI,
                new String[]{LawyerContract.Reminder._ID, LawyerContract.Reminder.REMINDER_NAME,
                        LawyerContract.Reminder.START_DATE_TIME, LawyerContract.Reminder.END_DATE_TIME,
                        LawyerContract.Reminder.LOCATION, LawyerContract.Reminder.NOTE, LawyerContract.Reminder.COLOUR
                }, null, null, null);

        if (eventCursor.getCount() > 0) {
            eventCursor.moveToFirst();
            do {
                long eventId = eventCursor.getLong(eventCursor.getColumnIndex(LawyerContract.Reminder._ID));
                String name = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.REMINDER_NAME));
                String strStartTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.START_DATE_TIME));
                String strEndTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.END_DATE_TIME));
                Date startDate = dateUtils.getFormattedDate(strStartTime);
                Date endDate = dateUtils.getFormattedDate(strEndTime);
                String location = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.LOCATION));
                String note = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.NOTE));
                String desc = "Start on " + dateUtils.getLocalTimeInReadableFormat(startDate) + " to " + dateUtils.getLocalTimeInReadableFormat(endDate);
                if (!TextUtils.isEmpty(location)) {
                    desc = desc + "\nlocation will be " + location;
                } else {
                    desc = desc + "\n";
                }
                if (!TextUtils.isEmpty(note)) {
                    desc = desc + "\nNote - " + note;
                } else {
                    desc = desc + "\n";
                }
               /* if (dateUtils.getLocalDateInFormat(startDate).equals(dateUtils.getLocalDateInFormat(todayCalender.getTime())))

                }*/
                NewsFeedData event = new NewsFeedData(eventId, "Reminder Details", name, desc, startDate.getTime());
                events.add(event);

            }
            while (eventCursor.moveToNext());
        }
        eventCursor.close();


        Cursor clientCursor = getApplicationContext().getContentResolver().query(LawyerContract.Client.CONTENT_URI,
                new String[]{LawyerContract.Client._ID, LawyerContract.Client.NAME,
                        LawyerContract.Client.PH_NUMBER, LawyerContract.Client.EMAIL,
                        LawyerContract.Client.DATE_TIME, LawyerContract.Client.ADDRESS}, null, null, null);

        if (clientCursor.getCount() > 0) {
            clientCursor.moveToFirst();
            do {
                long clientId = clientCursor.getLong(clientCursor.getColumnIndex(LawyerContract.Client._ID));
                long dateTime = clientCursor.getLong(clientCursor.getColumnIndex(LawyerContract.Client.DATE_TIME));
                String clientName = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.NAME));
                String phNo = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.PH_NUMBER));
                String email = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.EMAIL));
                String address = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Client.ADDRESS));
                String desc = "Ph No:" + phNo;
                if (!TextUtils.isEmpty(email)) {
                    desc = desc + "\nEmail Id:" + email;
                } else {
                    desc = desc + "\n";
                }
                if (!TextUtils.isEmpty(address)) {
                    desc = desc + "\nAddress:" + address;
                } else {
                    desc = desc + "\n";
                }
                NewsFeedData event = new NewsFeedData(clientId, "New Client", clientName, desc, dateTime);
                events.add(event);
            } while (clientCursor.moveToNext());
        }

        clientCursor.close();
        Collections.sort(events);
        return events;
    }

    /**
     * This function is get called when Session manager is not having user credentials.
     * It is use to navigate to LoginActivity class.
     */
    private void callToLogin() {
        Intent loginactivity = new Intent(MainActivity.this, LoginActivity.class);
        /*loginactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        loginactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        /*loginactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        startActivity(loginactivity);
        // moveTaskToBack(true);
        finish();

    }

    /**
     * isPurchase() function is use to check whether application is billing supported or not.
     */
    public void isPurchase()
    {
        int isBillingSupported = -1;
        try {
            isBillingSupported = mService.isBillingSupported(3, getPackageName(), "inapp");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (isBillingSupported != 0) {
            Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainAct);
            finish();
        } else
            getInAppPurchases();

    }

    /**
     * getInAppPurchases() this function is called when application is billing supported.
     * It is use to store purchase item details in to SessionManager.
     */
    public void getInAppPurchases() {
        try {
            Bundle purchaseItems = mService.getPurchases(3, getPackageName(), "inapp", null);
            int responseCode = purchaseItems.getInt("RESPONSE_CODE");
            if (responseCode == 0) {
                ArrayList<String> items = purchaseItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                if (items.contains("com.lawyerdiary.noads")) {
                  //  Toast.makeText(getApplicationContext(), "Product is already purchased", Toast.LENGTH_SHORT).show();
                    mSessionManager.setInAppPurchase(AppConstant.ITEM_PURCHASED);
                } else {
                    mSessionManager.setInAppPurchase(AppConstant.ITEM_NOT_PURCHASED);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
