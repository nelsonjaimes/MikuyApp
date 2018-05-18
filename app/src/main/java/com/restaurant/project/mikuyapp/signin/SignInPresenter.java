package com.restaurant.project.mikuyapp.signin;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.signin.ui.SignInView;

public interface SignInPresenter {
    void initSignIn(@NonNull String email, @NonNull String password);

    void attachView(@NonNull SignInView signInView);

    void dettachView();


    interface Callback {
        void onErrorService(@NonNull String message);

        void onFailure();

        void Success(@NonNull SignInResponseEntity signInResponseEntity);
    }
}
