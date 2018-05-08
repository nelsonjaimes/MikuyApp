package com.restaurant.project.restaurantapp.address;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.project.restaurantapp.R;


/**
 * @author La casa del Tacu Tacu
 */
public class AddressLocationFragment extends Fragment {


    public static AddressLocationFragment getInstance() {
        return new AddressLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address_location, container, false);
    }

}
