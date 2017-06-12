package com.finaqa.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.finaqa.R;
import com.finaqa.adapter.SpinnerSimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ConsultantRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button mNextButton;
    private EditText mDateOfBirth;
    private Calendar myCalendar;
    private Spinner mGender;
    private ArrayList<String> mGenderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_registration);
        setTitle("Consultant registration");
        mNextButton = (Button) findViewById(R.id.consult_first);
        mDateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        mGender = (Spinner) findViewById(R.id.genderSpinner);
        myCalendar = Calendar.getInstance();
        mGenderList = new ArrayList<>();
        mGenderList.add("Male");
        mGenderList.add("Female");
        mNextButton.setOnClickListener(this);
        mDateOfBirth.setOnClickListener(this);
        SpinnerSimpleAdapter dataAdapter = new SpinnerSimpleAdapter(ConsultantRegistration.this, mGenderList);
        mGender.setAdapter(dataAdapter);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.consult_first:
                Intent intent = new Intent(getApplicationContext(), ConsultantSecondRegistration.class);
                startActivity(intent);
                break;
            case R.id.dateOfBirth:
                new DatePickerDialog(ConsultantRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

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

        mDateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }
}
