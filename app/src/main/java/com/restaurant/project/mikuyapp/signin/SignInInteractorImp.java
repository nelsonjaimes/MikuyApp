package com.restaurant.project.mikuyapp.signin;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignInRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInInteractorImp implements SignInInteractor {
    private static Call<SignInResponseEntity> requestSignIn;
    @Override
    public void requestSignInService(@NonNull SignInRequestEntity signInRequestEntity,
                                     final SignInPresenter.Callback callback) {

        ApiMikuyInterface apiMikuyInterface = ApiMikuyManager.getInstance();
        requestSignIn = apiMikuyInterface.requestSignIn(signInRequestEntity);
        requestSignIn.enqueue(new Callback<SignInResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponseEntity> call,
                                   @NonNull Response<SignInResponseEntity> response) {
                if (response.isSuccessful()) {
                    SignInResponseEntity signInResponseEntity = response.body();
                    if (signInResponseEntity != null) {
                        if (callback != null) callback.Success(signInResponseEntity);
                    }
                } else {
                    MikuyException mikuyException = MikuyException.parseError(response);
                        if (callback != null) {
                            callback.onErrorService(mikuyException.getMessage());
                        }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignInResponseEntity> call,
                                  @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }

    @Override
    public void onCancel() {
        if (requestSignIn != null && requestSignIn.isExecuted()) {
            requestSignIn.cancel();
        }
    }
}
