package com.restaurant.project.mikuyapp.home;

import android.content.Context;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.home.ui.HomeView;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.LogUtil;

public class HomePresenterImp implements HomePresenter, HomePresenter.Callback {
    private HomeView homeView;
    private final HomeInteractor homeInteractor;
    private final Context context;

    public HomePresenterImp(Context context) {
        this.context = context;
        homeInteractor = new HomeInteractorImp(context);
    }

    @Override
    public void onAttach(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void downloadPlatesList() {
        if (homeView != null && homeInteractor != null) {
            homeView.showProgress();
            homeInteractor.requestDownLoadPlatesList(this);
        }
    }

    @Override
    public void onDettach() {
        this.homeView = null;
        homeInteractor.onDetach();
    }

    @Override
    public void onErrorService(String message) {
        if (homeView != null) {
            homeView.hideProgress();
            homeView.showSnackBar(message);
        }
    }

    @Override
    public void onSuccessDownloadPlatesList() {
        if (homeView != null) {
            homeView.hideProgress();
            homeView.onSuccessDownloadPlatesList();
            LogUtil.d("onSuccessDownloadPlatesList");
        }
    }

    @Override
    public void onFailure() {
        if (homeView != null) {
            homeView.hideProgress();
            homeView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer, MikuyPreference.getUrlBaseServer()));
        }
    }
}
