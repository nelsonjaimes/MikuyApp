package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.Gson;
import com.restaurant.project.mikuyapp.MikuyApplication;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;

import okhttp3.ResponseBody;

public class MikuyException {
    private String message;

    private MikuyException() {
        message = MikuyApplication.contextApp.getString(R.string.errorConnectionServer,
                MikuyPreference.getUrlBaseServer());
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static MikuyException parseError(ResponseBody responseBody) {
        MikuyException mikuyException = null;
        try {
            if (responseBody.string() != null) {
                mikuyException = new Gson().fromJson(responseBody.string(), MikuyException.class);
                if (mikuyException == null) {
                    mikuyException = new MikuyException();
                }
            }
        } catch (Exception e) {
            mikuyException = new MikuyException();
        }
        return mikuyException;
    }
}
