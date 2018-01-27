package com.demoriderctg.arif.demorider.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/25/2018.
 */

public class ClientHistorySpecific {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ClientHistory data;
}
