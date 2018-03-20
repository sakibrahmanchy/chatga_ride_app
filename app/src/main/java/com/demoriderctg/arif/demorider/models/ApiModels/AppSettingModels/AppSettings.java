package com.demoriderctg.arif.demorider.models.ApiModels.AppSettingModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/21/2018.
 */

public class AppSettings {

    @SerializedName("base_fare")
    private double baseFare;
    @SerializedName("price_per_km")
    private double pricePerKm;
    @SerializedName("price_per_min")
    private double pricePerMinute;
    @SerializedName("minimum_fare")
    private double minimumFare;
    @SerializedName("partner_percentage")
    private double partnerPercentage;

    public AppSettings(double baseFare, double pricePerKm, double pricePerMinute, double minimumFare, double partnerPercentage) {
        this.baseFare = baseFare;
        this.pricePerKm = pricePerKm;
        this.pricePerMinute = pricePerMinute;
        this.minimumFare = minimumFare;
        this.partnerPercentage = partnerPercentage;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public double getMinimumFare() {
        return minimumFare;
    }

    public void setMinimumFare(double minimumFare) {
        this.minimumFare = minimumFare;
    }

    public double getPartnerPercentage() {
        return partnerPercentage;
    }

    public void setPartnerPercentage(double partnerPercentage) {
        this.partnerPercentage = partnerPercentage;
    }
}

