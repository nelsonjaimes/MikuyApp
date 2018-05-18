package com.restaurant.project.mikuyapp.home;

import com.restaurant.project.mikuyapp.home.ui.HomeView;

public interface HomePresenter {
    void onAttach(HomeView homeView);

    void onDettach();

    void downloadPlatesList();

    interface Callback {
        void onErrorService(String message);

        void onSuccessDownloadPlatesList();

        void onFailure();
    }
}
