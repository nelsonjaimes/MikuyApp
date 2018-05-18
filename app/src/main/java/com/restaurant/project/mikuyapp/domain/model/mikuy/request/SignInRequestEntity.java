package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

public class SignInRequestEntity {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* {
"email":"mariamercedes@gmaidl.com",
"password":"mariamercedes"
} */
}
