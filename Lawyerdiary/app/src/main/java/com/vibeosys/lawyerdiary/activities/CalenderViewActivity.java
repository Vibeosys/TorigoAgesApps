package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.RectF;
import android.net.Uri;
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
import com.vibeosys.lawyerdiary.data.Client;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private DateUtils dateUtils = new DateUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        setTitle("Calendar");

        //retrieveData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewEvent = new Intent(getApplicationContext(), NewEventActivity.class);
                startActivity(iNewEvent);
                finish();
            }
        });
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Intent iSchedule = new Intent(getApplicationContext(), EventDetailsActivity.class);
                iSchedule.putExtra(EventDetailsActivity.EVENT_ID, event.getId());
                startActivity(iSchedule);
            }
        });

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> week = retrieveData(newYear, newMonth);
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

    private List<WeekViewEvent> retrieveData(int year, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();
        Cursor eventCursor = getApplicationContext().getContentResolver().query(LawyerContract.Reminder.CONTENT_URI,
                new String[]{LawyerContract.Reminder._ID, LawyerContract.Reminder.REMINDER_NAME,
                        LawyerContract.Reminder.START_DATE_TIME, LawyerContract.Reminder.END_DATE_TIME,
                        LawyerContract.Reminder.LOCATION, LawyerContract.Reminder.NOTE, LawyerContract.Reminder.COLOUR
                }, null, null, null);

        if (eventCursor.getCount() > 0) {
            eventCursor.moveToFirst();
            WeekViewEvent event = new WeekViewEvent();
            do {
                long eventId = eventCursor.getLong(eventCursor.getColumnIndex(LawyerContract.Reminder._ID));
                String name = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.REMINDER_NAME));
                String strStartTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.START_DATE_TIME));
                String strEndTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.END_DATE_TIME));
                Date startDate = dateUtils.getFormattedDate(strStartTime);
                Date endDate = dateUtils.getFormattedDate(strEndTime);
                Calendar startCalender = Calendar.getInstance();
                startCalender.setTime(startDate);
                Calendar endCalender = (Calendar) startCalender.clone();
                endCalender.setTime(endDate);
                event = new WeekViewEvent(eventId, name + getEventTitle(startCalender), startCalender, endCalender);
                event.setColor(getResources().getColor(R.color.event_color_01));
                if (startCalender.get(Calendar.MONTH) == newMonth && startCalender.get(Calendar.YEAR) == year)
                    events.add(event);
            }
            while (eventCursor.moveToNext());
        }
        return events;
    }

    /*private List<WeekViewEvent> getEvents(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 10);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 11);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth - 1);

        WeekViewEvent event = new WeekViewEvent(1, "Hearing of case 2 " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 13);
        startTime.set(Calendar.MINUTE, 00);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 14);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth - 1);

        event = new WeekViewEvent(2, "Meeting With Bhosale " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 16);
        endTime.set(Calendar.MINUTE, 30);
        event = new WeekViewEvent(10, "Meeting with Vinod " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);


        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 9);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 12);
        endTime.set(Calendar.MINUTE, 30);
        event = new WeekViewEvent(3, "Hearing of Case 3 " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 13);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 14);
        endTime.set(Calendar.MINUTE, 30);
        event = new WeekViewEvent(3, "Meeting With Khan " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 11);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 2);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 12);
        endTime.set(Calendar.MINUTE, 30);
        event = new WeekViewEvent(3, "Hearing of Case 2 " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 13);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 2);

        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 15);
        endTime.set(Calendar.MINUTE, 00);
        event = new WeekViewEvent(3, "Meeting With Bonde " + getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);


        return events;
    }

    */

    protected String getEventTitle(Calendar time) {
        return String.format(" On %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

}

