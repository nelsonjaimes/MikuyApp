package com.restaurant.project.mikuyapp.domain.api;

import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiDirections {
    @POST("directions/json")
    Call<DirectionsApiResponse> getPointsLocation(@Query("origin") String startLocation,
                                                  @Query("destination") String endLocation,
                                                  @Query("key") String key);
}
