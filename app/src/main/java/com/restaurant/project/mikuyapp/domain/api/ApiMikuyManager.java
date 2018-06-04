package com.restaurant.project.mikuyapp.domain.api;

import com.restaurant.project.mikuyapp.storage.MikuyPreference;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiMikuyManager {
    private static Retrofit retrofit;

    private ApiMikuyManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(8, TimeUnit.SECONDS);
        okBuilder.connectTimeout(8, TimeUnit.SECONDS);
        okBuilder.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(MikuyPreference.getUrlBaseServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okBuilder.build())
                .build();
    }

    private ApiMikuyInterface get() {
        return retrofit.create(ApiMikuyInterface.class);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
    public static ApiMikuyInterface getInstance() {
        return new ApiMikuyManager().get();
    }
}
