package com.restaurant.project.mikuyapp.address.ui;

import android.support.annotation.StringRes;

import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;

public interface AddressMapsView {
    void onSuccessDirectionApi(DirectionsApiResponse directionsApiResponse);

    void showProgressBar();

    void hideProgressBar();

    void onErrorService();

    void onFailure();
}
