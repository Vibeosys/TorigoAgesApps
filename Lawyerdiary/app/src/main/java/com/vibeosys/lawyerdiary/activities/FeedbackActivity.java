package com.vibeosys.lawyerdiary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private EditText mClientName,mClientFeedback;
    private Spinner mCountryName;
    private Button mSubmitBtn,mCancleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(getResources().getString(R.string.str_feedback_title));
        mClientName = (EditText) findViewById(R.id.clientName);
        mClientFeedback =(EditText) findViewById(R.id.feedback);
        mSubmitBtn = (Button) findViewById(R.id.btnSubmit);
        mCountryName = (Spinner) findViewById(R.id.countryName);
        mCancleBtn = (Button) findViewById(R.id.btnCancel);

        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        countries.add(0,"--Please select country name--");
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountryName.setAdapter(adapter);
        mSubmitBtn.setOnClickListener(FeedbackActivity.this);
        mCancleBtn.setOnClickListener(FeedbackActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnSubmit:
                callToValidation();
                break;
            case R.id.btnCancel:
                Intent mainActivity =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
                break;
        }
    }
    public boolean callToValidation()
    {
        String userName = mClientName.getText().toString().trim();
        String userFeedBack = mClientFeedback.getText().toString().trim();
        if(userName.toString().length()==0)
        {
            mClientName.requestFocus();
            mClientName.setError(getResources().getString(R.string.str_user_name_val));
            return false;

        }
        else if(userFeedBack.toString().length()==0)
        {
            mClientFeedback.requestFocus();
            mClientFeedback.setError(getResources().getString(R.string.str_user_feedback_val));
            return false;
        }

        return true;
    }

}
