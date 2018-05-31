package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.restaurant.project.mikuyapp.MikuyApplication;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MikuyException {
    private String message;
    private String developerMessage;

    private MikuyException() {
        message = MikuyApplication.contextApp.getString(R.string.errorConnectionServer,
                MikuyPreference.getUrlBaseServer());
    }

    private String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static MikuyException parseError(retrofit2.Response response) {
        MikuyException mikuyException;
        try {
            Converter<ResponseBody, MikuyException> errorConverter = ApiMikuyManager.getRetrofit().
                    responseBodyConverter(MikuyException.class, new Annotation[0]);
            mikuyException = errorConverter.convert(response.errorBody());
            LogUtil.d(mikuyException.getDeveloperMessage());
        } catch (Exception e) {
            mikuyException = new MikuyException();
        }
        return mikuyException;
    }
}
