package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.vibeosys.paymybill.MainActivity;
import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.util.UserAuth;

public class SplashScreen extends BaseActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                boolean test = UserAuth.isUserLoggedIn();
                if (!test) {
                    Intent mainRun = new Intent(SplashScreen.this, CarouselActivity.class);
                    startActivity(mainRun);
                    // close this activity
                    finish();
                }
                else
                {
                    Intent mainRun = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainRun);

                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
