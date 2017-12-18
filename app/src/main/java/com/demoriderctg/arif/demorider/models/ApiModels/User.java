package com.demoriderctg.arif.demorider.models.ApiModels;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class User {
    @SerializedName("id")
    private String userId;



    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}