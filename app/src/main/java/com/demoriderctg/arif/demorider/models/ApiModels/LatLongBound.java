package com.demoriderctg.arif.demorider.models.ApiModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/21/2018.
 */

public class LatLongBound {
    @SerializedName("region_name")
    private String regionName;
    @SerializedName("north_latitude")
    private double northLatitude;
    @SerializedName("northLongitude")
    private double northLongitude;
    @SerializedName("south_latitude")
    private double southLatitude;
    @SerializedName("south_longitude")
    private double southLongitude;

    public LatLongBound(String regionName, double northLatitude, double northLongitude, double southLatitude, double southLongitude) {
        this.regionName = regionName;
        this.northLatitude = northLatitude;
        this.northLongitude = northLongitude;
        this.southLatitude = southLatitude;
        this.southLongitude = southLongitude;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public double getNorthLatitude() {
        return northLatitude;
    }

    public void setNorthLatitude(double northLatitude) {
        this.northLatitude = northLatitude;
    }

    public double getNorthLongitude() {
        return northLongitude;
    }

    public void setNorthLongitude(double northLongitude) {
        this.northLongitude = northLongitude;
    }

    public double getSouthLatitude() {
        return southLatitude;
    }

    public void setSouthLatitude(double southLatitude) {
        this.southLatitude = southLatitude;
    }

    public double getSouthLongitude() {
        return southLongitude;
    }

    public void setSouthLongitude(double southLongitude) {
        this.southLongitude = southLongitude;
    }
}
