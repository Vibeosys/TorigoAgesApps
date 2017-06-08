package com.finaqa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finaqa.R;

public class ConsultantSecondRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_second_registration);
        setTitle("Consultant Login");
        mNextBtn = (Button) findViewById(R.id.consult_second);
        mNextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.consult_second:
                Intent intent = new Intent(getApplicationContext(), ConsultantThirdRegistration.class);
                startActivity(intent);
                break;
        }

    }
}
