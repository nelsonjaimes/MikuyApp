package com.restaurant.project.mikuyapp.home;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ListPlateResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface PlatesRepository {

    void savePlatesListSqlLite(@NonNull List<ListPlateResponseEntity.PlateResponseEntity> list);

    List<Plate> getListPlates();
}
