package com.restaurant.project.mikuyapp.home.ui;

import android.support.annotation.NonNull;

public interface HomeView {
    void showProgress();

    void hideProgress();

    void showSnackBar(@NonNull String message);

    void onSuccessDownloadPlatesList();
}
