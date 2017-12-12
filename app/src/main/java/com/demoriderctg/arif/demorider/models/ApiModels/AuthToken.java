package com.demoriderctg.arif.demorider.models.ApiModels;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class AuthToken {


    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private AccessTokenData data;

    @SerializedName("message")
    private String message;

    public AuthToken(Context context, AccessTokenData data, String status, String message) {

        this.data = data;
        this.status = status;
        this.message = message;
    }

    public AccessTokenData getdata() {
        return data;
    }

    public void setdata(AccessTokenData data) {
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}


