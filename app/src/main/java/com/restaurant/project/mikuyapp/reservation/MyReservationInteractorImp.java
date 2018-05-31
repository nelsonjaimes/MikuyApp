package com.restaurant.project.mikuyapp.reservation;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.MyReservationRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationResponseEntity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservationInteractorImp implements MyReservationInteractor {
    @Override
    public void requestMyReservationList(final MyReservationPresenter.Callback callback) {
        String email = MikuyPreference.getUserSession().getEmail();
        MyReservationRequestEntity myReservationRequestEntity = new MyReservationRequestEntity(email);
        ApiMikuyInterface mikuyInterface = ApiMikuyManager.getInstance();
        Call<MyReservationResponseEntity> call = mikuyInterface.reqeustReservationList(myReservationRequestEntity);
        call.enqueue(new Callback<MyReservationResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<MyReservationResponseEntity> call,
                                   @NonNull Response<MyReservationResponseEntity> response) {
                if (response.isSuccessful()) {
                    MyReservationResponseEntity entity = response.body();
                    if (callback != null) {
                        callback.onSuccessMyReservationList(entity.getReservationList());
                    }
                } else {
                    MikuyException mikuyException = MikuyException.parseError(response);
                    if (callback != null) callback.onError(mikuyException.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyReservationResponseEntity> call,
                                  @NonNull Throwable t) {
                if (callback != null) callback.onFailure();
            }
        });
    }
}
