package com.finaqa.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.finaqa.R;
import com.finaqa.adapter.SpinnerSimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText mAge;
    private Calendar myCalendar;
    private Spinner mGender;
    private ArrayList<String> mGenderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        setTitle("Customer registration");
        mAge = (EditText) findViewById(R.id.age);
        mGender = (Spinner) findViewById(R.id.genderSpinner);
        myCalendar = Calendar.getInstance();
        mGenderList = new ArrayList<>();
        mGenderList.add("Male");
        mGenderList.add("Female");

        SpinnerSimpleAdapter dataAdapter = new SpinnerSimpleAdapter(RegisterUserActivity.this, mGenderList);
        mGender.setAdapter(dataAdapter);
        mAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterUserActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


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

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mAge.setText(sdf.format(myCalendar.getTime()));
    }
}
