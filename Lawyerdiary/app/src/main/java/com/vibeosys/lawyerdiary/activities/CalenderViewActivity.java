package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.vibeosys.lawyerdiary.R;

import java.util.ArrayList;
import java.util.List;

/*Copyright 2014 Raquib-ul-Alam

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/
public class CalenderViewActivity extends AppCompatActivity {
    WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Intent iSchedule = new Intent(getApplicationContext(), SheduleActivity.class);
                startActivity(iSchedule);
            }
        });

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> week = new ArrayList<WeekViewEvent>();
                week.add(new WeekViewEvent(1, "Hearing of case 2", 2016, 4, 27, 11, 00, 2016, 4, 27, 12, 00));
                week.add(new WeekViewEvent(2, "Result of case 1", 2016, 4, 27, 14, 00, 2016, 4, 27, 15, 00));
                week.add(new WeekViewEvent(3, "Meeting With Bhosale", 2016, 4, 27, 16, 00, 2016, 4, 27, 17, 00));
                week.add(new WeekViewEvent(4, "Meeting with Vinod", 2016, 4, 27, 17, 00, 2016, 4, 27, 18, 00));

                week.add(new WeekViewEvent(5, "Hearing of case 3", 2016, 4, 28, 9, 00, 2016, 4, 27, 11, 00));
                week.add(new WeekViewEvent(6, "Result of case 6", 2016, 4, 28, 12, 00, 2016, 4, 27, 15, 00));
                week.add(new WeekViewEvent(7, "Meeting With Khan", 2016, 4, 28, 16, 00, 2016, 4, 27, 17, 00));

                week.add(new WeekViewEvent(9, "Hearing of case 6", 2016, 4, 29, 10, 00, 2016, 4, 27, 12, 00));
                week.add(new WeekViewEvent(11, "Meeting With Khan", 2016, 4, 29, 13, 00, 2016, 4, 27, 14, 00));
                week.add(new WeekViewEvent(12, "Meeting with Bonde", 2016, 4, 29, 14, 00, 2016, 4, 27, 15, 00));
                return week;
            }
        });

// Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });
    }
}

