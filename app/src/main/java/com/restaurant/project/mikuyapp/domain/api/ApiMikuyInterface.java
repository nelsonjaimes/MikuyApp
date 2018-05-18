package com.restaurant.project.mikuyapp.domain.api;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.MyReservationRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.ReservationRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignInRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ListPlateResponseEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationResponseEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiMikuyInterface {
    @POST("user/signup")
    Call<SignInResponseEntity> requestSignUp(@Body SignUpRequestEntity signUpRequestEntity);

    @POST("user/signin")
    Call<SignInResponseEntity> requestSignIn(@Body SignInRequestEntity signInRequestEntity);

    @GET("plates/list")
    Call<ListPlateResponseEntity> requestPlatesList();

    @POST("reserve/make")
    Call<ReservationResponseEntity> makeReserve(@Body ReservationRequestEntity reservationRequestEntity);

    @POST("reserve/list")
    Call<MyReservationResponseEntity> reqeustReservationList(@Body MyReservationRequestEntity requestEntity);

}
