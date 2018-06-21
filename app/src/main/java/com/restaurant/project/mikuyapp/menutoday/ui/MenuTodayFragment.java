package com.restaurant.project.mikuyapp.menutoday.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import com.restaurant.project.mikuyapp.menutoday.MenuTodayPresenter;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayPresenterImp;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayView;
import com.restaurant.project.mikuyapp.menutoday.adapter.MenuTodayAdapter;
import com.restaurant.project.mikuyapp.menutoday.model.Category;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.reservation.make.MakeReservationActivity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.storage.sqlite.SqlGlobal;
import com.restaurant.project.mikuyapp.utils.BaseFragment;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.LogUtil;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.ArrayList;
import java.util.List;


/**
 * @author TacuTacuRestaurant
 */
public class MenuTodayFragment extends BaseFragment implements PlateRecyclerListener,
        View.OnClickListener, MenuTodayView {

    private Context context;
    private Button btnReserve;
    private RecyclerView rvTodayPlate;
    private RelativeLayout rlMenuToday;
    private List<Object> todayPlateList;
    private DialogProgress dialogProgress;
    private MenuTodayPresenter menuTodayPresenter;
    // Pedido
    private Plate menuPlate, entryPlate, dessertPlate;
    public static final String EXTRA_AMOUNT = "amountReserve";
    public static final String EXTRA_CODE_RESERVE = "codeReserve";
    public static final String EXTRA_DATE_RESERVE = "dateReserve";

    public static MenuTodayFragment getInstance() {
        return new MenuTodayFragment();
    }


    @Override
    public void update() {
        if (MikuyPreference.getStateDownloadPlatesList(context)) {
            menuTodayPresenter.startLoadingPlates();
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
        menuTodayPresenter = new MenuTodayPresenterImp(context);
        return inflater.inflate(R.layout.fragment_menu_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTodayPlate = view.findViewById(R.id.rvTodayPlate);
        btnReserve = view.findViewById(R.id.btnReserve);
        rlMenuToday = view.findViewById(R.id.rlMenuToday);
        menuTodayPresenter.onAttach(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnReserve.setOnClickListener(this);
        if (MikuyPreference.getStateDownloadPlatesList(context)) {
            menuTodayPresenter.startLoadingPlates();
        }
    }
    @Override
    public void onResume() {
        menuTodayPresenter.onAttach(this);
        super.onResume();
    }

    @Override
    public void onStop() {
        menuTodayPresenter.onDetach();
        super.onStop();
    }

    @Override
    public void selectItem(int position) {
        Plate plate = (Plate) todayPlateList.get(position);
        switch (plate.getCategory()) {
            case SqlGlobal.CATEGORY_MENU:
                menuPlate = plate;
                LogUtil.d("menu");
                break;
            case SqlGlobal.CATEGORY_DESSERT:
                dessertPlate = plate;
                LogUtil.d("dessert");
                break;
            case SqlGlobal.CATEGORY_ENTRY:
                entryPlate = plate;
                LogUtil.d("entry");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (menuPlate == null) {
            showSnackbar(get(R.string.emptyBackgroundPlate));
            return;
        } else if (entryPlate == null) {
            showSnackbar(get(R.string.emptyEntry));
            return;
        } else if (dessertPlate == null) {
            showSnackbar(get(R.string.emptyDessert));
            return;
        }
        List<Plate> plateList = new ArrayList<>();
        plateList.add(menuPlate);
        plateList.add(entryPlate);
        plateList.add(dessertPlate);
        if (menuTodayPresenter != null) {
            menuTodayPresenter.makeReservation(plateList);
        }
    }

    private String get(@StringRes int idRes) {
        return context.getResources().getString(idRes);
    }

    @Override
    public void showProgress() {
        dialogProgress = DialogProgress.getInstance();
        dialogProgress.show(getFragmentManager(), Constant.DIALOG_PROGRESS);
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
    public void hideProgress() {
        if (dialogProgress == null) {
            dialogProgress = (DialogProgress) getFragmentManager().
                    findFragmentByTag(Constant.DIALOG_PROGRESS);
        }
        dialogProgress.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        showSnackbar(message);
    }

    @Override
    public void populateRecyclerView(@NonNull List<Plate> backgroundList,
                                     @NonNull List<Plate> dessertList,
                                     @NonNull List<Plate> entryList) {
        todayPlateList = new ArrayList<>();
        todayPlateList.add(new Category(R.drawable.background_64, get(R.string.backgroundPlate)));
        todayPlateList.addAll(backgroundList);
        todayPlateList.add(new Category(R.drawable.entry_64, get(R.string.entry)));
        todayPlateList.addAll(entryList);
        todayPlateList.add(new Category(R.drawable.dessert_64, get(R.string.dessert)));
        todayPlateList.addAll(dessertList);

        MenuTodayAdapter backgroundMenuTodayAdapter = new MenuTodayAdapter(context);
        backgroundMenuTodayAdapter.setObjectList(todayPlateList);
        backgroundMenuTodayAdapter.setPlateRecyclerListener(this);
        rvTodayPlate.setAdapter(backgroundMenuTodayAdapter);
    }

    private void showSnackbar(String message) {
        Operations.getSnackBar(context, rlMenuToday, message, Snackbar.LENGTH_SHORT).show();
    }
}
