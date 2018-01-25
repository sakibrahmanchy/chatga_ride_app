package com.demoriderctg.arif.demorider.OnrideMode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.GoogleMap.GetCurrentLocation;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.demoriderctg.arif.demorider.OnLocationChange.GPS_Service;
import com.demoriderctg.arif.demorider.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.MissingFormatArgumentException;

import ContactWithFirebase.Main;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.IGerRiderLocation;

import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.DEFAULT_ZOOM;
import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.OnchangeDeviceLOcation;

public class OnrideModeActivity extends AppCompatActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener, GoogleMap.OnMyLocationChangeListener, IGerRiderLocation {

    GoogleMap mMap;
    private ConnectionCheck connectionCheck;
    private Marker sourceMarker;
    private Marker destinationMarker;
    private Marker currentMarker;
    private Handler handler = new Handler();
    private GetCurrentLocation  getCurrentLocation;
    private static int progress=0;
    UiSettings uiSettings;
    private Main main = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);
        connectionCheck = new ConnectionCheck(this);
        getCurrentLocation = new GetCurrentLocation(this);
        main = new Main();
        initMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            };

            uiSettings = googleMap.getUiSettings();
            uiSettings.setMapToolbarEnabled(false);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            mMap.setOnMyLocationButtonClickListener( OnrideModeActivity.this);

            setUpMap();
            Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(OnrideModeActivity.this);
    }

    void setUpMap(){

        if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
            // Getting URL to the Google Directions API

            try{
                sourceMarker= mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(AppConstant.SOURCE.latitude,AppConstant.SOURCE.longitude))
                        .title("Home")
                        .snippet(AppConstant.SOURCE_NAME)
                        .alpha(.7f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_location)));

                destinationMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(AppConstant.DESTINATION.latitude,AppConstant.DESTINATION.longitude))
                        .title("DESTINATION")
                        .snippet(AppConstant.DESTINATION_NAME)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_map)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AppConstant.SOURCE, DEFAULT_ZOOM));
                MandatoryCall();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else{
           // Toast.makeText(OnRideModeActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @SuppressLint("MissingPermission")
    private void MandatoryCall() {


      //  this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.mMap.setMyLocationEnabled(true);

        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(getCurrentLocation.getLatitude(),getCurrentLocation.getLongitude()))
                .title("IN RIDE")
                .alpha(AppConstant.DEFAULT_ZOOM)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_motorcycle)));

        //noinspection deprecation
        mMap.setOnMyLocationChangeListener(this);

    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
             if(location != null){
                 currentMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
             }
    }

    private void GetRiderCurrentLocation(){

        /*Request*/
        main.GetRiderLocation(FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider, this);
    }

    @Override
    public void OnGerRiderLocation(boolean value, double Latitude, double Longitude) {
        if(value == true){
            /*Do stuff*/
        }
    }
}
