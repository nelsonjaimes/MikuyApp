package com.restaurant.project.mikuyapp.domain.api;

import com.restaurant.project.mikuyapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDirectionsManager {
    private final Retrofit retrofit;
    private static final String URL_BASE = "https://maps.googleapis.com/maps/api/";
    public static final String KEY = BuildConfig.KEY_API_DIRECTION;
    public static final String OK = "OK";
    private static ApiDirectionsManager INSTANCE;

    private ApiDirectionsManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(8, TimeUnit.SECONDS);
        okBuilder.connectTimeout(8, TimeUnit.SECONDS);
        okBuilder.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okBuilder.build())
                .build();
    }

    private ApiDirectionsInterface get() {
        return retrofit.create(ApiDirectionsInterface.class);
    }

    public static ApiDirectionsInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiDirectionsManager();
        }
        return INSTANCE.get();
    }
}
