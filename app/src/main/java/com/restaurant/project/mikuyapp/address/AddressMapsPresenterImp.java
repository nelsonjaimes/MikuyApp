package com.restaurant.project.mikuyapp.address;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.restaurant.project.mikuyapp.address.ui.AddressMapsView;
import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;
import com.restaurant.project.mikuyapp.utils.LogUtil;
import com.restaurant.project.mikuyapp.utils.Operations;

public class AddressMapsPresenterImp implements AddressMapsPresenter, AddressMapsPresenter.Callback {

    private AddressMapsView addressMapsView;
    private Context context;
    private AddressMapsInteractor addressMapsInteractor;

    @Override
    public void attachView(@NonNull AddressMapsView addressMapsView) {
        this.addressMapsView = addressMapsView;
        this.addressMapsInteractor = new AddressMapsInteractorImp();
        this.context = (Context) addressMapsView;
    }

    @Override
    public void initRequestDirectionApi(LatLng latLng) {
        if (Operations.isNetworkAvailable(context)) {
            if (addressMapsInteractor != null && addressMapsView != null) {
                addressMapsView.showProgressBar();
                addressMapsInteractor.initRequestDirectionApi(latLng, this);
            }
        } else {
            LogUtil.d("Error-initRequestDirectionApi");
            onFailure();
        }
    }

    @Override
    public void onDestroyView() {
        addressMapsInteractor.cancelCallbacks();
        addressMapsView = null;
        addressMapsInteractor = null;
    }

    @Override
    public void onSuccess(DirectionsApiResponse directionsApiResponse) {
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onSuccessDirectionApi(directionsApiResponse);
        }
    }

    @Override
    public void onErrorService() {
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onErrorService();
        }
    }

    @Override
    public void onFailure() {
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onFailure();
        }
    }
}
