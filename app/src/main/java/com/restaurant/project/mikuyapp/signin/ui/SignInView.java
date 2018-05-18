package com.restaurant.project.mikuyapp.signin.ui;

public interface SignInView {
    void showProgress();

    void hideProgress();

    void onSucessSignIn();

    void showSnackBar(String message);
}
