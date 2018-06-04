package com.restaurant.project.mikuyapp.home;

interface HomeInteractor {
    void requestDownLoadPlatesList(HomePresenter.Callback callback);

    void onDetach();
}
