package com.restaurant.project.mikuyapp.address.ui;

import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;

public interface AddressMapsView {
    void onSuccessDirectionApi(DirectionsApiResponse directionsApiResponse);

    void showProgressBar();

    void hideProgressBar();

    void onErrorService();

    void onFailure();
}
