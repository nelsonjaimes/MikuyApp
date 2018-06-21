package com.restaurant.project.mikuyapp.signup;

import android.content.Context;
import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.request.SignUpRequestEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpInteractorImp implements SignUpInteractor {
    private final Context context;

    public SignUpInteractorImp(Context context) {
        this.context = context;
    }

    @Override
    public void requestSignUpService(SignUpRequestEntity signUpRequestEntity,
                                     final SignUpPresenter.Callback callback) {

        ApiMikuyInterface mikuyInterface = ApiMikuyManager.getInstance(context);
        Call<SignInResponseEntity> call = mikuyInterface.requestSignUp(signUpRequestEntity);
        call.enqueue(new Callback<SignInResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponseEntity> call,
                                   @NonNull Response<SignInResponseEntity> response) {
                if (response.isSuccessful()) {
                    SignInResponseEntity signInResponseEntity = response.body();
                    callback.onSuccess(signInResponseEntity);
                }else{
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        MikuyException mikuyException = MikuyException.parseError(response, context);
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
}
