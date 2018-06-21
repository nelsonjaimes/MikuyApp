package com.restaurant.project.mikuyapp.menutoday;

import android.content.Context;
import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.ReservationRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.home.PlatesRepository;
import com.restaurant.project.mikuyapp.home.PlatesRepositoryImp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuTodayInteractorImp implements MenuTodayInteractor {

    private final PlatesRepository platesRepository;
    private final Context context;

    MenuTodayInteractorImp(Context context) {
        platesRepository = new PlatesRepositoryImp(context);
        this.context = context;
    }

    @Override
    public void requestListPlatesDb(MenuTodayPresenter.Callback callback) {
        if (callback != null) {
            callback.onSuccessListPlate(platesRepository.getListPlates());
        }
    }

    @Override
    public void requestMakeReservation(ReservationRequestEntity entity,
                                       final MenuTodayPresenter.Callback callback) {
        ApiMikuyInterface apiMikuyInterface = ApiMikuyManager.getInstance(context);
        Call<ReservationResponseEntity> call = apiMikuyInterface.makeReserve(entity);
        call.enqueue(new Callback<ReservationResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<ReservationResponseEntity> call,
                                   @NonNull Response<ReservationResponseEntity> response) {
                if (response.isSuccessful()) {
                    ReservationResponseEntity reservationResponseEntity = response.body();
                    if (callback != null)
                        callback.onSuccessMakeReservation(reservationResponseEntity);
                } else {
                    MikuyException mikuyException = MikuyException.parseError(response, context);
                    if (callback != null) callback.onError(mikuyException.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReservationResponseEntity> call,
                                  @NonNull Throwable t) {
                callback.onFailure();
            }
        });

    }
}
