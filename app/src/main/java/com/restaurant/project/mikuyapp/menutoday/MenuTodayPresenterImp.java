package com.restaurant.project.mikuyapp.menutoday;

import android.content.Context;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.ReservationRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.storage.sqlite.SqlGlobal;
import com.restaurant.project.mikuyapp.utils.LogUtil;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.ArrayList;
import java.util.List;

public class MenuTodayPresenterImp implements MenuTodayPresenter, MenuTodayPresenter.Callback {

    private MenuTodayView menuTodayView;
    private final Context context;
    private final MenuTodayInteractor menuTodayInteractor;

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
    public void makeReservation(List<Plate> plateList) {
        float amount = 0.0f;
        for (Plate plate : plateList) {
            amount += plate.getPrice();
        }
        LogUtil.d("amountReserve:" + amount);
        SignInResponseEntity session = MikuyPreference.getUserSession();
        String emailUser = session.getEmail();
        if (!Operations.isNetworkAvailable(context)) {
            if (menuTodayView != null) {
                menuTodayView.showSnackBar(context.getString(R.string.errorNetwoork));
            }
            return;
        }
        if (menuTodayInteractor != null && menuTodayView != null) {
            ReservationRequestEntity reservationRequestEntity =
                    new ReservationRequestEntity(emailUser, amount, plateList);
            menuTodayView.showProgress();
            menuTodayInteractor.requestMakeReservation(reservationRequestEntity, this);
        }
    }

    @Override
    public void onDetach() {
        this.menuTodayView = null;
    }

    @Override
    public void onSuccessListPlate(List<Plate> list) {
        List<Plate> menuList = new ArrayList<>();
        List<Plate> dessertList = new ArrayList<>();
        List<Plate> entryList = new ArrayList<>();
        for (Plate plate : list) {
            switch (plate.getCategory()) {
                case SqlGlobal.CATEGORY_MENU:
                    menuList.add(plate);
                    break;
                case SqlGlobal.CATEGORY_ENTRY:
                    entryList.add(plate);
                    break;
                case SqlGlobal.CATEGORY_DESSERT:
                    dessertList.add(plate);
                    break;
            }
        }
        if (menuTodayView != null) {
            menuTodayView.populateRecyclerView(menuList, dessertList, entryList);
        }
    }

    @Override
    public void onSuccessMakeReservation(ReservationResponseEntity entity) {
        LogUtil.d("ser realiso la reservacion crrectamente .. !!");
        LogUtil.d("codeReservation:" + entity.getCodeReserve());
        if (menuTodayView!=null){
            menuTodayView.hideProgress();
            menuTodayView.onSuccessReserve(entity);
        }
    }

    @Override
    public void onError(String message) {
        if (menuTodayView != null) {
            menuTodayView.hideProgress();
            menuTodayView.showSnackBar(message);
        }
    }

    @Override
    public void onFailure() {
        if (menuTodayView != null) {
            menuTodayView.hideProgress();
            menuTodayView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer, MikuyPreference.getUrlBaseServer()));
        }
    }
}
