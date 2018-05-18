package com.restaurant.project.mikuyapp.reservation;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationEntity;

import java.util.List;

public interface MyReservationView {
    void showProgress();

    void hideProgress();

    void showSnackBar(String message);

    void onSuccessMyReservationList(List<MyReservationEntity> list);

}
