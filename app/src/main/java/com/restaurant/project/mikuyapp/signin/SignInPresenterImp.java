package com.restaurant.project.mikuyapp.signin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignInRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.signin.ui.SignInView;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.Operations;

public class SignInPresenterImp implements SignInPresenter, SignInPresenter.Callback {

    private final Context context;
    private SignInView signInView;
    private final SignInInteractor signInInteractor;

    public SignInPresenterImp(Context context) {
        this.context = context;
        this.signInInteractor = new SignInInteractorImp(context);
    }

    @Override
    public void attachView(@NonNull SignInView signInView) {
        this.signInView = signInView;
    }

    @Override
    public void initSignIn(@NonNull String email, @NonNull String password) {
        if (signInView == null || signInInteractor == null) return;
        String message = get(R.string.emptyEmail);
        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                if (password.length() < 6) {
                    signInView.showSnackBar(get(R.string.shortPassword));
                    return;
                }
                if (!Operations.isNetworkAvailable(context)) {
                    signInView.showSnackBar(get(R.string.errorNetwoork));
                    return;
                }
                SignInRequestEntity signInRequestEntity = new SignInRequestEntity();
                signInRequestEntity.setEmail(email);
                signInRequestEntity.setPassword(password);
                signInView.showProgress();
                signInInteractor.requestSignInService(signInRequestEntity, this);
                return;
            }
            message = get(R.string.emptyPassword);
        }
        signInView.showSnackBar(message);
    }

    @Override
    public void dettachView() {
        signInInteractor.onCancel();
        signInView.hideProgress();
        signInView = null;
    }

    @Override
    public void onErrorService(@NonNull String message) {
        if (signInView != null) {
            signInView.hideProgress();
            signInView.showSnackBar(message);
        }
    }

    @Override
    public void onFailure() {
        if (signInView != null) {
            signInView.hideProgress();
            signInView.showSnackBar(context.getResources().
                    getString(R.string.errorConnectionServer,
                            MikuyPreference.getUrlBaseServer(context)));
        }
    }

    @Override
    public void Success(@NonNull SignInResponseEntity signInResponseEntity) {
        if (signInView != null) {
            MikuyPreference.saveUserSession(context, signInResponseEntity);
            signInView.hideProgress();
            signInView.onSucessSignIn();
        }
    }

    private String get(@StringRes int idString) {
        return context.getResources().getString(idString);
    }
}
