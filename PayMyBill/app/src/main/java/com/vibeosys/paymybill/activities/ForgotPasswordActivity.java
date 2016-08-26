package com.vibeosys.paymybill.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.util.GMailSender;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button mForgotPassword;
    private EditText mEmailid;
    private int SEND_EMAIL_PERMISSION_CODE;
    GMailSender sender;
    String mUserPwd,template,messageTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mEmailid = (EditText)findViewById(R.id.emailIdEditText);
        mForgotPassword =(Button) findViewById(R.id.forgotPassword);
        setTitle(R.string.forgotpass_title);
        mForgotPassword.setOnClickListener(this);
        //Enter emailid and Pwd
        sender = new GMailSender("", "");
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);*/
        template = "You'r Password is ";

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.forgotPassword:
               boolean returnVal= callToValidation();
                if(returnVal == true)
                {
                    //requestGrantPermission();
                    mUserPwd = mDbRepository.getPassword(mEmailid.getText().toString().trim());
                    if(!TextUtils.isEmpty(mUserPwd))
                    {
                        callTSendEmail();
                    }
                    else {
                        Toast toast =Toast.makeText(getApplicationContext(),"Cannot perform Operation",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }
                break;
        }

    }

    private void callTSendEmail() {
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"+"shrinivas@vibeosys.com"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,"shrinivas@vibeosys.com");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Forgot password ,Pay my bills");
        intent.putExtra(Intent.EXTRA_TEXT,"Your password is dummy data ");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            startActivity(Intent.createChooser(intent,"send email using"));
            finish();
        }catch (Exception e)
        {
            Log.d(TAG,"exception while sending email");
        }*/
        try {
            new MyAsyncClass().execute();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean callToValidation() {
        if(mEmailid.getText().toString().length()==0)
        {
            mEmailid.requestFocus();
            mEmailid.setError("Please enter email id");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mEmailid.getText().toString().trim()).matches())
        {
            mEmailid.requestFocus();
            mEmailid.setError("email Id is not in correct formate");
            return false;
        }
        return true;
    }
    private void requestGrantPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE},
                SEND_EMAIL_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SEND_EMAIL_PERMISSION_CODE && grantResults[0] == 0) {
            callTSendEmail();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ForgotPasswordActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                messageTxt= template + mUserPwd;
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("Forgot password", messageTxt, "", "");
//sender emailid and receviver emailid

            }

            catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_LONG).show();
        }
    }
}
