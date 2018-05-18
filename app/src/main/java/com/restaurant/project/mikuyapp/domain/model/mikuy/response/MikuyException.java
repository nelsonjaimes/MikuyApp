package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

public class MikuyException {
    private int status;
    private int code;
    private String message;
    private String moreInfo;
    private String developerMessage;

    public MikuyException() {
        message = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public static MikuyException parseError(ResponseBody responseBody) {
        Gson gson = new Gson();
        MikuyException mikuyException;
        try {
            mikuyException = gson.fromJson(responseBody.string(), MikuyException.class);
        } catch (IOException e) {
            e.printStackTrace();
            mikuyException = new MikuyException();
        }
        return mikuyException;
    }
}
