package com.demoriderctg.arif.demorider.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class ClientHistory {

    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("distance")
    private String distance;
    @SerializedName("time")
    private String time;
    @SerializedName("pick_point_address")
    private String pickPointAddress;
    @SerializedName("destination_address")
    private String destinationAddress;
    @SerializedName("total_fare")
    private String totalFare;
    @SerializedName("rider_id")
    private int riderId;
    @SerializedName("rider_name")
    private String riderName;
    @SerializedName("rider_avatar")
    private String riderAvatar;
    @SerializedName("promotion")
    private String promotion;
    @SerializedName("transaction_id")
    private String transactionId;
    @SerializedName("rating")
    private float rating;

    public ClientHistory(String dateTime, String distance, String pickPointAddress, String destinationAddress, String totalFare, int riderId,
                         String riderName, String riderAvatar, String promotion, String time, String transactionId,float rating) {
        this.dateTime = dateTime;
        this.distance = distance;
        this.pickPointAddress = pickPointAddress;
        this.destinationAddress = destinationAddress;
        this.totalFare = totalFare;
        this.riderId = riderId;
        this.riderName = riderName;
        this.riderAvatar = riderAvatar;
        this.promotion = promotion;
        this.time = time;
        this.transactionId = transactionId;
        this.rating = rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderAvatar() {
        return riderAvatar;
    }

    public void setRiderAvatar(String riderAvatar) {
        this.riderAvatar = riderAvatar;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
