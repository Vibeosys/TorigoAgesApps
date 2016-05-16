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

import com.vibeosys.fitnessapp.R;

import java.util.ArrayList;

/**
 * Created by shrinivas on 21-04-2016.
 */
public class DashboardActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> navDrawerItems;
    ImageView worksheet;
    ImageView diat;
    ImageView InstructionNotes;
    ImageView messageChat;
    ImageView purchase;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.dash_board);

        setContentView(R.layout.drawer_layout_main);
        // setContentView(R.layout.new_round_dashboard);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_drawer);

        worksheet = (ImageView) findViewById(R.id.circular_image_view1);
        diat = (ImageView) findViewById(R.id.circular_image_view2);
        InstructionNotes = (ImageView) findViewById(R.id.circular_image_view3);
        messageChat = (ImageView) findViewById(R.id.circular_image_view4);
        purchase = (ImageView) findViewById(R.id.circular_image_view5);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Dash board");

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
                Intent worksheet = new Intent(getApplicationContext(), WorkSheetActivity.class);
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
                Intent instructions = new Intent(getApplicationContext(), InstructionsActivity.class);
                startActivity(instructions);
            }
        });
        messageChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messagech = new Intent(getApplicationContext(), MessageChatActivity.class);
                startActivity(messagech);
            }
        });
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
                finish();
                break;
            case 1:
                Intent bodymass = new Intent(getApplicationContext(), BodyMassIndexActivity.class);
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
        return super.onOptionsItemSelected(item);
    }

    public void serviceOpen(View v) {
        startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
    }

    public void cartOpen(View v) {
        startActivity(new Intent(getApplicationContext(), PurchaseServices.class));
    }
}
