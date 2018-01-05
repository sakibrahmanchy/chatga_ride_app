package com.demoriderctg.arif.demorider.GoogleMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Arif on 12/27/2017.
 */

public class MapMarkerDragging {

    private Context mContext;
    private LatLng mSource;
    private LatLng mDestination;
    private GoogleMap mMap;
    private MapActivity  mapActivity;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor ;
    private String currentLocation;

    public MapMarkerDragging(Context mContext, LatLng mSource, LatLng mDestination, GoogleMap mMap) {
        this.mContext = mContext;
        this.mDestination=mDestination;
        this.mSource=mSource;
        this.mMap = mMap;
        mapActivity = new MapActivity();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.editor = sharedpreferences.edit();
        Init();

    }



    void Init(){
       mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

           @Override
           public void onMarkerDragStart(Marker marker) {
               // TODO Auto-generated method stub
               // Here your code
               Toast.makeText(mContext, "Dragging Start",
                       Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onMarkerDragEnd(Marker marker) {
               // TODO Auto-generated method stub
               String markerOption = marker.getSnippet();
               Geocoder myLocation = new Geocoder(mContext, Locale.getDefault());
               try {
                   List<Address> myList = myLocation.getFromLocation(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude(), 1);
                   Address address = (Address) myList.get(0);
                   currentLocation = address.getAddressLine(0);

               } catch (IOException e) {
                   e.printStackTrace();
               }
               if(markerOption.equals("Home")){
                   editor.putFloat("lats", (float) mMap.getMyLocation().getLatitude());
                   editor.putFloat("lons", (float)mMap.getMyLocation().getLongitude());
                   editor.putString("locationName", currentLocation);
                   editor.commit();
               }
               else{
                   editor.putFloat("latd", (float)mMap.getMyLocation().getLatitude());
                   editor.putFloat("lond", (float)mMap.getMyLocation().getLongitude());
                   editor.putString("locationNamed", currentLocation);
                   editor.commit();
               }
             //  mapActivity.checkLatLon();
           }

           @Override
           public void onMarkerDrag(Marker marker) {
               // TODO Auto-generated method stub
               // Toast.makeText(MainActivity.this, "Dragging",
               // Toast.LENGTH_SHORT).show();
               System.out.println("Draagging");
           }
       });
   }

}
