package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

public class MyReservationRequestEntity {
    private String email;

    public MyReservationRequestEntity(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
