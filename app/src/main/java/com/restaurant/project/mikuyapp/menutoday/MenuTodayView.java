package com.restaurant.project.mikuyapp.menutoday;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface MenuTodayView {
    void showProgress();

    void hideProgress();

    void populateRecyclerView(@NonNull List<Plate> backgroundList,
                              @NonNull List<Plate> dessertList, @NonNull List<Plate> entryList);
}
