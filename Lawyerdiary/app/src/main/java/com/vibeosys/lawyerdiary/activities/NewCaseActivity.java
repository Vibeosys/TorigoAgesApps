package com.vibeosys.lawyerdiary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vibeosys.lawyerdiary.R;

public class NewCaseActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        setTitle("Add Case");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestitial_banner_home_footer));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1C22DEC8AEF4249E83143364E2E5AC32").build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // super.onAdLoaded();
                showIntestititalCase();
            }
        });
    }
    public void showIntestititalCase()
    {
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }
}
