package com.vibeosys.fitnessapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.database.FitnessContract;

/**
 * Created by shrinivas on 25-04-2016.
 */
public class DietPlanActivity extends AppCompatActivity {
    private Context context;
    private static final String TAG = DietPlanActivity.class.getSimpleName();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.diet_plan);
        setContentView(R.layout.diet_plan_web_view);
        getSupportActionBar().setTitle("Diet plan");
        webView = (WebView) findViewById(R.id.webView_diet);
        // webView.loadUrl("file:///android_res/raw/normal.html");
        context = DietPlanActivity.this;
        String args[] = {"22", "22"};
        try {
            Cursor cursor = getContentResolver().query(FitnessContract.UserDiet.CONTENT_URI, null,
                    "ud_bmi_max >= ? AND ud_bmi_min <= ?", args, null);
            if (cursor == null) {
                Log.e(TAG, cursor.toString());
            } else if (cursor.getCount() < 1) {
                Log.e(TAG, cursor.toString());
            }
            if (cursor.getCount() >= 1) {
                if (cursor.moveToFirst()) {
                    String userEmailId = cursor.getString(cursor.getColumnIndex(FitnessContract.UserDiet.USER_DIET));
                    String url = "file:///android_res/raw/" + userEmailId;

                    webView.loadUrl(url);

                }

            }
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());

        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
    }
}
