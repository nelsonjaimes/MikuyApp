package com.restaurant.project.mikuyapp.signup;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignUpResponseEntity;
import com.restaurant.project.mikuyapp.signup.ui.SignUpView;
import com.restaurant.project.mikuyapp.utils.LogUtil;

public class SignUpPresenterImp implements SignUpPresenter, SignUpPresenter.Callback {
    private SignUpView signUpView;
    private SignUpInteractor signUpInteractor;

    public SignUpPresenterImp() {
        signUpInteractor = new SignUpInteractorImp();
    }

    @Override
    public void attachView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void initRegister(@NonNull String name, @NonNull String lastName, @NonNull String email,
                             char gender, @NonNull String password) {
        SignUpRequestEntity signUpRequestEntity = new SignUpRequestEntity();
        signUpRequestEntity.setName(name);
        signUpRequestEntity.setEmail(email);
        signUpRequestEntity.setLastname(lastName);
        signUpRequestEntity.setGender(gender);
        signUpRequestEntity.setPassword(password);
        if (signUpInteractor != null) {
            signUpInteractor.requestSignUpService(signUpRequestEntity, this);
        }
    }

    @Override
    public void dettachView() {
        signUpView = null;
    }

    @Override
    public void onErrorService(@NonNull String message) {

    }

    @Override
    public void onSuccess(SignUpResponseEntity signUpResponseEntity) {
        LogUtil.d(signUpResponseEntity.getStatus());
        if (signUpView != null) {
            signUpView.onSucessSignUp();
        }
    }

    @Override
    public void onFailure() {
        LogUtil.d("onFailure");
    }
}
