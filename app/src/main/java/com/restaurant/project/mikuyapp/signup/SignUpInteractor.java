package com.restaurant.project.mikuyapp.signup;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;

interface SignUpInteractor {
    void requestSignUpService(SignUpRequestEntity signUpRequestEntity,
                              SignUpPresenter.Callback callback);

}
