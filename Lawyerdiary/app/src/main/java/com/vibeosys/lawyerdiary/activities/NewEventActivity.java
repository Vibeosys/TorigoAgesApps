package com.vibeosys.lawyerdiary.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Vibeosys software on 27-04-2016.
 */

/**
 * NewEventActivity is use to add new case event.
 * It allows user to add event details  like event date ,time and meeting location.
 */
public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = NewEventActivity.class.getSimpleName();
    private EditText mReminderName, mLocation, mNote, mDefaultColour, mStartDatePicker,
            mStartTimePicker, mEndDatePicker, mEndTimePicker, mCaseIdEditText;
    private String mReminderStr;
    private Button mSaveBtn, mCancelBtn;
    /*private TextView mStartDatePicker;*/
    private int mYear, mMonth, mDay;
    Calendar myCalendar, myCalendar2;
    private DateUtils dateUtils = new DateUtils();
    private long eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setTitle("Add Reminder");

        mLocation = (EditText) findViewById(R.id.locationTxt);
        mReminderName = (EditText) findViewById(R.id.reminderName);
        mNote = (EditText) findViewById(R.id.noteTxt);
        mDefaultColour = (EditText) findViewById(R.id.defaultColour);
        mStartDatePicker = (EditText) findViewById(R.id.dateStartMeeting);
        mSaveBtn = (Button) findViewById(R.id.AddRemiderSave);
        mCancelBtn = (Button) findViewById(R.id.AddRemiderCancel);
        mStartTimePicker = (EditText) findViewById(R.id.timeMeetingStart);
        mEndDatePicker = (EditText) findViewById(R.id.dateEndMeeting);
        mEndTimePicker = (EditText) findViewById(R.id.timeMeetingEnd);
        mCaseIdEditText = (EditText) findViewById(R.id.CaseId);
        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

        mSaveBtn.setOnClickListener(NewEventActivity.this);
        mCancelBtn.setOnClickListener(NewEventActivity.this);
        mStartDatePicker.setInputType(InputType.TYPE_NULL);
        mStartTimePicker.setInputType(InputType.TYPE_NULL);
        mEndDatePicker.setInputType(InputType.TYPE_NULL);
        mEndTimePicker.setInputType(InputType.TYPE_NULL);
        mStartDatePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                myCalendar = Calendar.getInstance();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(NewEventActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }

        });
        mEndDatePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(NewEventActivity.this, date1, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });
        mStartTimePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new TimePickerDialog(NewEventActivity.this, time,
                            myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
                }
                return false;
            }
        });
        mEndTimePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new TimePickerDialog(NewEventActivity.this, time1,
                            myCalendar2.get(Calendar.HOUR_OF_DAY), myCalendar2.get(Calendar.MINUTE), false).show();
                }
                return false;
            }
        });
    }

    private void updateLabel() {
        mStartDatePicker.setText(dateUtils.getLocalDateInReadableFormat(myCalendar.getTime()));
        mStartDatePicker.setError(null);
    }

    private void updateLabel1() {
        mEndDatePicker.setText(dateUtils.getLocalDateInReadableFormat(myCalendar2.getTime()));
        mEndDatePicker.setError(null);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateTime();
        }
    };
    TimePickerDialog.OnTimeSetListener time1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar2.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar2.set(Calendar.MINUTE, minute);
            updateTime1();
        }
    };

    private void updateTime() {
        mStartTimePicker.setText(dateUtils.getLocalTimeInReadableFormat(myCalendar.getTime()));
        mStartTimePicker.setError(null);
    }

    private void updateTime1() {
        mEndTimePicker.setText(dateUtils.getLocalTimeInReadableFormat(myCalendar2.getTime()));
        mEndTimePicker.setError(null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.AddRemiderSave:
                eventId = addEvent();
                if (eventId > 0) {
                    Toast.makeText(getApplicationContext(), getResources().
                            getString(R.string.str_new_event_added), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CalenderViewActivity.class));
                }
                break;
            case R.id.AddRemiderCancel:

                break;
            default:

        }
    }

    /**
     * This function is use to validate the field  and also add the case event details,like
     * case location,case date and case id.
     * @return after successfully insertion it returns event id.
     */
    public long addEvent() {

        String reminderStr = mReminderName.getText().toString().trim();
        String locationStr = mLocation.getText().toString().trim();
        String noteStr = mNote.getText().toString().trim();
        String startDateTime = dateUtils.getDateTimeReadFormat(myCalendar.getTime());
        String endDateTime = dateUtils.getDateTimeReadFormat(myCalendar2.getTime());
        String colour = mDefaultColour.getText().toString().trim();
        String caseId = mCaseIdEditText.getText().toString().trim();

        boolean check = true;
        View focusView = null;
        if (TextUtils.isEmpty(reminderStr)) {
            focusView = mReminderName;
            mReminderName.setError(getResources().getString(R.string.str_reminder_name));
            check = false;
        } else if (TextUtils.isEmpty(mStartDatePicker.getText().toString().trim())) {
            focusView = mStartDatePicker;
            mStartDatePicker.setError(getResources().getString(R.string.str_err_date));
            check = false;
        } else if (TextUtils.isEmpty(mStartTimePicker.getText().toString().trim())) {
            focusView = mStartTimePicker;
            mStartTimePicker.setError(getResources().getString(R.string.str_err_time));
            check = false;
        } else if (TextUtils.isEmpty(mEndDatePicker.getText().toString().trim())) {
            focusView = mEndDatePicker;
            mEndDatePicker.setError(getResources().getString(R.string.str_err_date));
            check = false;
        } else if (TextUtils.isEmpty(mEndTimePicker.getText().toString().trim())) {
            focusView = mEndTimePicker;
            mEndTimePicker.setError(getResources().getString(R.string.str_err_time));
            check = false;
        }
        if (!check) {
            focusView.requestFocus();
            return 0;
        } else {
            ContentValues reminderValues = new ContentValues();
            reminderValues.put(LawyerContract.Reminder.REMINDER_NAME, reminderStr);
            reminderValues.put(LawyerContract.Reminder.START_DATE_TIME, startDateTime);
            reminderValues.put(LawyerContract.Reminder.END_DATE_TIME, endDateTime);
            reminderValues.put(LawyerContract.Reminder.LOCATION, locationStr);
            reminderValues.put(LawyerContract.Reminder.NOTE, noteStr);
            reminderValues.put(LawyerContract.Reminder.COLOUR, colour);
            reminderValues.put(LawyerContract.Reminder.CASE_ID, caseId);
            try {
                Uri insertEvent = getContentResolver().insert(LawyerContract.Reminder.CONTENT_URI, reminderValues);
                eventId = ContentUris.parseId(insertEvent);
            } catch (SQLException e) {
                Log.e(TAG, "Event is not added " + e.toString());
            }
            return eventId;
        }
    }
}
