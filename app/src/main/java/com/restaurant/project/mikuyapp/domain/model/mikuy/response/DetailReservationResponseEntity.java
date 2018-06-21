package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
class DetailReservationResponseEntity {

    @SerializedName("code_reserve")
    private String codeReserve;
    @SerializedName("code_plate")
    private String codePlate;
    @SerializedName("count_plate")
    private String countPlate;

    public String getCodeReserve() {
        return codeReserve;
    }

    public void setCodeReserve(String codeReserve) {
        this.codeReserve = codeReserve;
    }

    public String getCodePlate() {
        return codePlate;
    }

    public void setCodePlate(String codePlate) {
        this.codePlate = codePlate;
    }

    public String getCountPlate() {
        return countPlate;
    }

    public void setCountPlate(String countPlate) {
        this.countPlate = countPlate;
    }

}
