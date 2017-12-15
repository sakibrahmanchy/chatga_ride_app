package com.demoriderctg.arif.demorider;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ContactWithFirebase.Main;
import __Firebase.FirebaseWrapper;


/**
 * Created by User on 10/2/2017.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    ArrayList markerPoints= new ArrayList();
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            //  getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
            Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(54.69726685890506,-2.7379201682812226), new LatLng(55.38942944437183, -1.2456105979687226));
    String CurrentLocation;


    //widgets

    private Button sendButton;
    private ImageView mGps;
    private  ImageView searchSources;
    private  ImageView searchDestination;
    private Button requestbtn;
    private RelativeLayout relativeLayoutforSource;
    private RelativeLayout relativeLayoutforDestination;
    private  TextView sourceText;
    private  TextView destinationText;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseWrapper firebaseWrapper = null;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private  String distance="arif";
    private  String duration="arif";
    private LatLng source,dest;
    private  final int SOURCE =1;
    private  final int DESTINATION =2;
    private String MycurrentLocation;
    private  LatLng home,workplace;
    private ProgressBar spinner;
    private double latS,lons;
    MarkerOptions options = new MarkerOptions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

       drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
       actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);;

        sourceText = (TextView) findViewById(R.id.sourceText);
        destinationText =(TextView) findViewById(R.id.destinationText);

        mGps = (ImageView) findViewById(R.id.ic_gps);
        sendButton = (Button) findViewById(R.id.btnSend);
        requestbtn = (Button) findViewById(R.id.pickupbtn);
        sendButton.setVisibility(View.INVISIBLE);
        requestbtn.setVisibility(View.INVISIBLE);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //   sendButton.setVisibility(View.INVISIBLE);

        getLocationPermission();
       String x = getIntent().getStringExtra("locationName");
        if(x !=null){
             x = getIntent().getStringExtra("SearchLocation");
            onActivityResult1(x);
        }

    }

    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();



        //mSearchTextDestination.setOnItemClickListener(mAutocompleteClickListenerForDestination);
       // mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        sourceText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(MapActivity.this, autoComplete.class);
                i.putExtra("From","from");
                startActivityForResult(i, 1);

                return false;
            }
        });

        destinationText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(MapActivity.this, autoComplete.class);
                i.putExtra("From","to");
                startActivity(i);

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(source, dest);

                DownloadTask downloadTask = new DownloadTask(mMap,source,dest);
                DownloadTask2 downloadTask2 = new DownloadTask2(source,dest);
                String a = downloadTask2.getDestinatin();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
                sendButton.setVisibility(View.INVISIBLE);
                requestbtn.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                //mMap.getUiSettings().setScrollGesturesEnabled(false);
            }
        });





        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                requestbtn.setVisibility(View.INVISIBLE);
                mGps.setVisibility(View.INVISIBLE);
                mMap.getUiSettings().setScrollGesturesEnabled(false);

                Double SourceLat = source.latitude;
                Double SourceLan = source.longitude;
                Double DestinationLat = dest.latitude;
                Double DestinationLan = source.longitude;

               Pair Source = Pair.create(SourceLat,SourceLan);
               Pair Destination = Pair.create(DestinationLat,DestinationLan);
               Main main = new Main();
              // main.RequestForRide(Source, Destination);

            }
        });

        hideSoftKeyboard();
    }

    private void geoLocate(int position){
        Log.d(TAG, "geoLocate: geolocating");
        Toast.makeText(getApplicationContext(),"dokche",Toast.LENGTH_LONG).show();
        if(position==1){
            String searchString = sourceText.getText().toString();

            Geocoder geocoder = new Geocoder(MapActivity.this);
            List<Address> list = new ArrayList<>();
            try{
                list = geocoder.getFromLocationName(searchString, 1);
            }catch (IOException e){
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
            }

            if(list.size() > 0){
                Address address = list.get(0);
                sourceText.setText("");
                sourceText.setText(address.getAddressLine(0));
                home = new LatLng(address.getLatitude(), address.getLongitude());
                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                        address.getAddressLine(0));
            }
        }

        else if(position==2){
            String searchString = sourceText.getText().toString();

            Geocoder geocoder = new Geocoder(MapActivity.this);
            List<Address> list = new ArrayList<>();
            try{
                list = geocoder.getFromLocationName(searchString, 1);
            }catch (IOException e){
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
            }

            if(list.size() > 0){
                Address address = list.get(0);

                Log.d(TAG, "geoLocate: found a location: " + address.toString());
                //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
                workplace = new LatLng(address.getLatitude(), address.getLongitude());
                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                        address.getAddressLine(0));
            }
        }

    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            Geocoder myLocation = new Geocoder(MapActivity.this, Locale.getDefault());
                            try {
                                List<Address> myList = myLocation.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                Address address = (Address) myList.get(0);
                                MycurrentLocation = address.getAddressLine(0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            source= home = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            markerPoints.add(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void onActivityResult1(String activity) {
        if (activity.equals("from")) {
            // TODO Extract the data returned from the child Activity.
            latS =  getIntent().getDoubleExtra("lat", 0.00);

            String locationName = getIntent().getStringExtra("locationName");
            lons =  getIntent().getDoubleExtra("lon", 0.00);
            source = new LatLng(latS,lons);
            sourceText.setText(locationName);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putFloat("lats", (float)latS);
            editor.putFloat("lons", (float)lons);
            editor.putString("locationName", locationName);
            editor.commit();
            checkLatLon();
        }
        if (activity.equals("to")) {
            // TODO Extract the data returned from the child Activity.
            latS =  getIntent().getDoubleExtra("lat", 0.00);

            String locationName = getIntent().getStringExtra("locationName");
            lons =  getIntent().getDoubleExtra("lon", 0.00);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putFloat("latd", (float)latS);
            editor.putFloat("lond", (float)lons);
            editor.putString("locationNamed", locationName);
            editor.commit();
          //  destinationText.setText(locationName);
            checkLatLon();
        }
    }

    void  checkLatLon(){
        String location = sharedpreferences.getString("locationName", "");
        double lats = sharedpreferences.getFloat("lats", (float) 0.0);
        double lons = sharedpreferences.getFloat("lons", (float) 0.0);
        String locationd = sharedpreferences.getString("locationNamed", "");
        double latd = sharedpreferences.getFloat("latd", (float) 0.0);
        double lond = sharedpreferences.getFloat("lond", (float) 0.0);

        if(lats !=0.0){
            sourceText.setText("");
            sourceText.setText(location);
            source = new LatLng(latS,lons);
        }
        if(latd !=0.0){
            destinationText.setText(locationd);
            dest = new LatLng(latd,lond);
        }
        if(source !=null && dest !=null){
            sendButton.setVisibility(View.VISIBLE);
        }
        else {
            sendButton.setVisibility(View.INVISIBLE);
        }
    }




    //Menu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}










