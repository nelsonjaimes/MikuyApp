package com.restaurant.project.restaurantapp.reservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.project.restaurantapp.R;


/**
 * @author TacuTacuRestaurant
 */
public class MyReservationsFragment extends Fragment {

    public static MyReservationsFragment getInstance() {
        return new MyReservationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_reservations, container, false);
    }

}
