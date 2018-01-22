package com.demoriderctg.arif.demorider.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class ClientHistory {

    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("distance_time")
    private String distanceTime;
    @SerializedName("pick_point_address")
    private String pickPointAddress;
    @SerializedName("destination_address")
    private String destinationAddress;
    @SerializedName("total_fare")
    private String totalFare;

    public ClientHistory(String dateTime, String distanceTime, String pickPointAddress, String destinationAddress, String totalFare) {
        this.dateTime = dateTime;
        this.distanceTime = distanceTime;
        this.pickPointAddress = pickPointAddress;
        this.destinationAddress = destinationAddress;
        this.totalFare = totalFare;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getPickPointAddress() {
        return pickPointAddress;
    }

    public void setPickPointAddress(String pickPointAddress) {
        this.pickPointAddress = pickPointAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }
}
