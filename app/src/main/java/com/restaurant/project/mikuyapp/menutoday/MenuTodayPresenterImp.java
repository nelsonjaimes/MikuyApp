package com.restaurant.project.mikuyapp.menutoday;

import android.content.Context;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuTodayPresenterImp implements MenuTodayPresenter, MenuTodayPresenter.Callback {

    private MenuTodayView menuTodayView;
    private Context context;
    private MenuTodayInteractor menuTodayInteractor;

    public MenuTodayPresenterImp(Context context) {
        this.context = context;
        this.menuTodayInteractor = new MenuTodayInteractorImp(context);
    }

    @Override
    public void onAttach(MenuTodayView menuTodayView) {
        this.menuTodayView = menuTodayView;
    }

    @Override
    public void startLoadingPlates() {
        if (menuTodayInteractor != null) {
            menuTodayInteractor.requestListPlatesDb(this);
        }
    }

    @Override
    public void onDetach() {
        this.menuTodayView = null;
    }

    @Override
    public void onSuccessListPlate(List<Plate> list) {
        List<Plate> backgroundList = new ArrayList<>();
        List<Plate> dessertList = new ArrayList<>();
        List<Plate> entryList = new ArrayList<>();

        for (Plate plate : list) {
            LogUtil.d("plate:" + plate.getName());
        }
    }

    @Override
    public void onError() {

    }
}
