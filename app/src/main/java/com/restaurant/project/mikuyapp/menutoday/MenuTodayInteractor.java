package com.restaurant.project.mikuyapp.menutoday;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.ReservationRequestEntity;

public interface MenuTodayInteractor {
    void requestListPlatesDb(MenuTodayPresenter.Callback callback);

    void requestMakeReservation(ReservationRequestEntity entity, MenuTodayPresenter.Callback callback);
}
