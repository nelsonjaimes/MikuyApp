package com.restaurant.project.mikuyapp.letter;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface LetterView {
    void showProgress();

    void hideProgress();

    void showSnackBar(String message);

    void populateRecyclerLetter(@NonNull List<Plate> plateList);

    void onSuccessReserve(ReservationResponseEntity reservationResponseEntity);
}
