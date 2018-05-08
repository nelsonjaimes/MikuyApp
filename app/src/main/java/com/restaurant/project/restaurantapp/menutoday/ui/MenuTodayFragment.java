package com.restaurant.project.restaurantapp.menutoday.ui;


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

import com.restaurant.project.restaurantapp.R;
import com.restaurant.project.restaurantapp.menutoday.adapter.PlateAdapter;
import com.restaurant.project.restaurantapp.menutoday.model.Plate;
import com.restaurant.project.restaurantapp.reservation.make.MakeReservationActivity;
import com.restaurant.project.restaurantapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author TacuTacuRestaurant
 */
public class MenuTodayFragment extends Fragment implements PlateRecyclerListener,
        View.OnClickListener {

    private RecyclerView rvTodayPlate;
    private Context context;
    private Button btnReserve;

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
        return inflater.inflate(R.layout.fragment_menu_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTodayPlate = view.findViewById(R.id.rvTodayPlate);
        btnReserve = view.findViewById(R.id.btnReserve);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnReserve.setOnClickListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<Plate> todayPlateList = new ArrayList<>();
        todayPlateList.add(new Plate("Lomo Saltado", 12.5, PlateAdapter.TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Arroz a la jardinera", 12.0, PlateAdapter.TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Chorizo de chancho", 15.0, PlateAdapter.TYPE_MENU_BACKGROUND));
        todayPlateList.add(new Plate("Lomo Saltado", 12.5, PlateAdapter.TYPE_MENU_BACKGROUND));

        todayPlateList.add(new Plate("Papa ala Huancaina", 0.0, PlateAdapter.TYPE_MENU_ENTRY));
        todayPlateList.add(new Plate("Yuquitas", 0.0, PlateAdapter.TYPE_MENU_ENTRY));
        todayPlateList.add(new Plate("Picarones", 0.0, PlateAdapter.TYPE_MENU_ENTRY));

        todayPlateList.add(new Plate("Gelatina", 0.0, PlateAdapter.TYPE_MENU_DESSERT));
        todayPlateList.add(new Plate("Pai de Manzana", 0.0, PlateAdapter.TYPE_MENU_DESSERT));
        todayPlateList.add(new Plate("Pastel de choclo", 0.0, PlateAdapter.TYPE_MENU_DESSERT));

        PlateAdapter backgroundPlateAdapter = new PlateAdapter(context);
        backgroundPlateAdapter.setPlateList(todayPlateList);
        backgroundPlateAdapter.setPlateRecyclerListener(this);

        rvTodayPlate.setAdapter(backgroundPlateAdapter);
    }

    @Override
    public void selectItem(Plate plate, int position) {
        switch (plate.getCategory()) {
            case PlateAdapter.TYPE_MENU_BACKGROUND:
                LogUtil.d("PlatoFondo:" + plate.getName());
                break;
            case PlateAdapter.TYPE_MENU_ENTRY:
                LogUtil.d("Entrada:" + plate.getName());
                break;
            case PlateAdapter.TYPE_MENU_DESSERT:
                LogUtil.d("Postre:" + plate.getName());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(context, MakeReservationActivity.class));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
