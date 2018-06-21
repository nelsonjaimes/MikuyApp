package com.restaurant.project.mikuyapp.letter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.dialog.DialogProgress;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.letter.adapter.LettherAdapter;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.reservation.make.MakeReservationActivity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.BaseFragment;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.ArrayList;
import java.util.List;

import static com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment.EXTRA_AMOUNT;
import static com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment.EXTRA_CODE_RESERVE;
import static com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment.EXTRA_DATE_RESERVE;

/**
 * @author TacuTacuRestaurant
 */
public class LetterOfThePlatesFragment extends BaseFragment implements
        View.OnClickListener, LetterView {

    private RecyclerView rvLetter;
    private Context context;
    private Button btnReserve;
    private LetterPresenter letterPresenter;
    private RelativeLayout rlLetter;
    private final List<Plate> pedidoList = new ArrayList<>();
    private DialogProgress dialogProgress;
    private LettherAdapter lettherAdapter;

    public static LetterOfThePlatesFragment getInstance() {
        return new LetterOfThePlatesFragment();
    }

    @Override
    public void update() {
        if (MikuyPreference.getStateDownloadPlatesList(context)) {
            letterPresenter.startLoadingPlates();
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
        letterPresenter = new LetterPresenterImp(context);
        return inflater.inflate(R.layout.fragment_letter_of_dishes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvLetter = view.findViewById(R.id.rvLetter);
        btnReserve = view.findViewById(R.id.btnReserve);
        rlLetter = view.findViewById(R.id.rlLetter);
        letterPresenter.onAttach(this);
    }

    @Override
    public void onSuccessReserve(ReservationResponseEntity reservationResponseEntity) {
        Intent intent = new Intent(context, MakeReservationActivity.class);
        intent.putExtra(EXTRA_CODE_RESERVE, reservationResponseEntity.getCodeReserve());
        intent.putExtra(EXTRA_AMOUNT, String.valueOf(reservationResponseEntity.getAmount()));
        intent.putExtra(EXTRA_DATE_RESERVE, reservationResponseEntity.getDateHour());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        letterPresenter.onAttach(this);
        super.onResume();
    }

    @Override
    public void onStop() {
        letterPresenter.onDetach();
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnReserve.setOnClickListener(this);
        if (MikuyPreference.getStateDownloadPlatesList(context)) {
            letterPresenter.startLoadingPlates();
        }
    }

    @Override
    public void populateRecyclerLetter(@NonNull List<Plate> plateList) {
        lettherAdapter = new LettherAdapter(context);
        lettherAdapter.setPlateList(plateList);
        rvLetter.setAdapter(lettherAdapter);
    }

    @Override
    public void onClick(View v) {
        pedidoList.clear();
        for (Plate plate : lettherAdapter.getPlateList()) {
            if (plate.isAggregate()) {
                pedidoList.add(plate);
            }
        }
        if (pedidoList.size() > 0) {
            if (letterPresenter != null)
                letterPresenter.makeReservation(pedidoList);
        } else {
            showSnackBar(getString(R.string.emptyListLetterReserver));
        }
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
        Operations.getSnackBar(context, rlLetter, message, Snackbar.LENGTH_SHORT).show();
    }


}
