package com.vibeosys.lawyerdiary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vibeosys.lawyerdiary.R;

/**
 * Created by shrinivas on 27-04-2016.
 */
public class AddClientActivity extends AppCompatActivity{
    Spinner case_stage;
    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
      //  setContentView(R.layout.add_client_custom_edit);
        getSupportActionBar().setTitle("Add Client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arraySpinner =  new String[]{"Filing Date","Open","Writting statment/Say","Evidence"};
        case_stage  = (Spinner)findViewById(R.id.spinner_case_stage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        case_stage.setAdapter(adapter);


    }
}
