package com.vibeosys.fitnessapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.utils.GMailSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mClientName, mClientFeedback;
    private Spinner mCountryName;
    private Button mSubmitBtn, mCancleBtn;
    GMailSender sender;
    private String mSenderEmail, mSenderPassword, mCustomerFeedBack, TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TAG = getClass().getName();
        setTitle(getResources().getString(R.string.str_feedback_title));
        mClientName = (EditText) findViewById(R.id.clientName);
        mClientFeedback = (EditText) findViewById(R.id.feedback);
        mSubmitBtn = (Button) findViewById(R.id.btnSubmit);
        mCountryName = (Spinner) findViewById(R.id.countryName);
        mCancleBtn = (Button) findViewById(R.id.btnCancel);
        mSenderEmail = "vibeosys@gmail.com";
        mSenderPassword = "dummyPassword";
        sender = new GMailSender(mSenderEmail, mSenderPassword);

        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        countries.add(0, "--Please select country name--");
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountryName.setAdapter(adapter);
        mSubmitBtn.setOnClickListener(FeedbackActivity.this);
        mCancleBtn.setOnClickListener(FeedbackActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                boolean returnVal = callToValidation();
                if (returnVal == true) {
                    //requestGrantPermission();
                    callToSendEmail();
                }
                break;
            case R.id.btnCancel:
                Intent mainActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(mainActivity);
                finish();
                break;
        }
    }

    public boolean callToValidation() {
        String userName = mClientName.getText().toString().trim();
        String userFeedBack = mClientFeedback.getText().toString().trim();
        if (userName.toString().length() == 0) {
            mClientName.requestFocus();
            mClientName.setError(getResources().getString(R.string.str_user_name_val));
            return false;

        } else if (userFeedBack.toString().length() == 0) {
            mClientFeedback.requestFocus();
            mClientFeedback.setError(getResources().getString(R.string.str_user_feedback_val));
            return false;
        } else if (mCountryName.getSelectedItemPosition() == 0) {
            createAlertDialog("FitnessApp", getResources().getString(R.string.str_select_country));
            return false;
        }

        return true;
    }

    public void callToSendEmail() {
        try {
            // mReceiverEmail= mEmailid.getText().toString().trim();


            mCustomerFeedBack = "Customer Name:" + "\t" + mClientName.getText().toString().trim() + "\n"
                    + "Country Name:" + "\t" + mCountryName.getSelectedItem().toString() + "\n" + "Feedback:" + "\t" + mClientFeedback.getText().toString().trim();
            String test = "info@vibeosys.com";
            new MyAsyncClass().execute(test);

        } catch (Exception ex) {
            Log.d(TAG, ex.toString());

        }
    }

    protected void createAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // whatever...
                    }
                }).create().show();
    }


    class MyAsyncClass extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;
        String customerName = mClientName.getText().toString().trim();
        String customerCountry = mCountryName.getSelectedItem().toString();
        String customerFeedBack = mClientFeedback.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FeedbackActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... mApi) {
            try {


                String htmlCode = "<html>\n" +
                        "<head>\n" +
                        "<style> \n" +
                        "    @media only screen and (max-width:620px;){\n" +
                        "        .content{\n" +
                        "            height: 60px;\n" +
                        "        }\n" +
                        "    }\n" +
                        "    @media only screen and (min-width:620px){\n" +
                        "        .content{\n" +
                        "            height: 40px;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table class=\"table\" style=\"border: 2px solid #b9b6b6;padding: 0px 0px 10px 0px;text-align: left;\">\n" +
                        "  <thead style=\"background: #4DB6AC;\">\n" +
                        "    <tr>\n" +
                        "      <th colspan=\"3\" style=\"padding: 10px 10px 10px 10px;color:#fff;\">\n" +
                        "          <div> Fitness App Feedback</div>\n" +
                        "         </th>\n" +
                        "    </tr>\n" +
                        "     \n" +
                        "  </thead>\n" +
                        "  <tbody>\n" +
                        "    <tr>\n" +
                        "     \n" +
                        "      <td style=\"padding: 7px 10px 4px 10px;\">\n" +
                        "          <div>\n" +
                        "          <div style=\"width:70px;display:inline-block\">\n" +
                        "              Customer</div>\n" +
                        "              <div style=\"width:15px;display:inline-block;\">:</div>\n" +
                        "              <div style=\"display:inline-block;\">" + "" + customerName + "</div>\n" +
                        "          </div> </td>\n" +
                        "     \n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td style=\"padding: 0px 10px 4px 10px;\">\n" +
                        "     <div>\n" +
                        "          <div style=\"width:70px;display:inline-block\">\n" +
                        "              Country</div>\n" +
                        "              <div style=\"width:15px;display:inline-block;\">:</div>\n" +
                        "              <div style=\"display:inline-block;\">" + "" + customerCountry + "</div>\n" +
                        "          </div>\n" +
                        "      </td>\n" +
                        "\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td colspan=\"3\" style=\"padding: 7px 10px 4px 10px;background: #f5f5f5;\">\n" +
                        "    <div > \n" +
                        "        <div>Message</div>\n" +
                        "        <div><hr style=\"margin-top: 4px;border: 1px solid #efeeee;\"></div>\n" +
                        "        <div class=\"content\">" + "" + customerFeedBack + "</div>\n" +
                        "          </div>\n" +
                        "        </td>\n" +
                        "\n" +
                        "    </tr>\n" +
                        "      \n" +
                        "  </tbody>\n" +
                        "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";


                //  messageTxt= template + mUserPwd;
                String email = mApi[0];

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("Fitness App feedback", htmlCode, mSenderEmail, email);
//sender emailid and receviver emailid

            } catch (Exception ex) {
                Log.d("feedback", ex.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast toast = Toast.makeText(getApplicationContext(), "Thank you for your feedback", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
    }

}
