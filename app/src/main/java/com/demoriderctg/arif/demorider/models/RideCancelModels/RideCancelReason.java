package com.demoriderctg.arif.demorider.models.RideCancelModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 4/19/2018.
 */

public class RideCancelReason {

    @SerializedName("id")
    private String id;
    @SerializedName("cancel_reason")
    private String cancelReason;

    public RideCancelReason(String id, String cancelReason) {
        this.id = id;
        this.cancelReason = cancelReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
