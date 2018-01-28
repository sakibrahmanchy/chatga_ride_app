package com.demoriderctg.arif.demorider.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class RideFinishHistory {

    @SerializedName("id")
    private int id;
    @SerializedName("ride_cost")
    private double rideCost;
    @SerializedName("ride_distance")
    private double rideDistance;

    public RideFinishHistory(int id, double rideCost, double rideDistance) {
        this.id = id;
        this.rideCost = rideCost;
        this.rideDistance = rideDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRideCost() {
        return rideCost;
    }

    public void setRideCost(double rideCost) {
        this.rideCost = rideCost;
    }

    public double getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(double rideDistance) {
        this.rideDistance = rideDistance;
    }
}
