package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

public class SignInResponseEntity {
    private String status;
    private String name;
    private String lastname;
    private String email;
    private String gender;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
