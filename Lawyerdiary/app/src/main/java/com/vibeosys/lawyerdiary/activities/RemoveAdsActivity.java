package com.vibeosys.lawyerdiary.activities;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vibeosys.lawyerdiary.MainActivity;
import com.vibeosys.lawyerdiary.R;
import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class RemoveAdsActivity extends BaseActivity implements View.OnClickListener {

    IInAppBillingService mService;
    private Button mPurchaseBtn,mCancelBtn;
    private  String mPurchasePrice;
    private UUID mTransactionId;

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
        mPurchaseBtn.setOnClickListener(RemoveAdsActivity.this);
        mCancelBtn.setOnClickListener(RemoveAdsActivity.this);

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.removeAds:
                int isBillingSupported = -1;
                try {
                    isBillingSupported = mService.isBillingSupported(6, getPackageName(), "inapp");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (isBillingSupported != 0) {
                    Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainAct);
                    finish();
                } else
                    getInAppPurchases();

                break;
            case R.id.cancelPurchase:
                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAct);
                finish();
                break;
        }
    }
    public void getInAppPurchases() {
        try {
            Bundle purchaseItems = mService.getPurchases(6, getPackageName(), "inapp", null);
            int responseCode = purchaseItems.getInt("RESPONSE_CODE");
            if (responseCode == 0) {
                ArrayList<String> items = purchaseItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                if (items.contains("com.lawyerdiary.noads")) {
                    Toast.makeText(getApplicationContext(), "Product is already purchased", Toast.LENGTH_SHORT).show();
                } else {
                    purchaseProducts();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private void purchaseProducts() {
        ArrayList<String> purchaseList = new ArrayList<String>();
        purchaseList.add("com.lawyerdiary.noads");
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", purchaseList);
        PurchaseDetails mBundle = new PurchaseDetails();
        mBundle.execute(querySkus);

    }
    private class PurchaseDetails extends AsyncTask<Bundle,Void,Bundle>
    {
        @Override
        protected Bundle doInBackground(Bundle... params) {
            Bundle skuDetails = null;
            try {
                skuDetails = mService.getSkuDetails(6,
                        getPackageName(), "inapp", params[0]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return skuDetails;
        }

        @Override
        protected void onPostExecute(Bundle bundle) {
            super.onPostExecute(bundle);
            try
            {
                int response = bundle.getInt("RESPONSE_CODE");

                if(response==0)
                {
                    ArrayList<String> responseList = bundle.getStringArrayList("DETAILS_LIST");

                    for(String thisResponse:responseList)
                    {
                        JSONObject object = new JSONObject(thisResponse);
                        String productId = object.getString("productId");
                        String price = object.getString("Pirce");
                        if(productId.equals("com.lawyerdiary.noads"))
                        {
                            mPurchasePrice = price;
                            mTransactionId = UUID.randomUUID();
                            Bundle buyIntentBundle = mService.getBuyIntent(6,getPackageName(),
                                        productId,"inapp",mTransactionId.toString());
                            PendingIntent pendingIntent= buyIntentBundle.getParcelable("BUY_INTENT");
                            startIntentSenderForResult(pendingIntent.getIntentSender(),1001,
                                        null, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                        }

                    }
                }

            }catch (IntentSender.SendIntentException e)
            {
                Log.d("INAPP",""+e.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1001)
        {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

                if (resultCode == 0) {
                    try {
                        JSONObject jo = new JSONObject(purchaseData);
                        String sku = jo.getString("productId");
                        String payload = jo.getString("developerPayload");
                        if (sku.equals("com.lawyerdiary.noads")) {
                            Toast.makeText(getApplicationContext(), "You have purchase the ads free " +
                                    "application. Thank you!", Toast.LENGTH_LONG).show();
                            
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Failed to parse purchase data.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConnection);
        }
    }
}
