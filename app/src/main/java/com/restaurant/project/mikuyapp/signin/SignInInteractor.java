package com.restaurant.project.mikuyapp.signin;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignInRequestEntity;

public interface SignInInteractor {
    void requestSignInService(@NonNull SignInRequestEntity signInRequestEntity,
                              SignInPresenter.Callback callback);

    void onDestroy();
}
