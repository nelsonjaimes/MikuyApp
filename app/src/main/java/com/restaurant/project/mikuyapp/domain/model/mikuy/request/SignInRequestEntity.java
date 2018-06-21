package com.restaurant.project.mikuyapp.domain.model.mikuy.request;

@SuppressWarnings("all")
public class SignInRequestEntity {
    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* {
"email":"mariamercedes@gmaidl.com",
"password":"mariamercedes"
} */
}
