package com.finaqa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.finaqa.R;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback");
    }
}