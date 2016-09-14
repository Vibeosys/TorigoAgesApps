package com.vibeosys.lawyerdiary.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.SyncAdapterType;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mReminderName,mLocation, mNote, mDefaultColour,mStartDatePicker,mStartTimePicker
            ,mEndDatePicker,mEndTimePicker;
    private String mReminderStr;
    private Button mSaveBtn, mCancelBtn;
    /*private TextView mStartDatePicker;*/
    private int mYear, mMonth, mDay;
    Calendar myCalendar,myCalendar2;

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
        myCalendar = Calendar.getInstance();

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
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
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
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(NewEventActivity.this, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                return false;
            }
        });
        mStartTimePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(NewEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if(selectedHour > 12)
                            {   int timeIn12 = selectedHour-12;
                                mStartTimePicker.setText( timeIn12 + ":" + selectedMinute+" PM");
                                mStartTimePicker.setError(null);
                            }
                              else
                            {
                                mStartTimePicker.setText( selectedHour + ":" + selectedMinute+" AM");
                                mStartTimePicker.setError(null);
                            }
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                return false;
            }
        });
        mEndTimePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;

                    mTimePicker = new TimePickerDialog(NewEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Calendar mcurrentTime = Calendar.getInstance();
                            timePicker.setIs24HourView(false);

                            mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                            mcurrentTime.set(Calendar.MINUTE,selectedMinute);
                            if(selectedHour > 12)
                            {   int timeIn12 = selectedHour-12;
                                mEndTimePicker.setText( timeIn12 + ":" + selectedMinute+" PM");
                                mEndTimePicker.setError(null);
                            }
                            else
                            {
                                mEndTimePicker.setText( selectedHour + ":" + selectedMinute+" AM");
                                mEndTimePicker.setError(null);
                            }
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                return false;
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mStartDatePicker.setText(sdf.format(myCalendar.getTime()));
        mStartDatePicker.setError(null);
    }
    private void updateLabel1() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEndDatePicker.setText(sdf.format(myCalendar.getTime()));
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
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.AddRemiderSave:
                boolean check = callToValidation();
                if (check) {
                    Toast toast = Toast.makeText(getApplicationContext(), "All Validations are done",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    String ReminderStr = mReminderName.getText().toString().trim();
                    String LocationStr = mLocation.getText().toString().trim();
                    String NoteStr = mNote.getText().toString().trim();
                    String StartDateTime = mStartDatePicker.getText().toString().trim()
                            +"-"+mStartTimePicker.getText().toString().trim();
                    String EndDateTime = mEndDatePicker.getText().toString().trim()+
                            "-"+mEndTimePicker.getText().toString().trim();
                    String Colour = mDefaultColour.getText().toString().trim();
                    String CaseId = "1";
                    onClickAddEvent(ReminderStr,LocationStr,NoteStr,StartDateTime,EndDateTime,Colour,CaseId);
                }
                break;
            case R.id.AddRemiderCancel:
                onClickRetriverData();
                break;
            default:

        }
    }

    public boolean callToValidation() {
        if (TextUtils.isEmpty(mReminderName.getText().toString().trim())) {
            mReminderName.requestFocus();
            mReminderName.setError(getResources().getString(R.string.str_reminder_name));
            return false;
        }
        if (TextUtils.isEmpty(mLocation.getText().toString().trim())) {
            mLocation.requestFocus();
            mLocation.setError(getResources().getString(R.string.str_location));
            return false;
        }
        if (TextUtils.isEmpty(mNote.getText().toString().trim())) {
            mNote.requestFocus();
            mNote.setError(getResources().getString(R.string.str_note));
            return false;
        }
        if (TextUtils.isEmpty(mStartDatePicker.getText().toString().trim())) {
            mStartDatePicker.requestFocus();
            mStartDatePicker.setError("Please select date");
            return false;
        }
        if (TextUtils.isEmpty(mStartTimePicker.getText().toString().trim())) {
            mStartTimePicker.requestFocus();
            mStartTimePicker.setError("Please select time");
            return false;
        }
        if (TextUtils.isEmpty(mEndDatePicker.getText().toString().trim())) {
            mEndDatePicker.requestFocus();
            mEndDatePicker.setError("Please select date");
            return false;
        }
        if (TextUtils.isEmpty(mEndTimePicker.getText().toString().trim())) {
            mEndTimePicker.requestFocus();
            mEndTimePicker.setError("Please select time");
            return false;
        }
        if(!TextUtils.isEmpty(mStartDatePicker.getText().toString().trim()))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date,date2;
            try {
                date = simpleDateFormat.parse(mEndDatePicker.getText().toString().trim());
                date2 = simpleDateFormat.parse(mStartDatePicker.getText().toString().trim());
                    if(date2.after(date))
                    {
                        mEndDatePicker.requestFocus();
                        mEndDatePicker.setError("Please select proper");
                        return false;
                    }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void onClickAddEvent(String ReminderStr,String LocationStr,String NoteStr,
                                String StartDateTime,String EndDateTime,String Colour,String CaseId)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LawyerContract.Reminder.REMINDER_NAME, ReminderStr);
        contentValues.put(LawyerContract.Reminder.START_DATE_TIME, StartDateTime);
        contentValues.put(LawyerContract.Reminder.END_DATE_TIME, EndDateTime);
        contentValues.put(LawyerContract.Reminder.LOCATION, LocationStr);
        contentValues.put(LawyerContract.Reminder.NOTE, NoteStr);
        contentValues.put(LawyerContract.Reminder.COLOUR, Colour);
        contentValues.put(LawyerContract.Reminder.CASE_ID, CaseId);

        String finalUr = LawyerContract.BASE_CONTENT_URI +"/"+LawyerContract.PATH_REMINDER;
        Uri uriFirst = Uri.parse(finalUr);
        Uri uri = getContentResolver().insert(uriFirst, contentValues);
    }

    public void onClickRetriverData() {

        String URL = LawyerContract.BASE_CONTENT_URI + "/"+LawyerContract.PATH_REMINDER;
        Uri uriFetch = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(uriFetch, null, null, null, "name");
        if (cursor.moveToFirst()) {
            do {

                String Name = cursor.getString(cursor.getColumnIndex("name"));
                String startDate = cursor.getString(cursor.getColumnIndex("start_date_time"));
                String endDate = cursor.getString(cursor.getColumnIndex("end_date_time"));
                String Colour = cursor.getString(cursor.getColumnIndex("location"));
            } while (cursor.moveToNext());
        }
    }
}
