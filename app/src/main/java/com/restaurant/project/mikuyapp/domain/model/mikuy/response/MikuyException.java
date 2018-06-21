package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import android.content.Context;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MikuyException {
    private final String message;
    private final String developerMessage;

    private MikuyException(Context context, String developerMessage) {
        message = context.getString(R.string.errorConnectionServer,
                MikuyPreference.getUrlBaseServer(context));
        this.developerMessage = developerMessage;
    }

    private String getDeveloperMessage() {
        return developerMessage;
    }

    public String getMessage() {
        return message;
    }

    public static MikuyException parseError(retrofit2.Response response, Context context) {
        MikuyException mikuyException;
        try {
            Converter<ResponseBody, MikuyException> errorConverter = ApiMikuyManager.getRetrofit().
                    responseBodyConverter(MikuyException.class, new Annotation[0]);
            mikuyException = errorConverter.convert(response.errorBody());
            LogUtil.d(mikuyException.getDeveloperMessage());
        } catch (Exception e) {
            mikuyException = new MikuyException(context, e.getMessage());
        }
        return mikuyException;
    }
}
