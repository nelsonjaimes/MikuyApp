package com.restaurant.project.mikuyapp.domain.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDirectionsManager {
    private Retrofit retrofit;
    private final String URL_BASE = "https://maps.googleapis.com/maps/api/";
    public static final String KEY = "AIzaSyDCaUIFxpS9oqI12NrYDsv5BUKqefsvytw";
    public static final String OK = "OK";
    private static ApiDirectionsManager INSTANCE;

    private ApiDirectionsManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private ApiDirections get() {
        return retrofit.create(ApiDirections.class);
    }

    public static ApiDirections getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiDirectionsManager();
        }
        return INSTANCE.get();
    }
}
