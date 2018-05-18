package com.restaurant.project.mikuyapp.menutoday;

import android.content.Context;

import com.restaurant.project.mikuyapp.home.PlatesRepository;
import com.restaurant.project.mikuyapp.home.PlatesRepositoryImp;

public class MenuTodayInteractorImp implements MenuTodayInteractor {

    private PlatesRepository platesRepository;

    MenuTodayInteractorImp(Context context) {
        platesRepository = new PlatesRepositoryImp(context);
    }

    @Override
    public void requestListPlatesDb(MenuTodayPresenter.Callback callback) {
        if (callback != null) {
            callback.onSuccessListPlate(platesRepository.getListPlates());
        }
    }
}
