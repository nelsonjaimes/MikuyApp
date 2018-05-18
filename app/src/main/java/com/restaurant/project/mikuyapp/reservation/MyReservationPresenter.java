package com.restaurant.project.mikuyapp.reservation;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationEntity;

import java.util.List;

public interface MyReservationPresenter {

    void onAttach(MyReservationView myReservationView);

    void onDetach();

    void startLoadMyReservationList();

    interface Callback {
        void onSuccessMyReservationList(List<MyReservationEntity> list);

        void onError(String message);

        void onFailure();
    }

}
