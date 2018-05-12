package com.restaurant.project.mikuyapp.address;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.restaurant.project.mikuyapp.domain.api.ApiDirections;
import com.restaurant.project.mikuyapp.domain.api.ApiDirectionsManager;
import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressMapsInteractorImp implements AddressMapsInteractor {
    private static Call<DirectionsApiResponse> callRequest;

    @Override
    public void initRequestDirectionApi(@NonNull LatLng latLng,
                                        @NonNull final AddressMapsPresenter.Callback callback) {
        LogUtil.d("Iniciando el initRequestDirectionApi...");
        String startLocation = latLng.latitude + "," + latLng.longitude;
        String endLocation = "-12.0697911,-77.0496689";
        ApiDirections instance = ApiDirectionsManager.getInstance();
        callRequest = instance.getPointsLocation(startLocation, endLocation, ApiDirectionsManager.KEY);
        callRequest.enqueue(new Callback<DirectionsApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsApiResponse> call,
                                   @NonNull Response<DirectionsApiResponse> response) {
                DirectionsApiResponse directionResponse = response.body();
                if (directionResponse != null) {
                    if (directionResponse.getStatus().equals(ApiDirectionsManager.OK)) {
                        LogUtil.d("Status:" + directionResponse.getStatus());
                        callback.onSuccess(directionResponse);
                    } else {
                        LogUtil.d("onErrorService");
                        callback.onErrorService();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsApiResponse> call,
                                  @NonNull Throwable t) {
                callback.onFailure();
                LogUtil.d("onFailure");
            }
        });
    }

    @Override
    public void cancelCallbacks() {
        if (callRequest != null) {
            LogUtil.d("cancelCallbacks");
            callRequest.cancel();
        }
    }
}
