package com.restaurant.project.mikuyapp.reservation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.dialog.DialogProgress;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationEntity;
import com.restaurant.project.mikuyapp.reservation.adapter.MyReservationAdapter;
import com.restaurant.project.mikuyapp.utils.BaseFragment;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.List;
/**
 * @author TacuTacuRestaurant
 */
public class MyReservationsFragment extends BaseFragment implements MyReservationView {

    private Context context;
    private RecyclerView rvMyReservation;
    private LinearLayout llMyReservation;
    private DialogProgress dialogProgress;
    private MyReservationPresenter myReservationPresenter;


    public static MyReservationsFragment getInstance() {
        return new MyReservationsFragment();
    }

    @Override
    public void update() {
        if (myReservationPresenter != null) {
            myReservationPresenter.startLoadMyReservationList();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myReservationPresenter = new MyReservationPresenterImp(context);
        return inflater.inflate(R.layout.fragment_my_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myReservationPresenter.onAttach(this);
        rvMyReservation = view.findViewById(R.id.rvMyReservation);
        llMyReservation = view.findViewById(R.id.llMyReservation);
    }

    @Override
    public void onResume() {
        myReservationPresenter.onAttach(this);
        super.onResume();
    }

    @Override
    public void onStop() {
        myReservationPresenter.onDetach();
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myReservationPresenter.startLoadMyReservationList();
    }

    @Override
    public void showProgress() {
        dialogProgress = DialogProgress.getInstance();
        dialogProgress.show(getFragmentManager(), Constant.DIALOG_PROGRESS);
    }

    @Override
    public void hideProgress() {
        if (dialogProgress == null) {
            dialogProgress = (DialogProgress) getFragmentManager().
                    findFragmentByTag(Constant.DIALOG_PROGRESS);
        }
        dialogProgress.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        Operations.getSnackBar(llMyReservation, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessMyReservationList(List<MyReservationEntity> list) {
        MyReservationAdapter myReservationAdapter = new MyReservationAdapter(context);
        myReservationAdapter.setEntityList(list);
        rvMyReservation.setAdapter(myReservationAdapter);
    }
}
