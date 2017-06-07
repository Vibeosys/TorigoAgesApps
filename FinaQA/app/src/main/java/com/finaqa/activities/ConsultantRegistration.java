package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.finaqa.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultantRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_registration);
        mNextButton = (Button) findViewById(R.id.consult_first);

        mNextButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.consult_first:
                Intent intent = new Intent(getApplicationContext(), ConsultantSecondRegistration.class);
                startActivity(intent);
                break;
        }

    }
}
