package com.vibeosys.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.MainActivity;
import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.data.UserInfo;
import com.vibeosys.fitnessapp.utils.DateUtils;
import com.vibeosys.fitnessapp.utils.UserAuth;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shrinivas on 21-04-2016.
 */
public class DashboardActivity extends BaseActivity {

    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> navDrawerItems;
    ImageView worksheet, diat, InstructionNotes, feedBack;
    ImageView messageChat;
    ImageView purchase;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    private Calendar mCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_drawer);

        worksheet = (ImageView) findViewById(R.id.circular_image_view1);
        diat = (ImageView) findViewById(R.id.circular_image_view2);
        InstructionNotes = (ImageView) findViewById(R.id.circular_image_view3);
        feedBack = (ImageView) findViewById(R.id.circular_image_view4);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Dash board");
        UserInfo userInfo = getIntent().getParcelableExtra("UserInfo");
        if (userInfo != null) {
            String UserName = userInfo.getUserName();
            String userEmailId = userInfo.getUserEmailId();
        }
        toggle = new ActionBarDrawerToggle
                (
                        this,
                        drawerLayout,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                ) {
        };
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem("My Profile", R.drawable.ic_profile));
        navDrawerItems.add(new NavDrawerItem("B M I", R.drawable.ic_yoga_ico));
        navDrawerItems.add(new NavDrawerItem("Photo", R.drawable.ic_photo_icon));
        NavigationDrawerListAdapter adapter = new NavigationDrawerListAdapter(this, navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        worksheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent worksheet = new Intent(getApplicationContext(), SelectWorkoutActivity.class);
                startActivity(worksheet);
            }
        });
        diat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dietPlan = new Intent(getApplicationContext(), DietPlanActivity.class);
                startActivity(dietPlan);
            }
        });
        InstructionNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructions = new Intent(getApplicationContext(), WorkoutReportActivity.class);
                startActivity(instructions);
            }
        });
        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        try {
            Date lastWorkOutDate = DateUtils.dateWithoutTime(new Date(appWorkoutData.getWorkoutDate()));
            Date currentDate = DateUtils.dateWithoutTime(mCalender.getTime());
            if (appWorkoutData.getWorkoutDate() > 0) {
                if (currentDate.compareTo(lastWorkOutDate) == 0) {
                    // Current date
                    if (appWorkoutData.getWorkoutId() > 0) {
                        Intent intent = new Intent(getApplicationContext(), SelectSetActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong(SelectSetActivity.WKM_ID, appWorkoutData.getWorkoutId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), SelectWorkoutActivity.class));
                        finish();
                    }
                } else if (currentDate.after(lastWorkOutDate)) {
                    // New date is started
                    startActivity(new Intent(getApplicationContext(), SelectWorkoutActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), getString(R.string.str_not_finish_session),
                            Toast.LENGTH_SHORT).show();
                } else if (currentDate.before(lastWorkOutDate)) {
                    // Invalid date
                    Toast.makeText(getApplicationContext(), getString(R.string.str_invalid_date),
                            Toast.LENGTH_SHORT).show();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar, menu);
        menu.findItem(R.id.signoutChef).setVisible(true);
        return true;
    }

    private class SlidMenuDrawer implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {

        switch (position) {
            case 0:
                Intent i = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(i);
                break;
            case 1:
                Intent bodymass = new Intent(getApplicationContext(), MonthlyBMIActivity.class);
                startActivity(bodymass);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.signoutChef:
                UserAuth.clearUserAuthentication();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void serviceOpen(View v) {
        startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
    }

    /*public void cartOpen(View v) {
        startActivity(new Intent(getApplicationContext(), PurchaseServices.class));
    }*/
}
