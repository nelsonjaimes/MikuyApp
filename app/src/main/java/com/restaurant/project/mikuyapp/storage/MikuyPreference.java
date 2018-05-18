package com.restaurant.project.mikuyapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.restaurant.project.mikuyapp.MikuyApplication;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;

final public class MikuyPreference {
    private static final String NAME_PREFERENCE = "mikuyPreference";
    private static final String KEY_USER_SESSION = "userSession";
    private static final String KEY_DOWNLOAD_PLATES = "downloadPlatesList";

    public static void saveUserSession(SignInResponseEntity signInResponseEntity) {
        SharedPreferences preferences = MikuyApplication.contextApp.
                getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
        preferences.edit().putString(KEY_USER_SESSION,
                new Gson().toJson(signInResponseEntity)).apply();
    }

    public static void saveStateDownloadPlatesList(boolean state) {
        SharedPreferences preferences = MikuyApplication.contextApp.
                getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(KEY_DOWNLOAD_PLATES, state).apply();
    }

    public static boolean getStateDownloadPlatesList() {
        SharedPreferences preferences = MikuyApplication.contextApp.
                getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_DOWNLOAD_PLATES, false);
    }

    public static SignInResponseEntity getUserSession() {
        SignInResponseEntity signInResponseEntity = null;
        try {
            SharedPreferences preferences = MikuyApplication.contextApp.
                    getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
            signInResponseEntity = new Gson().fromJson(preferences.getString(KEY_USER_SESSION,
                    ""), SignInResponseEntity.class);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return signInResponseEntity;
    }

    public static void deleteAll() {
        SharedPreferences preferences = MikuyApplication.contextApp.
                getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

}
