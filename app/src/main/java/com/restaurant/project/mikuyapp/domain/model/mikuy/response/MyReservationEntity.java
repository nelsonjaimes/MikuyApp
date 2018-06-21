package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("all")
public class MyReservationEntity {

    @SerializedName("codereserve")
    private String codeReserve;
    private String datehour;
    private int state;
    @SerializedName("plateList")
    private List<DetailReservationResponseEntity> plateList;

    public String getCodeReserve() {
        return codeReserve;
    }

    public void setCodeReserve(String codeReserve) {
        this.codeReserve = codeReserve;
    }

    public String getDatehour() {
        return datehour;
    }

    public void setDatehour(String datehour) {
        this.datehour = datehour;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DetailReservationResponseEntity> getPlateList() {
        return plateList;
    }

    public void setPlateList(List<DetailReservationResponseEntity> plateList) {
        this.plateList = plateList;
    }

    /*
    * "codereserve": "R1",
        "datehour": "18/05/18 3:43 am",
        "amount": "8",
        "state": "0",
        "plateList
    * */
}
