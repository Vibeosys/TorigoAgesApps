package com.vibeosys.lawyerdiary.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;
import com.android.vending.billing.IInAppBillingService;
public class RemoveAdsActivity extends BaseActivity implements View.OnClickListener {

    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    private Button mPurchaseBtn,mCancelBtn;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService=IInAppBillingService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_ads);
        setTitle(getResources().getString(R.string.str_remove_ads));
        mPurchaseBtn =(Button) findViewById(R.id.removeAds);
        mCancelBtn =(Button) findViewById(R.id.cancelPurchase);
        /*Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);*/
        mPurchaseBtn.setOnClickListener(RemoveAdsActivity.this);
        mCancelBtn.setOnClickListener(RemoveAdsActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.removeAds:
                Toast toast =Toast.makeText(getApplicationContext(),"Purchase button is clicked",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                break;
            case R.id.cancelPurchase:
                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAct);
                finish();
                break;
        }
    }
}
