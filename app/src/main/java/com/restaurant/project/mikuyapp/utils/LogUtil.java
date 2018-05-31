package com.restaurant.project.mikuyapp.utils;

import android.util.Log;

import com.restaurant.project.mikuyapp.BuildConfig;

public final class LogUtil {
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d("nel_", message);
        }
    }
}
