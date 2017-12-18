package com.demoriderctg.arif.demorider.models.ApiModels;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.google.gson.annotations.SerializedName;


public class AuthToken {
    @SerializedName("access_token")
    private String accessToken;

    public AuthToken(String userId) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String userId) {
        this.accessToken = accessToken;
    }
}