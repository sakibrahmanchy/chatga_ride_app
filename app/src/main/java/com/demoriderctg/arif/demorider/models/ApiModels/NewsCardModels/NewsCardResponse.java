package com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 3/20/2018.
 */

public class NewsCardResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<NewsCard> data;

    public NewsCardResponse(boolean success, String message,  ArrayList<NewsCard> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public  ArrayList<NewsCard> getData() {
        return data;
    }

    public void setData( ArrayList<NewsCard> data) {
        this.data = data;
    }
}
