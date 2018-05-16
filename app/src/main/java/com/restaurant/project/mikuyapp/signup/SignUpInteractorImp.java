package com.restaurant.project.mikuyapp.signup;

import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignUpResponseEntity;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpInteractorImp implements SignUpInteractor {
    @Override
    public void requestSignUpService(SignUpRequestEntity signUpRequestEntity,
                                     final SignUpPresenter.Callback callback) {

        ApiMikuyInterface mikuyInterface = ApiMikuyManager.getInstance();
        Call<SignUpResponseEntity> call = mikuyInterface.requestSignUp(signUpRequestEntity);
        call.enqueue(new Callback<SignUpResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponseEntity> call,
                                   @NonNull Response<SignUpResponseEntity> response) {
                LogUtil.d("entro....");
                if (response.isSuccessful()) {
                    LogUtil.d("entro en isSuccessful");
                    SignUpResponseEntity signUpResponseEntity = response.body();
                    callback.onSuccess(signUpResponseEntity);
                }else{
                    LogUtil.d("entro2....");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponseEntity> call,
                                  @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });

    }
}
