package com.demoriderctg.arif.demorider.FavoritePlaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Arif on 2/1/2018.
 */

public class WorkLocationModel {
    public LatLng getWork() {
        return work;
    }

    public void setWork(LatLng work) {
        this.work = work;
    }

    public String getWorkLocationName() {
        return workLocationName;
    }

    public void setWorkLocationName(String workLocationName) {
        this.workLocationName = workLocationName;
    }

    private LatLng work = null;
    private String workLocationName=null;

    public WorkLocationModel(LatLng work, String workLocationName) {
        this.work = work;
        this.workLocationName = workLocationName;
    }
}
