package com.restaurant.project.mikuyapp.reservation;

import android.content.Context;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationEntity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.List;

public class MyReservationPresenterImp implements MyReservationPresenter, MyReservationPresenter.Callback {

    private final Context context;
    private MyReservationView myReservationView;
    private final MyReservationInteractor myReservationInteractor;

    MyReservationPresenterImp(Context context) {
        this.context = context;
        myReservationInteractor = new MyReservationInteractorImp();
    }

    @Override
    public void onAttach(MyReservationView myReservationView) {
        this.myReservationView = myReservationView;
    }

    @Override
    public void onDetach() {
        myReservationView = null;
    }

    @Override
    public void startLoadMyReservationList() {
        if (myReservationView == null || myReservationInteractor == null) {
            return;
        }
        if (Operations.isNetworkAvailable(context)) {
            myReservationView.showProgress();
            myReservationInteractor.requestMyReservationList(this);
        } else {
            myReservationView.showSnackBar(context.getString(R.string.errorNetwoork));
        }
    }
    @Override
    public void onSuccessMyReservationList(List<MyReservationEntity> list) {
        if (myReservationView != null) {
            myReservationView.hideProgress();
            myReservationView.onSuccessMyReservationList(list);
        }
    }

    @Override
    public void onError(String message) {
        if (myReservationView != null) {
            myReservationView.hideProgress();
            myReservationView.showSnackBar(message);
        }
    }

    @Override
    public void onFailure() {
        if (myReservationView != null) {
            myReservationView.hideProgress();
            myReservationView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer, MikuyPreference.getUrlBaseServer()));
        }
    }
}
