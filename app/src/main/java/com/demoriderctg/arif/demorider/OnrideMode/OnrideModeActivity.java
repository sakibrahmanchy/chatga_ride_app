package com.demoriderctg.arif.demorider.OnrideMode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogInRideMode;
import com.demoriderctg.arif.demorider.Dailog.RideFinishDailog;
import com.demoriderctg.arif.demorider.Dailog.SearchingDriver;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.GoogleMap.GetCurrentLocation;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.MainActivity;
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
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.IGerRiderLocation;

import static android.support.v4.app.NotificationCompat.*;
import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.DEFAULT_ZOOM;
import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.OnchangeDeviceLOcation;

public class OnrideModeActivity extends AppCompatActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener, GoogleMap.OnMyLocationChangeListener, IGerRiderLocation {

    GoogleMap mMap;
    private ConnectionCheck connectionCheck;
    private Marker sourceMarker;
    private Marker destinationMarker;
    private Marker currentMarker ;
    private Handler handler = new Handler();
    private Handler handlerForFinishRide = new Handler();
    private GetCurrentLocation  getCurrentLocation;
    private static int progressStatus=1;
    private Builder notification;
    private Notification note;
    private NotificationManager notificationManager;
    UiSettings uiSettings;
    private Main main = new Main();
    private SendNotification sendNotification;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private ImageView riderImage;
    private TextView riderName;
    private  TextView contactRider;
    private  TextView rating;
    private RiderModel riderModel;
    private NotificationModel notificationModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);
        connectionCheck = new ConnectionCheck(this);
        getCurrentLocation = new GetCurrentLocation(this);
        sendNotification = new SendNotification(this);
        notification = new NotificationCompat.Builder(this);
        riderImage = (ImageView) findViewById(R.id.Rider_profile_pic);
        riderName = (TextView) findViewById(R.id.rider_name);
        contactRider = (TextView) findViewById(R.id.rider_number);
        rating = (TextView) findViewById(R.id.rider_rating);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        if(notificationModel==null){
            AppConstant.RIDER_NAME = notificationModel.riderName;
            AppConstant.RIDER_PHONENUMBER = notificationModel.riderPhone;
        }
        initMap();
        setUi();

    }

    void setUi(){
        riderName.setText(AppConstant.RIDER_NAME);
        rating.setText("100");
        contactRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

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
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_map)));

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
        //noinspection deprecation
        mMap.setOnMyLocationChangeListener(this);

        Runnable runnableForStartRide = new Runnable() {
            @Override
            public void run() {

                if(AppConstant.FINISH_RIDE){
                    RideFinishDailog rideFinishDailog = new RideFinishDailog(OnrideModeActivity.this);
                    rideFinishDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    rideFinishDailog.show();
                }
                else {
                    handlerForFinishRide.postDelayed(this, 3000);
                }


            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if(AppConstant.START_RIDE){
                    mMap.clear();
                    try{
                        sourceMarker= mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(AppConstant.SOURCE.latitude,AppConstant.SOURCE.longitude))
                                .title("Home")
                                .snippet(AppConstant.SOURCE_NAME)
                                .alpha(.7f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_map)));

                        destinationMarker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(AppConstant.DESTINATION.latitude,AppConstant.DESTINATION.longitude))
                                .title("DESTINATION")
                                .snippet(AppConstant.DESTINATION_NAME)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_map)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AppConstant.SOURCE, DEFAULT_ZOOM));
                        handlerForFinishRide.postDelayed(runnableForStartRide,3000);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    GetRiderCurrentLocation();
                    handler.postDelayed(this, 3000);
                }


            }
        };
        handler.postDelayed(runnable, 3000);
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        /*
             if(location != null){
                 currentMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
             }
             */
    }

    private void GetRiderCurrentLocation(){

        /*Request*/
        main.GetRiderLocation(FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider, this);
    }

    @Override
    public void OnGerRiderLocation(boolean value, double Latitude, double Longitude) {
        if(value == true){
            /*Do stuff*/

            try {
                if(currentMarker == null){

                    currentMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(AppConstant.SOURCE.latitude,AppConstant.SOURCE.longitude))
                            .title("RIDER")
                            .alpha(AppConstant.DEFAULT_ZOOM)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_motorcycle)));
                }
                currentMarker.setPosition(new LatLng(Latitude,Longitude));
            }catch(Exception e){
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.onridemode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cancel_ride:
                main.ForceCancelRide();
                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0"+AppConstant.RIDER_PHONENUMBER)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}
