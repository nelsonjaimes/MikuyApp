package com.restaurant.project.mikuyapp.menutoday;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface MenuTodayView {
    void showProgress();

    void hideProgress();

    void showSnackBar(String message);

    void populateRecyclerView(@NonNull List<Plate> backgroundList,
                              @NonNull List<Plate> dessertList, @NonNull List<Plate> entryList);

    void onSuccessReserve(ReservationResponseEntity reservationResponseEntity);
}
