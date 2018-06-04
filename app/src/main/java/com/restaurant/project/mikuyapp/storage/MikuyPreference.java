package com.restaurant.project.mikuyapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.restaurant.project.mikuyapp.MikuyApplication;
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
    private static final String DEFAULT_IP_ADDRESS_SERVER = "0.0.0.0";

    public static void saveUserSession(SignInResponseEntity signInResponseEntity) {
        SharedPreferences preferences = getSharePreference();
        preferences.edit().putString(KEY_USER_SESSION,
                new Gson().toJson(signInResponseEntity)).apply();
    }

    public static void saveStateDownloadPlatesList(boolean state) {
        getSharePreference().edit().putBoolean(KEY_DOWNLOAD_PLATES, state).apply();
    }

    public static boolean getStateDownloadPlatesList() {
        return getSharePreference().getBoolean(KEY_DOWNLOAD_PLATES, false);
    }

    public static SignInResponseEntity getUserSession() {
        SignInResponseEntity signInResponseEntity = null;
        try {
            SharedPreferences preferences = getSharePreference();
            signInResponseEntity = new Gson().fromJson(preferences.getString(KEY_USER_SESSION,
                    ""), SignInResponseEntity.class);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return signInResponseEntity;
    }

    public static void deleteAll() {
        getSharePreference().edit().remove(KEY_USER_SESSION).apply();
        getSharePreference().edit().remove(KEY_DOWNLOAD_PLATES).apply();
    }

    public static void saveInterfaceName(String name) {
        SharedPreferences preferences = getSharePreference();
        preferences.edit().putString(KEY_NAME_INTERFACE, name).apply();
    }

    public static String getNameInterface() {
        return getSharePreference().getString(KEY_NAME_INTERFACE, DEFAULT_INTERFACE);
    }

    private static SharedPreferences getSharePreference() {
        return MikuyApplication.contextApp.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void saveIpAddressServer(String network) {
        getSharePreference().edit().putString(KEY_IP_ADDRESS_SERVER, network).apply();
    }

    public static String getIpAddressServer() {
        return getSharePreference().getString(KEY_IP_ADDRESS_SERVER, DEFAULT_IP_ADDRESS_SERVER);
    }

    public static String getUrlBaseServer() {
        LogUtil.d("http://".concat(getIpAddressServer()).concat("/api.mikuy.com/v1/"));
        return "http://".concat(getIpAddressServer()).concat("/api.mikuy.com/v1/");
    }

}
