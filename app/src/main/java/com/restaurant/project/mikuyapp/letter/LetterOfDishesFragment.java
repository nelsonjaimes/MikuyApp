package com.restaurant.project.mikuyapp.letter;


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
import com.restaurant.project.mikuyapp.letter.adapter.LettherAdapter;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.menutoday.ui.PlateRecyclerListener;
import com.restaurant.project.mikuyapp.reservation.make.MakeReservationActivity;

import java.util.ArrayList;
import java.util.List;

import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_LETTER_FOOD;

/**
 * @author TacuTacuRestaurant
 */
public class LetterOfDishesFragment extends Fragment implements PlateRecyclerListener,
        View.OnClickListener {

    private RecyclerView rvLetter;
    private Context context;
    private Button btnReserve;

    public static LetterOfDishesFragment getInstance() {
        return new LetterOfDishesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_letter_of_dishes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvLetter = view.findViewById(R.id.rvLetter);
        btnReserve = view.findViewById(R.id.btnReserve);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnReserve.setOnClickListener(this);
        initRecyclerView();
    }

    void initRecyclerView() {
        List<Plate> letterList = new ArrayList<>();
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Arroz a la jardinera", 12.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Chorizo de chancho", 15.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Arroz a la jardinera", 12.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Chorizo de chancho", 15.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Arroz a la jardinera", 12.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Chorizo de chancho", 15.0, TYPE_LETTER_FOOD));
        letterList.add(new Plate("Lomo Saltado", 12.5, TYPE_LETTER_FOOD));

        LettherAdapter lettherAdapter = new LettherAdapter(context);
        lettherAdapter.setRecyclerListener(this);
        lettherAdapter.setPlateList(letterList);
        rvLetter.setAdapter(lettherAdapter);
    }

    @Override
    public void selectItem(int position) {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(context, MakeReservationActivity.class));
    }

}
