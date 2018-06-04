package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

public class SignUpRequestEntity {
    private String email;
    private String name;
    private String lastname;
    private char gender;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
