package com.demoriderctg.arif.demorider.OnrideMode;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.GoogleMap.GetCurrentLocation;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;


import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.demoriderctg.arif.demorider.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Date;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.IGerRiderLocation;

import static android.support.v4.app.NotificationCompat.*;
import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.DEFAULT_ZOOM;
import static com.demoriderctg.arif.demorider.GoogleMap.MapActivity.contextOfApplication;

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
    private TextView bikeNumber;
    private RiderModel riderModel;
    private NotificationModel notificationModel;
    public static Activity OnrideModeContext;
    private float v;
    private double lat,lng;
    private TextView rider_phone_number;



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
        contactRider = (TextView) findViewById(R.id.contact_with_rider);
        rating = (TextView) findViewById(R.id.rider_rating);
        rider_phone_number =findViewById(R.id.rider_number);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        OnrideModeContext = this;

        if(notificationModel.riderId >0){
            AppConstant.RIDER_NAME = notificationModel.riderName;
            AppConstant.RIDER_PHONENUMBER = notificationModel.riderPhone;

        }
        initMap();
        setUi();

    }

    void setUi(){
        riderName.setText(AppConstant.RIDER_NAME);
        rating.setText("100");
        rider_phone_number.setText(AppConstant.RIDER_PHONENUMBER);
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
        if (connectionCheck.isNetworkConnected()) {

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
            mMap.setOnMyLocationButtonClickListener( OnrideModeActivity.this);

            setUpMap();

        }
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(OnrideModeActivity.this);
    }

    void setUpMap(){

        if(connectionCheck.isNetworkConnected()){
            try{

                mMap.addMarker(new MarkerOptions().position(AppConstant.DESTINATION).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
                mMap.addMarker(new MarkerOptions()
                        .position(AppConstant.SOURCE).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",400,300))).anchor(.5f,.5f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AppConstant.SOURCE, DEFAULT_ZOOM));
                MandatoryCall();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(OnrideModeActivity.this,MapActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    handlerForFinishRide.postDelayed(this, 1000);
                }


            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if(AppConstant.START_RIDE){
                    mMap.clear();
                    try{

                        mMap.addMarker(new MarkerOptions().position(AppConstant.DESTINATION).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
                        mMap.addMarker(new MarkerOptions()
                                .position(AppConstant.SOURCE).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",400,300))).anchor(.5f,.5f));

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
        handler.postDelayed(runnable, 2000);
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
                            .anchor(.5f,.5f)
                            .flat(true)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_bike",30,30))));

                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        v = valueAnimator.getAnimatedFraction();
                        lng = v*AppConstant.SOURCE.longitude+(1-v)*Longitude;
                        lat = v*AppConstant.SOURCE.latitude+(1-v)*Latitude;
                        LatLng newPos = new LatLng(Latitude,Longitude);
                        currentMarker.setPosition(newPos);
                        currentMarker.setAnchor(0.5f,0.5f);
                        currentMarker.setRotation(getBearing(newPos,AppConstant.SOURCE));
//                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
//                                new CameraPosition.Builder().
//                                        target(newPos)
//                                      .zoom(15.5f).build()));
                    }
                });
                valueAnimator.start();

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
                if(AppConstant.START_RIDE == false && AppConstant.FINISH_RIDE == false){
                    new AlertDialog.Builder(OnrideModeActivity.this)
                            .setTitle("Really Exit?")
                            .setMessage("Are you sure you want to Cancel the Ride?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    main.ForceCancelRide();
                                    Intent intent = new Intent(OnrideModeActivity.this, MapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).create().show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Can not cancel ride!!",Toast.LENGTH_SHORT).show();
                }

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

    @Override
    public void onBackPressed() {

    }


    @Override
    public void onStart() {
        super.onStart();
        AppConstant.ONRIDEMODE_ACTIVITY = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppConstant.ONRIDEMODE_ACTIVITY = false;
    }

    private float getBearing(LatLng startPos, LatLng newPos)
    {
        double lat = Math.abs(startPos.latitude - newPos.latitude);
        double lng = Math.abs(startPos.longitude - newPos.longitude);

        if(startPos.latitude < newPos.latitude && startPos.longitude < newPos.longitude){
            return (float) (Math.toDegrees(Math.atan(lng/lat)));
        }else if(startPos.latitude >= newPos.latitude && startPos.longitude < newPos.longitude){
            return (float) (90 -(Math.toDegrees(Math.atan(lng/lat)))+90);
        }else if(startPos.latitude >= newPos.latitude && startPos.longitude >= newPos.longitude){
            return (float) (90 -(Math.toDegrees(Math.atan(lng/lat)))+180);
        }else if(startPos.latitude < newPos.latitude && startPos.longitude >= newPos.longitude){
            return (float) (90 -(Math.toDegrees(Math.atan(lng/lat)))+270);
        }

        return -1;
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap decodeResource = BitmapFactory.decodeResource(this.getResources(),this.getResources().getIdentifier(iconName, "drawable", this.getPackageName()));
        return Bitmap.createScaledBitmap(decodeResource, (int) (((double) decodeResource.getWidth()) * .25d), (int) (((double) decodeResource.getHeight()) * .25d), false);
    }
}
