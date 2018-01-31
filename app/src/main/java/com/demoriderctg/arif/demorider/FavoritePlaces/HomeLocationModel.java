package com.demoriderctg.arif.demorider.FavoritePlaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Arif on 2/1/2018.
 */

public class HomeLocationModel {
    private LatLng home = null;

    public void setHome(LatLng home) {
        this.home = home;
    }

    public void setHomeLocationName(String homeLocationName) {
        this.homeLocationName = homeLocationName;
    }

    private String homeLocationName =null;

    public LatLng getHome() {
        return home;
    }

    public String getHomeLocationName() {
        return homeLocationName;
    }



    public HomeLocationModel(LatLng home, LatLng work, String homeLocationName, String workLocationName) {
        this.home = home;
        this.homeLocationName = homeLocationName;
    }
}
