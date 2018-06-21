package com.restaurant.project.mikuyapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.restaurant.project.mikuyapp.BuildConfig;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import static com.restaurant.project.mikuyapp.scan.NetInfo.DEFAULT_INTERFACE;

final public class MikuyPreference {
    private static final String NAME_PREFERENCE = "mikuyPreference";
    private static final String KEY_USER_SESSION = "userSession";
    private static final String KEY_DOWNLOAD_PLATES = "downloadPlatesList";
    /*WIfi*/
    private static final String KEY_NAME_INTERFACE = "nameInterface";
    private static final String KEY_IP_ADDRESS_SERVER = "ipAdressServer";
    private static final String DEFAULT_IP_ADDRESS_SERVER = "000.000.000.000";

    public static void saveUserSession(Context context, SignInResponseEntity signInResponseEntity) {
        context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                edit().putString(KEY_USER_SESSION, new Gson().toJson(signInResponseEntity)).apply();
    }

    public static void saveStateDownloadPlatesList(Context context, boolean state) {
        context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                edit().putBoolean(KEY_DOWNLOAD_PLATES, state).apply();
    }

    public static boolean getStateDownloadPlatesList(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                getBoolean(KEY_DOWNLOAD_PLATES, false);
    }

    public static SignInResponseEntity getUserSession(Context context) {
        SignInResponseEntity signInResponseEntity = null;
        try {
            SharedPreferences preferences = context.
                    getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
            signInResponseEntity = new Gson().fromJson(preferences.getString(KEY_USER_SESSION,
                    ""), SignInResponseEntity.class);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return signInResponseEntity;
    }

    public static void deleteAll(Context context) {
        context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                edit().remove(KEY_USER_SESSION).remove(KEY_DOWNLOAD_PLATES).apply();
    }

    public static void saveInterfaceName(Context context, String name) {
        context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                edit().putString(KEY_NAME_INTERFACE, name).apply();
    }

    public static String getNameInterface(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                getString(KEY_NAME_INTERFACE, DEFAULT_INTERFACE);
    }

    public static void saveIpAddressServer(Context context, String network) {
        context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                edit().putString(KEY_IP_ADDRESS_SERVER, network).apply();
    }

    public static String getIpAddressServer(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE).
                getString(KEY_IP_ADDRESS_SERVER, DEFAULT_IP_ADDRESS_SERVER);
    }

    public static String getUrlBaseServer(Context context) {
        //LogUtil.d("http://".concat(getIpAddressServer(context)).concat("/api.mikuy.com/v1/"));
       // return "http://".concat(getIpAddressServer(context)).concat("/api.mikuy.com/v1/");
        return BuildConfig.SERVER_DOMAIN;
    }
}
