package com.vibeosys.lawyerdiary.activities;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import java.util.Date;

/**
 * Created by Vibeosys software on 27-04-2016.
 */

/**
 * EventDetailsActivity is used to display event details of the case like Date, time, location and case title.
 * This activity also use DateUtils class which helps to get date,time into local time zone format.
 */
public class EventDetailsActivity extends AppCompatActivity {

    public static final String EVENT_ID = "event_id";
    private static final String TAG = EventDetailsActivity.class.getSimpleName();

    private TextView txtHeading, txtEventName, txtDate, txtTime, txtLocation, txtNote;
    private long _eventId = 0;
    private DateUtils dateUtils = new DateUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setTitle("Event Details");
        _eventId = getIntent().getExtras().getLong(EVENT_ID);

        txtHeading = (TextView) findViewById(R.id.txtEventHeading);
        txtEventName = (TextView) findViewById(R.id.txtEventName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtNote = (TextView) findViewById(R.id.txtNote);
        loadAndDisplayData();
    }

    /**
     * This function is use to fetch the data from data base like case note ,case location,
     * case id and case reminder date.
     *
     */
    private void loadAndDisplayData() {
        if (_eventId != 0) {
            Cursor eventCursor = null;
            try {
                eventCursor = getApplicationContext().getContentResolver().query(LawyerContract.Reminder.CONTENT_URI,
                        new String[]{LawyerContract.Reminder._ID, LawyerContract.Reminder.REMINDER_NAME,
                                LawyerContract.Reminder.START_DATE_TIME, LawyerContract.Reminder.END_DATE_TIME,
                                LawyerContract.Reminder.LOCATION, LawyerContract.Reminder.NOTE, LawyerContract.Reminder.COLOUR
                        }, LawyerContract.Reminder._ID + "=?", new String[]{String.valueOf(_eventId)}, null);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }

            if (eventCursor.moveToFirst()) {
                long eventId = eventCursor.getLong(eventCursor.getColumnIndex(LawyerContract.Reminder._ID));
                String name = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.REMINDER_NAME));
                String strStartTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.START_DATE_TIME));
                String strEndTime = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.END_DATE_TIME));
                Date startDate = dateUtils.getFormattedDate(strStartTime);
                Date endDate = dateUtils.getFormattedDate(strEndTime);
                String location = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.LOCATION));
                String note = eventCursor.getString(eventCursor.getColumnIndex(LawyerContract.Reminder.NOTE));
                String heading = name + " On " + dateUtils.getDateTimeFormat(startDate);
                txtEventName.setText(name);
                if (TextUtils.isEmpty(location)) {
                    txtLocation.setText(getResources().getString(R.string.str_not_defined_location));
                } else {
                    txtLocation.setText(location);
                }
                if (TextUtils.isEmpty(note)) {
                    txtNote.setText(getResources().getString(R.string.str_not_defined_note));
                } else {
                    txtNote.setText(note);
                }
                txtDate.setText(dateUtils.getLocalDateInReadableFormat(startDate));
                txtTime.setText(dateUtils.getLocalTimeInReadableFormat(startDate) + " To " + dateUtils.getLocalTimeInReadableFormat(endDate));

                txtHeading.setText(heading);
            }
        }


    }
}
