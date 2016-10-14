package com.vibeosys.paymybill;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by akshay on 14-10-2016.
 */
public class PayMyBillApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
    }
}
