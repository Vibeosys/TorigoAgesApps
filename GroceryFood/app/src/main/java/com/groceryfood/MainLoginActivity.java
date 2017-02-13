package com.groceryfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layQuick, layLogin;
    TextView txtVendorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_login);
        layQuick = (LinearLayout) findViewById(R.id.lay_quick);
        layLogin = (LinearLayout) findViewById(R.id.lay_login);
        txtVendorLogin = (TextView) findViewById(R.id.txt_vender_login);

        layQuick.setOnClickListener(this);
        layLogin.setOnClickListener(this);
        txtVendorLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lay_quick:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.lay_login:
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                finish();
                break;
            case R.id.txt_vender_login:
                startActivity(new Intent(getApplicationContext(), PartnerLoginActivity.class));
                finish();
                break;
        }
    }
}
