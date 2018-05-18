package com.restaurant.project.mikuyapp.signin;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignInRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInInteractorImp implements SignInInteractor {
    @Override
    public void requestSignInService(@NonNull SignInRequestEntity signInRequestEntity,
                                     final SignInPresenter.Callback callback) {

        ApiMikuyInterface apiMikuyInterface = ApiMikuyManager.getInstance();
        Call<SignInResponseEntity> call = apiMikuyInterface.requestSignIn(signInRequestEntity);
        call.enqueue(new Callback<SignInResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponseEntity> call,
                                   @NonNull Response<SignInResponseEntity> response) {
                if (response.isSuccessful()) {
                    SignInResponseEntity signInResponseEntity = response.body();
                    if (signInResponseEntity != null) {
                        if (callback != null) callback.Success(signInResponseEntity);
                    }
                } else {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        MikuyException mikuyException = MikuyException.parseError(responseBody);
                        if (callback != null) callback.onErrorService(mikuyException.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignInResponseEntity> call,
                                  @NonNull Throwable t) {
                if (callback != null) callback.onFailure();
            }
        });
    }
}
