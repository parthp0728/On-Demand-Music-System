package com.greatlove.musicplay.activity;

import android.content.Context;

import androidx.multidex.MultiDexApplication;


/**
 * Created by vishalvasoya on 7/23/2018.
 */

public class Helpservices extends MultiDexApplication {
    public static Helpservices application;
    private static final String TAG = Helpservices.class.getSimpleName();

    public Helpservices() {
        application = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Helpservices getApp() {
        if (application == null) {
            application = new Helpservices();
        }
        return application;
    }

    public static Context getAppContext() {
        if (application == null) {
            application = new Helpservices();
        }
        return application;
    }

}
