package com.restaurant.project.mikuyapp;

import android.app.Application;
import android.content.Context;

public class MikuyApplication extends Application {
    public static Context contextApp;

    @Override
    public void onCreate() {
        super.onCreate();
        contextApp = getApplicationContext();
    }
}
