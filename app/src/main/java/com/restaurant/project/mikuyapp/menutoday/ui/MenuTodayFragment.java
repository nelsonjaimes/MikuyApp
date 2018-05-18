package com.restaurant.project.mikuyapp.menutoday.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayPresenter;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayPresenterImp;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayView;
import com.restaurant.project.mikuyapp.menutoday.adapter.MenuTodayAdapter;
import com.restaurant.project.mikuyapp.menutoday.model.Category;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.reservation.make.MakeReservationActivity;

import java.util.ArrayList;
import java.util.List;

import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_MENU_BACKGROUND;
import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_MENU_DESSERT;
import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_MENU_ENTRY;


/**
 * @author TacuTacuRestaurant
 */
public class MenuTodayFragment extends Fragment implements PlateRecyclerListener,
        View.OnClickListener, MenuTodayView {

    private RecyclerView rvTodayPlate;
    private Context context;
    private Button btnReserve;
    private MenuTodayPresenter menuTodayPresenter;

    public static MenuTodayFragment getInstance() {
        return new MenuTodayFragment();
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
        menuTodayPresenter.onAttach(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnReserve.setOnClickListener(this);
        menuTodayPresenter.startLoadingPlates();
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

    private void initRecyclerView() {
        List<Object> todayPlateList = new ArrayList<>();
        todayPlateList.add(new Category(R.drawable.background_64, get(R.string.backgroundPlate)));
        todayPlateList.add(new Plate("Lomo Saltado", 12.5f, TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Arroz a la jardinera", 12.0f, TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Chorizo de chancho", 15.0f, TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Lomo Saltado", 12.5f, TYPE_MENU_BACKGROUND));

        todayPlateList.add(new Category(R.drawable.entry_64, get(R.string.entry)));
        todayPlateList.add(new Plate("Papa ala Huancaina", 0.0f, TYPE_MENU_ENTRY));
        todayPlateList.add(new Plate("Yuquitas", 0.0f, TYPE_MENU_ENTRY));
        todayPlateList.add(new Plate("Picarones", 0.0f, TYPE_MENU_ENTRY));
        todayPlateList.add(new Category(R.drawable.dessert_64, get(R.string.dessert)));

        todayPlateList.add(new Plate("Gelatina", 0.0f, TYPE_MENU_DESSERT));
        todayPlateList.add(new Plate("Pai de Manzana", 0.0f, TYPE_MENU_DESSERT));
        todayPlateList.add(new Plate("Pastel de choclo", 0.0f, TYPE_MENU_DESSERT));

        MenuTodayAdapter backgroundMenuTodayAdapter = new MenuTodayAdapter(context);
        backgroundMenuTodayAdapter.setObjectList(todayPlateList);
        backgroundMenuTodayAdapter.setPlateRecyclerListener(this);

        rvTodayPlate.setAdapter(backgroundMenuTodayAdapter);
    }

    @Override
    public void selectItem(int position) {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(context, MakeReservationActivity.class));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private String get(int idRes) {
        return context.getResources().getString(idRes);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void populateRecyclerView(@NonNull List<Plate> backgroundList,
                                     @NonNull List<Plate> dessertList,
                                     @NonNull List<Plate> entryList) {


    }
}
