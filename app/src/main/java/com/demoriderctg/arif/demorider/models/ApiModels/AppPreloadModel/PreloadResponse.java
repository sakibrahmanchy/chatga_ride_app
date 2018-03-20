package com.demoriderctg.arif.demorider.models.ApiModels.AppPreloadModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/21/2018.
 */

public class PreloadResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    PreloadData data;

    public PreloadResponse(boolean success, String message, PreloadData data) {
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

    public PreloadData getData() {
        return data;
    }

    public void setData(PreloadData data) {
        this.data = data;
    }
}
