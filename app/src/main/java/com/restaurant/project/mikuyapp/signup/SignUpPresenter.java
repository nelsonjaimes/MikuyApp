package com.restaurant.project.mikuyapp.signup;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.signup.ui.SignUpView;

public interface SignUpPresenter {
    void attachView(SignUpView signUpView);

    void dettachView();

    void initRegister(@NonNull String name, @NonNull String lastName, @NonNull String email,
                      char gender, @NonNull String password);

    interface Callback {
        void onErrorService(@NonNull String message);

        void onSuccess(SignInResponseEntity signInResponseEntity);

        void onFailure();
    }
}
