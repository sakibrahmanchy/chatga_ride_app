package com.demoriderctg.arif.demorider.GoogleMap;



import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.DownloadTask2;
import com.demoriderctg.arif.demorider.FavoritePlaces.FavoritePlacesActivity;
import com.demoriderctg.arif.demorider.PlaceAutocompleteAdapter;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.autoComplete;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ContactWithFirebase.Main;


/**
 * Created by User on 10/2/2017.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    ArrayList markerPoints= new ArrayList();
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;


        if (mLocationPermissionsGranted) {
            //getDeviceLocation();

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
    private String MycurrentLocation,phonemumber;
    private  LatLng home,workplace;
    private ProgressBar spinner;
    private double latS,lons;
    private LoginData loginData;
    private UserInformation userInformation;
    private MarkerOptions options ;
    TextView userFirstName;
    TextView userPhoneNumber;
    private  Address address;

    private Main main;
    public  long back_pressed;
    private ConnectionCheck connectionCheck;
    private SharedPreferences.Editor editor ;
    private String activityChangeForSearch=null;
    private MapMarkerDragging mapMarkerDragging;
    private LinearLayout linearLayout;

    private  String sourceLocationName,destinationLocationName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);;

        InitializationAll();

        getLocationPermission();
        activityChangeForSearch = getIntent().getStringExtra("locationName");
        if(activityChangeForSearch !=null){
            activityChangeForSearch = getIntent().getStringExtra("SearchLocation");
            onActivityResult1(activityChangeForSearch);
        }




    }



    private void InitializationAll(){
        sourceText = (TextView) findViewById(R.id.sourceText);
        destinationText =(TextView) findViewById(R.id.destinationText);

        userFirstName = (TextView) findViewById(R.id.userNameProfile);
        userPhoneNumber = (TextView) findViewById(R.id.use_Phonemuber);
        linearLayout = (LinearLayout)findViewById(R.id.searchLinearLayout) ;

        mGps = (ImageView) findViewById(R.id.ic_gps);
        sendButton = (Button) findViewById(R.id.btnSend);
        requestbtn = (Button) findViewById(R.id.pickupbtn);
        sendButton.setVisibility(View.INVISIBLE);
        requestbtn.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        sharedpreferences = this.getSharedPreferences("MyPref", 0);
        //   sendButton.setVisibility(View.INVISIBLE);
        userInformation = new UserInformation(this);
        connectionCheck = new ConnectionCheck(this);
        editor= sharedpreferences.edit();
        main = new Main();
        options= new MarkerOptions();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        loginData = userInformation.getuserInformation();
        phonemumber = sharedpreferences.getString("phone_number","");

        main.CreateNewRiderFirebase(loginData,phonemumber);
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

           if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected() && activityChangeForSearch==null){
               getDeviceLocation();

           }




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
                linearLayout.setVisibility(View.VISIBLE);
                if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
                    getDeviceLocation();

                }

                else {
                    connectionCheck.showGPSDisabledAlertToUser();
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(source, dest);

                    DownloadTask downloadTask = new DownloadTask(mMap,source,dest);
                    DownloadTask2 downloadTask2 = new DownloadTask2(source,dest);
                    String a = downloadTask2.getDestinatin();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                    sendButton.setVisibility(View.INVISIBLE);
                    requestbtn.setVisibility(View.VISIBLE);
                    //mMap.getUiSettings().setScrollGesturesEnabled(false);
                }
                else{
                    Toast.makeText(MapActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                }
            }
        });





        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
                    spinner.setVisibility(View.VISIBLE);
                    requestbtn.setVisibility(View.INVISIBLE);
                    mGps.setVisibility(View.INVISIBLE);
                    mMap.getUiSettings().setScrollGesturesEnabled(false);
                    linearLayout.setVisibility(View.INVISIBLE);
                    Double SourceLat = source.latitude;
                    Double SourceLan = source.longitude;
                    Double DestinationLat = dest.latitude;
                    Double DestinationLan = dest.longitude;
                    String srcLocation = sourceLocationName;
                    String destLocation = destinationLocationName;
                    Pair Source = Pair.create(SourceLat,SourceLan);
                    Pair Destination = Pair.create(DestinationLat,DestinationLan);
                    Main main = new Main();
                    main.RequestForRide(Source, Destination, srcLocation, destLocation);
                }
                else{
                    Toast.makeText(MapActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                }



            }
        });


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub
                // Here your code
                Toast.makeText(getApplicationContext(), "Dragging Start",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub
                if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
                    String markerOption = marker.getSnippet();
                    Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> myList = myLocation.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude, 1);
                        address = (Address) myList.get(0);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(markerOption.equals("Home")){
                        editor.putFloat("lats", (float) marker.getPosition().latitude);
                        editor.putFloat("lons", (float) marker.getPosition().longitude);
                        editor.putString("locationName", address.getAddressLine(0));
                        editor.commit();
                    }
                    else{
                        editor.putFloat("latd", (float) marker.getPosition().latitude);
                        editor.putFloat("lond", (float)marker.getPosition().longitude);
                        editor.putString("locationNamed", address.getAddressLine(0));
                        editor.commit();
                    }
                    checkLatLon();
                }
                else{
                    Toast.makeText(MapActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                // Toast.makeText(MainActivity.this, "Dragging",
                // Toast.LENGTH_SHORT).show();
                System.out.println("Draagging");
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
        requestbtn.setVisibility(View.INVISIBLE);
        mMap.clear();
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
                            editor.putFloat("lats",(float) source.latitude);
                            editor.putFloat("lons",(float) source.longitude);
                            editor.putString("locationName", MycurrentLocation);
                            sourceText.setText(MycurrentLocation);
                            editor.putFloat("latd",(float) 0.0);
                            editor.putFloat("lond",(float) 0.0);
                            editor.putString("locationNamed", "");
                            editor.commit();

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(source.latitude, source.longitude))
                                    .title("Destination")
                                    .draggable(true)
                                    .snippet("Work")
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                           // mapMarkerDragging = new MapMarkerDragging(MapActivity.this,source,dest,mMap);
                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
                                    checkLatLon();


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

            sourceText.setText(locationName);
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

     public void  checkLatLon(){
        String location = sharedpreferences.getString("locationName", "");
        double lats = sharedpreferences.getFloat("lats", (float) 0.0);
        double lons = sharedpreferences.getFloat("lons", (float) 0.0);
        String locationd = sharedpreferences.getString("locationNamed", "");
        double latd = sharedpreferences.getFloat("latd", (float) 0.0);
        double lond = sharedpreferences.getFloat("lond", (float) 0.0);
         sourceText.setText(location);
         destinationText.setText(locationd);
        if(lats !=0.0){

            source = new LatLng(lats,lons);
        }
        if(latd !=0.0){

            dest = new LatLng(latd,lond);
        }
        if(lats !=0.0 && latd !=0.0){
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

    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
         //   Intent intent = getIntent();
         //   finish();
         //   startActivity(intent);
            Toast.makeText(getBaseContext(),
                    "Press once again to exit!", Toast.LENGTH_SHORT)
                    .show();
        }
        back_pressed = System.currentTimeMillis();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),""+item.getItemId(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.nav_notifications:
              Intent intent = new Intent(MapActivity.this, FavoritePlacesActivity.class);
              startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}










