package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyReservationResponseEntity {

    @SerializedName("reservationlist")
    private List<MyReservationEntity> reservationList;


    public List<MyReservationEntity> getReservationList() {
        return reservationList;
    }
}
