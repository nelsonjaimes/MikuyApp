package com.restaurant.project.mikuyapp.letter;

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

public class LetterPresenterImp implements LetterPresenter, LetterPresenter.Callback {
    private LetterView letterView;
    private final Context context;
    private final LetterInteractor letterInteractor;

    LetterPresenterImp(Context context) {
        this.context = context;
        this.letterInteractor = new LetterInteractorImp(context);
    }

    @Override
    public void onAttach(LetterView letterView) {
        this.letterView = letterView;
    }

    @Override
    public void onDetach() {
        letterView = null;
    }

    @Override
    public void startLoadingPlates() {
        if (letterInteractor != null) {
            letterInteractor.requestListPlatesDb(this);
        }
    }

    @Override
    public void makeReservation(List<Plate> plateList) {
        float amount = 0.0f;
        LogUtil.d("size:" + plateList.size());
        for (Plate plate : plateList) {
            amount += plate.getPrice() * plate.getAcount();
        }

        LogUtil.d("amountReserve:" + amount);
        SignInResponseEntity session = MikuyPreference.getUserSession(context);
        String emailUser = session.getEmail();
        if (!Operations.isNetworkAvailable(context)) {
            if (letterView != null) {
                letterView.showSnackBar(context.getString(R.string.errorNetwoork));
            }
            return;
        }
        if (letterView != null && letterInteractor != null) {
            ReservationRequestEntity reservationRequestEntity =
                    new ReservationRequestEntity(emailUser, amount, plateList);
            letterView.showProgress();
            letterInteractor.requestMakeReservation(reservationRequestEntity, this);
        }
    }

    @Override
    public void onSuccessListPlate(List<Plate> list) {
        List<Plate> plateList = new ArrayList<>();
        for (Plate plate : list) {
            if (plate.getCategory().equals(SqlGlobal.CATEGORY_LETTER)) {
                plateList.add(plate);
            }
        }
        if (letterView != null) {
            letterView.populateRecyclerLetter(plateList);
        }
    }

    @Override
    public void onSuccessMakeReservation(ReservationResponseEntity entity) {
        if (letterView != null) {
            letterView.hideProgress();
            letterView.onSuccessReserve(entity);
        }
    }

    @Override
    public void onError(String message) {
        if (letterView != null) {
            letterView.hideProgress();
            letterView.showSnackBar(message);
        }
    }

    @Override
    public void onFailure() {
        if (letterView != null) {
            letterView.hideProgress();
            letterView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer,
                            MikuyPreference.getUrlBaseServer(context)));
        }
    }
}

