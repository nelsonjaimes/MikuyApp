package com.restaurant.project.mikuyapp.address;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.restaurant.project.mikuyapp.address.ui.AddressMapsView;
import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;
import com.restaurant.project.mikuyapp.utils.Operations;

public class AddressMapsPresenterImp implements AddressMapsPresenter, AddressMapsPresenter.Callback {

    private AddressMapsView addressMapsView;
    private Context context;
    private AddressMapsInteractor addressMapsInteractor;
    private boolean stateAvality;
    @Override
    public void attachView(@NonNull AddressMapsView addressMapsView) {
        this.addressMapsView = addressMapsView;
        this.addressMapsInteractor = new AddressMapsInteractorImp();
        this.context = (Context) addressMapsView;
        this.stateAvality = true;
    }

    @Override
    public void initRequestDirectionApi(@NonNull LatLng latLng) {
        if (stateAvality) {
            if (Operations.isNetworkAvailable(context)) {
                if (addressMapsInteractor != null && addressMapsView != null) {
                    stateAvality = false;
                    addressMapsView.showProgressBar();
                    addressMapsInteractor.initRequestDirectionApi(latLng, this);
                }
            } else {
                onFailure();
            }
        }
    }

    @Override
    public void onDestroyView() {
        stateAvality = false;
        addressMapsInteractor.cancelCallbacks();
        addressMapsView = null;
        addressMapsInteractor = null;
    }

    @Override
    public void onSuccess(@NonNull DirectionsApiResponse response) {
        stateAvality = true;
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onSuccessDirectionApi(response);
        }
    }

    @Override
    public void onErrorService() {
        stateAvality = true;
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onErrorService();
        }
    }

    @Override
    public void onFailure() {
        stateAvality = true;
        if (addressMapsView != null) {
            addressMapsView.hideProgressBar();
            addressMapsView.onFailure();
        }
    }
}
