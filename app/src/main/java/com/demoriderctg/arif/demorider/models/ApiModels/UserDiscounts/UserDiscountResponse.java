package com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 1/27/2018.
 */

public class UserDiscountResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<UserDiscounts> data;

    public UserDiscountResponse(boolean success, String message, ArrayList<UserDiscounts> data) {
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

    public ArrayList<UserDiscounts> getData() {
        return data;
    }

    public void setData(ArrayList<UserDiscounts> data) {
        this.data = data;
    }
}


