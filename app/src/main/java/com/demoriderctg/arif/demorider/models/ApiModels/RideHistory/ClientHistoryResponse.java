package com.demoriderctg.arif.demorider.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class ClientHistoryResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<ClientHistory> data;


    public ClientHistoryResponse(boolean success, String message, ArrayList<ClientHistory> data) {
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

    public ArrayList<ClientHistory> getData() {
        return data;
    }

    public void setData(ArrayList<ClientHistory> data) {
        this.data = data;
    }
}
