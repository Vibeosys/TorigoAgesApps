package com.vibeosys.lawyerdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.vibeosys.lawyerdiary.Adapter.NewsFeedAdapter;
import com.vibeosys.lawyerdiary.activities.BaseActivity;
import com.vibeosys.lawyerdiary.activities.CalenderViewActivity;
import com.vibeosys.lawyerdiary.activities.CasesActivity;
import com.vibeosys.lawyerdiary.activities.ClientActivity;
import com.vibeosys.lawyerdiary.activities.EventDetailsActivity;
import com.vibeosys.lawyerdiary.activities.LoginActivity;
import com.vibeosys.lawyerdiary.activities.SettingsActivity;
import com.vibeosys.lawyerdiary.activities.SheduleActivity;
import com.vibeosys.lawyerdiary.data.NewsFeedData;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.DateUtils;
import com.vibeosys.lawyerdiary.utils.UserAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView;
    private GridView gridNews;
    private NewsFeedAdapter adapter;
    private DateUtils dateUtils = new DateUtils();
    Calendar todayCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
        mAdView.loadAd(adRequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridNews = (GridView) findViewById(R.id.newsFeedGrid);
        boolean checkLogin = UserAuth.isUserLoggedIn();
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
                if (dateUtils.getLocalDateInFormat(startDate).equals(dateUtils.getLocalDateInFormat(todayCalender.getTime()))) {
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
                    NewsFeedData event = new NewsFeedData(eventId, name, desc);
                    events.add(event);
                }

            }
            while (eventCursor.moveToNext());
        }
        return events;
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
}
