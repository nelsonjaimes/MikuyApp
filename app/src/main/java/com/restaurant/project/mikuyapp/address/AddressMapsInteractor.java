package com.restaurant.project.mikuyapp.address;

import com.google.android.gms.maps.model.LatLng;

public interface AddressMapsInteractor {
    void initRequestDirectionApi(LatLng latLng, AddressMapsPresenter.Callback callback);

    void cancelCallbacks();

}
