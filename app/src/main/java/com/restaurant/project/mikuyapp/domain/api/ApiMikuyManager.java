package com.restaurant.project.mikuyapp.domain.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMikuyManager {
    private Retrofit retrofit;
    private static ApiMikuyManager INSTANCE;
    public static final String URL_BASE = "http://192.168.43.92/api.mikuy.com/v1/";

    private ApiMikuyManager() {
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

    public Retrofit gerRetrofit() {
        return retrofit;
    }

    private ApiMikuyInterface get() {
        return retrofit.create(ApiMikuyInterface.class);
    }

    public static ApiMikuyInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiMikuyManager();
        }
        return INSTANCE.get();
    }
}
