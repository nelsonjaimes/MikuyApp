package com.restaurant.project.mikuyapp.address;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.restaurant.project.mikuyapp.address.ui.AddressMapsView;
import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;

public interface AddressMapsPresenter {
    void attachView(AddressMapsView addressMapsView);

    void initRequestDirectionApi(@NonNull LatLng latLng);

    void onDestroyView();

    interface Callback {
        void onSuccess(@NonNull DirectionsApiResponse directionsApiResponse);

        void onErrorService();

        void onFailure();
    }
}
