package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

public class SignInResponseEntity {
    private String name;
    private String lastname;
    private String email;
    private String gender;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    /*
    {
    "status": "ok",
    "name": "Nelson",
    "lastname": "Jaimes",
    "email": "nelsonjaimesgonzales@gmail",
    "gender": "M",
    "token": "cf87ee41e81d5f25c911b46010e062ea"
}    */
}
