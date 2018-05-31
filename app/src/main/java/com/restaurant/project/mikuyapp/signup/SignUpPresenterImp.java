package com.restaurant.project.mikuyapp.signup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.signup.ui.SignUpView;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.Operations;

public class SignUpPresenterImp implements SignUpPresenter, SignUpPresenter.Callback {
    private SignUpView signUpView;
    private SignUpInteractor signUpInteractor;
    private Context context;

    public SignUpPresenterImp(Context context) {
        signUpInteractor = new SignUpInteractorImp();
        this.context = context;
    }

    @Override
    public void attachView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void initRegister(@NonNull String name, @NonNull String lastName, @NonNull String email,
                             char gender, @NonNull String password) {
        if (signUpView == null || signUpInteractor == null) return;
        String message = get(R.string.emptyName);
        if (!name.isEmpty()) {
            if (!lastName.isEmpty()) {
                if (!String.valueOf(gender).isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                            if (password.length() >= 6) {
                                if (!Operations.isNetworkAvailable(context)) {
                                    signUpView.showSnackBar(get(R.string.errorNetwoork));
                                    return;
                                }
                                SignUpRequestEntity signUpRequestEntity = new SignUpRequestEntity();
                                signUpRequestEntity.setName(name);
                                signUpRequestEntity.setEmail(email);
                                signUpRequestEntity.setLastname(lastName);
                                signUpRequestEntity.setGender(gender);
                                signUpRequestEntity.setPassword(password);
                                signUpView.showProgress();
                                signUpInteractor.requestSignUpService(signUpRequestEntity, this);
                                return;
                            } else {
                                signUpView.showSnackBar(get(R.string.shortPassword));
                                return;
                            }
                        }
                        signUpView.showSnackBar(get(R.string.emptyPassword));
                        return;
                    }
                    signUpView.showSnackBar(get(R.string.emptyEmail));
                    return;
                }
                signUpView.showSnackBar(get(R.string.emptyGender));
                return;
            }
            message = get(R.string.emptyLastName);
        }
        signUpView.showSnackBar(message);
    }

    @Override
    public void dettachView() {
        signUpView = null;
    }

    @Override
    public void onErrorService(@NonNull String message) {
        if (signUpView != null) {
            signUpView.hideProgress();
            signUpView.showSnackBar(message);
        }
    }

    @Override
    public void onSuccess(SignInResponseEntity signInResponseEntity) {
        if (signUpView != null) {
            MikuyPreference.saveUserSession(signInResponseEntity);
            signUpView.hideProgress();
            signUpView.onSucessSignUp();
        }
    }

    @Override
    public void onFailure() {
        if (signUpView != null) {
            signUpView.hideProgress();
            signUpView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer, MikuyPreference.getUrlBaseServer()));
        }
    }

    private String get(@StringRes int idString) {
        return context.getResources().getString(idString);
    }
}
