package com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/28/2018.
 */

public class ApplyPromoCodeResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;


    public ApplyPromoCodeResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
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
}
