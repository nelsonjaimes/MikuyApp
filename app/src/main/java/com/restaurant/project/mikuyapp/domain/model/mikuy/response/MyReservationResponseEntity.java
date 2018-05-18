package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyReservationResponseEntity {

    private int status;
    @SerializedName("reservationlist")
    List<MyReservationEntity> reservationList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MyReservationEntity> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<MyReservationEntity> reservationList) {
        this.reservationList = reservationList;
    }
}
