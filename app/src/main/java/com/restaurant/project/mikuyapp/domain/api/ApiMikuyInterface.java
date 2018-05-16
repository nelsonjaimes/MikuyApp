package com.restaurant.project.mikuyapp.domain.api;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignUpResponseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiMikuyInterface {
    @POST("user/signup")
    Call<SignUpResponseEntity> requestSignUp(@Body SignUpRequestEntity signUpRequestEntity);

}
