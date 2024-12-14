package com.abreuretto.AppsWithAds;

import android.app.Application;
import android.util.Log;

public class AppsApplication extends Application {
    private static final String TAG = "ShowApplicationsWithAds";
    private static AppsApplication instance = null;

    public static AppsApplication getInstance() {
        return instance; // Singleton
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d(TAG, "CarrosApplication.onCreate()");
        // Salva a instância para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //Log.d(TAG, "CarrosApplication.onTerminate()");
    }
}
