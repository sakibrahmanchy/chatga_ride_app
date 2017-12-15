package com.demoriderctg.arif.demorider;

/**
 * Created by Arif on 11/21/2017.
 */

public class SearchHistory {
    String Place;
    String searchLocation;
    int photoId;

    public  SearchHistory(String Place, String searchLocation, int photoId) {
        this.Place = Place;
        this.searchLocation = searchLocation;
        this.photoId = photoId;
    }
}
