package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

public class ReservationResponseEntity {

    private String message;
    @SerializedName("code_reserve")
    private String codeReserve;
    private float amount;
    @SerializedName("date_hour")
    private String dateHour;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeReserve() {
        return codeReserve;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDateHour() {
        return dateHour;
    }
    /*"status": 200,
                "message": "Se realiz\u00f3 la reservati\u00f3n correctamente",
                "code_reserve": "R4",
                "amount": 20.5,
                "date_hour": "18\/05\/18 2:35 am" */
}
