package com.restaurant.project.mikuyapp.menutoday;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface MenuTodayPresenter {

    void onAttach(MenuTodayView menuTodayView);

    void onDetach();

    void startLoadingPlates();

    interface Callback {
        void onSuccessListPlate(List<Plate> list);

        void onError();
    }
}
