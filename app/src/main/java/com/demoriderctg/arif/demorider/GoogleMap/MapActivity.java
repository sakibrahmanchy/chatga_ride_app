package com.demoriderctg.arif.demorider.GoogleMap;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.About.AboutActivity;
import com.demoriderctg.arif.demorider.Adapters.History.ClientHistoryActivity;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.ClearData.ClearData;
import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogRide;
import com.demoriderctg.arif.demorider.Dailog.RideFinishDailog;
import com.demoriderctg.arif.demorider.FavoritePlaces.FavoritePlacesActivity;
import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.FinishRideActivity.FinishRideActivity;
import com.demoriderctg.arif.demorider.Help.HelpActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.InternetConnection.InternetCheckActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.NotificationActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.PlaceAutocompleteAdapter;
import com.demoriderctg.arif.demorider.PromotionActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.Setting.SettingActivity;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.VmModels.VmCurrentLocation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import ContactWithFirebase.Main;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.Shape;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera postion change" + "", cameraPosition + "");
                getSupportActionBar().show();
                LatLng changeLocation = cameraPosition.target;
              //  sourceMarker.setPosition(changeLocation);
                CheckService(changeLocation);
                    try {
                        List<Address> myList = myLocation.getFromLocation(changeLocation.latitude, changeLocation.longitude, 1);
                        if(myList.size()>0){
                            Address address = (Address) myList.get(0);
                            if(AppConstant.SOURCE_SELECT){
                                AppConstant.searchSorceLocationModel.homeLocationName = address.getAddressLine(0);
                                AppConstant.searchSorceLocationModel.home = changeLocation;
                            }
                            else{
                                AppConstant.searchDestinationLocationModel.workLocationName = address.getAddressLine(0);
                                AppConstant.searchDestinationLocationModel.work = changeLocation;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        });
        if (mLocationPermissionsGranted) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
         //   if (connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected() && activityChangeForSearch == null) {
                getDeviceLocation();


           // }
            init();
        }
    }

    private static final String TAG = "MapActivity";
    private static final String TAGHEIGHT = "HEIGHTS";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static boolean sendtBtnClick =false;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(54.69726685890506, -2.7379201682812226), new LatLng(55.38942944437183, -1.2456105979687226));
    String CurrentLocation;
    //widgets
    private Button sendButton;
    private ImageView mGps;
    private Button requestbtn;
    private TextView sourceText;
    private TextView destinationText;
    private SharedPreferences sharedpreferences;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private LatLng source, dest;
    private String phonemumber;
    private ProgressBar spinner;
    private LoginData loginData;
    private UserInformation userInformation;
    private Marker sourceMarker,destinationMarker=null;
    TextView userFirstName;
    TextView userPhoneNumber;
    private Address address;
    private int PLACE_PICKER_REQUEST = 1;
    private int PLACE_PICKER_REQUEST_DESTINATION = 2;
    private  ImageView defaultImageMarker;


    private Main main;
    public long back_pressed;
    private ConnectionCheck connectionCheck;
    private SharedPreferences.Editor editor;
    private String activityChangeForSearch = null;
    public static Context contextOfApplication;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private CoordinatorLayout elemnentsContainer;
    private LinearLayout actionsConainer;
    private LinearLayout searchContainer;
    private TextView serviceNotAvailable;
    private RatingBar userRating;
    private View v;

    private GetCurrentLocation getCurrentLocation;


    private LinearLayout linearLayout;

    private boolean isTotalHeightFound = false;
    private boolean isActionHeightFound = false;
    private boolean isSearchHeightFound = false;
    private boolean isAppShowCased = false;
    private VmCurrentLocation  vmCurrentLocation;
    Geocoder myLocation;

    private double totalHeight, actionHeight, searchHeight,peekHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        contextOfApplication = getApplicationContext();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        elemnentsContainer = findViewById(R.id.elements_container);
        actionsConainer = findViewById(R.id.actions_container);
        searchContainer = findViewById(R.id.searchLinearLayout);
        serviceNotAvailable =findViewById(R.id.service_not_available);
        serviceNotAvailable.setVisibility(View.INVISIBLE);
        getCurrentLocation = new GetCurrentLocation(this);

         defaultImageMarker = findViewById(R.id.default_image_Marker);

        bottomSheet = findViewById( R.id.bottom_sheet );

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        getPeekHeightOfScrollBar();

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState){

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                if(slideOffset>=0.8){
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.hide();
                }
                else if(slideOffset>=0.7 ){
                    linearLayout.setVisibility(View.GONE);
                }else if(slideOffset>=0 && slideOffset<0.7){
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.show();
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    mBottomSheetBehavior.setPeekHeight((int) peekHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });

        getLocationPermission();
        InitializationAll();



    }


    private void InitializationAll() {
        sourceText = (TextView) findViewById(R.id.sourceText);
        destinationText = (TextView) findViewById(R.id.destinationText);
        linearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        sendButton = (Button) findViewById(R.id.btnSend);
        requestbtn = (Button) findViewById(R.id.pickupbtn);
        requestbtn.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        //spinner = (ProgressBar) findViewById(R.id.progressBar);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        destinationText.didTouchFocusSelect();
        v = navigationView.getHeaderView(0);
        userInformation = new UserInformation(this);

        ImageView avatarContainer = (ImageView ) v.findViewById(R.id.profile_nav);
        userFirstName = (TextView) v.findViewById(R.id.user_full_name);
        userPhoneNumber =v.findViewById(R.id.user_phone_number);
        userRating =v.findViewById(R.id.user_rating);
        userFirstName.setText(userInformation.getuserInformation().getFirstName() +" " + userInformation.getuserInformation().getLastName());
        userPhoneNumber.setText(userInformation.getuserInformation().phone);
        userRating.setRating( (float) (userInformation.getuserInformation().getRating()));
        Picasso.with(this).invalidate(userInformation.getuserInformation().getAvatar());
        Picasso.with(this)
                .load(userInformation.getuserInformation().getAvatar())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .noFade()
                .into(avatarContainer);


        //spinner.setVisibility(View.GONE);
        sharedpreferences = this.getSharedPreferences("MyPref", 0);

        connectionCheck = new ConnectionCheck(this);
        editor = sharedpreferences.edit();
        main = new Main();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(AppConstant.FINISH_RIDE){
           Intent intent = new Intent(this, FinishRideActivity.class);
           startActivity(intent);


        }
        loginData = userInformation.getuserInformation();
        phonemumber = userInformation.getRiderPhoneNumber();

        if (userInformation.getUserHomeLocation() != null) {
            AppConstant.searchSorceLocationModel = new HomeLocationModel();
            AppConstant.searchSorceLocationModel = userInformation.getUserHomeLocation();
        }

        if (userInformation.getUserWorkLocation() != null) {
            AppConstant.searchDestinationLocationModel = new WorkLocationModel();
            AppConstant.searchDestinationLocationModel = userInformation.getUserWorkLocation();
        }

        main.CreateNewClientFirebase(loginData, phonemumber);
    }

    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);


        sourceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    if(sendtBtnClick==true){
                        mMap.clear();
                        sendtBtnClick=false;


                    }



                    AutocompleteFilter filter =
                            new AutocompleteFilter.Builder()
                                    .setCountry("BD")
                                    .build();
//                    Intent intent =
//                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).
//                                    setBoundsBias(AppConstant.LAT_LNG_BOUNDS).setFilter(filter)
//                                    .build(MapActivity.this);
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setBoundsBias(AppConstant.LAT_LNG_BOUNDS_CTG_3)
                                    .setFilter(filter)
                                    .build(MapActivity.this);
                    startActivityForResult(intent, AppConstant.SEARCH_SOURCE_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        destinationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(sendtBtnClick==true){
                        mMap.clear();
                        sendtBtnClick=false;

                    }
                    AutocompleteFilter filter =
                            new AutocompleteFilter.Builder()
                                    .setCountry("BD")
                                    .build();
//                    Intent intent =
//                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).
//                                    setBoundsBias(AppConstant.LAT_LNG_BOUNDS).setFilter(filter)
//                                    .build(MapActivity.this);
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setBoundsBias(AppConstant.LAT_LNG_BOUNDS_CTG_3)
                                    .setFilter(filter)
                                    .build(MapActivity.this);
                    startActivityForResult(intent, AppConstant.SEARCH_DESTINATION_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                if (!connectionCheck.isNetworkConnected()) {
                    Intent intent = new Intent(MapActivity.this, InternetCheckActivity.class);
                    startActivityForResult(intent, AppConstant.INTERNET_CHECK);
                }  else {
                    getDeviceLocation();
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionCheck.isNetworkConnected() && CheckService(AppConstant.searchSorceLocationModel.home)&& CheckService(AppConstant.searchDestinationLocationModel.work)) {
                    new DiscountCalculation(MapActivity.this).getClientPromotions();
                    AppConstant.SOURCE = AppConstant.searchSorceLocationModel.home;
                    AppConstant.DESTINATION = AppConstant.searchDestinationLocationModel.work;
                    AppConstant.SOURCE_NAME = AppConstant.searchSorceLocationModel.homeLocationName;
                    AppConstant.DESTINATION_NAME = AppConstant.searchDestinationLocationModel.workLocationName;
                    String url = getDirectionsUrl(AppConstant.SOURCE, AppConstant.DESTINATION);
                    DownloadTask downloadTask = new DownloadTask(MapActivity.this, mMap, AppConstant.SOURCE, AppConstant.DESTINATION);
                    downloadTask.execute(url);
                }
                else{
                    serviceNotAvailable.setVisibility(View.VISIBLE);
                }

            }
        });


        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialogFragment myBottomSheet = BottomSheetDailogRide.newInstance("Modal Bottom Sheet");
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
            }
        });

        sourceText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(sourceMarker !=null){
                    sourceMarker.remove();
                }
                mapStateChange(true);
                moveCamera(AppConstant.searchSorceLocationModel.home,AppConstant.DEFAULT_ZOOM,"Default");
                AppConstant.SOURCE_SELECT = true;
                AppConstant.DESTINATION_SELECT = false;
                defaultImageMarker.setImageResource(R.drawable.ic_marker_pickup);
                return false;
            }
        });

        destinationText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(destinationMarker !=null){
                    destinationMarker.remove();
                }
                mapStateChange(true);
                moveCamera(AppConstant.searchDestinationLocationModel.work,AppConstant.DEFAULT_ZOOM,"Default");
                AppConstant.SOURCE_SELECT = false;
                AppConstant.DESTINATION_SELECT = true;

                defaultImageMarker.setImageResource(R.drawable.ic_marker_destination);
                return false;
            }
        });

        hideSoftKeyboard();
    }


    private void getDeviceLocation() {
        myLocation = new Geocoder(MapActivity.this, Locale.getDefault());
        if(connectionCheck.isGpsEnable()){

            try {
                vmCurrentLocation = new VmCurrentLocation();
                vmCurrentLocation.latitude=getCurrentLocation.getLatitude();
                vmCurrentLocation.logitude=getCurrentLocation.getLongitude();
                List<Address> myList = myLocation.getFromLocation(vmCurrentLocation.latitude, vmCurrentLocation.logitude, 1);
               if(myList.size()>0){

                   Address address = myList.get(0);
                   Gson gson = new Gson();
                   String json = gson.toJson(vmCurrentLocation);
                   editor.putString("currentLocation",json);
                   editor.commit();

                   if (AppConstant.searchSorceLocationModel == null) {
                       AppConstant.searchSorceLocationModel = new HomeLocationModel();

                   }

                   AppConstant.searchSorceLocationModel.homeLocationName = address.getAddressLine(0);
                   AppConstant.searchSorceLocationModel.home = new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude);
                   if(AppConstant.searchDestinationLocationModel == null){
                       AppConstant.searchDestinationLocationModel = new WorkLocationModel();
                       AppConstant.searchDestinationLocationModel.workLocationName = AppConstant.searchSorceLocationModel.homeLocationName;
                       AppConstant.searchDestinationLocationModel.work =AppConstant.searchSorceLocationModel.home;
                   }
                   String sourceLocation = AppConstant .searchSorceLocationModel.homeLocationName;
                   sourceText.setText(sourceLocation);


                   moveCamera(new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude),
                           AppConstant.DEFAULT_ZOOM, "Default");
               }
                 CheckService(AppConstant.searchSorceLocationModel.home);
                if(!sharedpreferences.getString("APP_SHOWCASED","").equals("true"))
                    showCaseApp();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            vmCurrentLocation = userInformation.getUserCurrentLocation();
            if(vmCurrentLocation !=null){
                AppConstant.searchSorceLocationModel = new HomeLocationModel();
                AppConstant.searchSorceLocationModel.homeLocationName = vmCurrentLocation.locationName;
                AppConstant.searchSorceLocationModel.home = new LatLng(vmCurrentLocation.latitude,
                        vmCurrentLocation.logitude);
                String sourceLocation = AppConstant .searchSorceLocationModel.homeLocationName;
                sourceText.setText(sourceLocation);
                moveCamera(new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude),
                        AppConstant.DEFAULT_ZOOM,

                        "Source");
            }
            else{
                AppConstant.searchSorceLocationModel = new HomeLocationModel();
                AppConstant.searchSorceLocationModel.homeLocationName = "UNKNOWN";
                AppConstant.searchSorceLocationModel.home = new LatLng(AppConstant.LAT_LNG_BOUNDS_CTG.southwest.latitude,
                        AppConstant.LAT_LNG_BOUNDS_CTG.southwest.longitude);
                String sourceLocation = AppConstant .searchSorceLocationModel.homeLocationName;
                sourceText.setText(sourceLocation);
                moveCamera(AppConstant.LAT_LNG_BOUNDS_CTG.southwest,
                         AppConstant.DEFAULT_ZOOM,

                        "Source");
            }
            Toast.makeText(getContextOfApplication(),"GPS OFF",Toast.LENGTH_SHORT).show();
        }



    }

    void checkButtonState(){
        if(AppConstant.searchDestinationLocationModel == null){
            sendButton.setVisibility(View.INVISIBLE);
            requestbtn.setVisibility(View.INVISIBLE);
            destinationText.setText("");
        }
        if(AppConstant.searchSorceLocationModel == null){
            sendButton.setVisibility(View.INVISIBLE);
            requestbtn.setVisibility(View.INVISIBLE);
            sourceText.setText("");
        }
        if(AppConstant.searchSorceLocationModel!=null && AppConstant.searchDestinationLocationModel !=null){
            sendButton.setVisibility(View.VISIBLE);
            sourceText.setText(AppConstant.searchSorceLocationModel.homeLocationName);
            destinationText.setText(AppConstant.searchDestinationLocationModel.workLocationName);
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title) {


        if (title.equals("Destination"))
        {
               if(destinationMarker !=null){
                   destinationMarker.remove();
               }

                destinationMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Destination")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));

                 mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


        }
        if (title.equals("Source"))
        {
            if(sourceMarker !=null){
                sourceMarker.remove();
            }
                sourceMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Source")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",200,200))).anchor(.5f,.5f));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
        else{
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        }

    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void hideSoftKeyboard() {
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

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        requestbtn.setVisibility(View.INVISIBLE);
    if (requestCode == AppConstant.SEARCH_SOURCE_AUTOCOMPLETE) {
        if (resultCode == RESULT_OK) {

            if(AppConstant.searchSorceLocationModel !=null){
                Place place = PlaceAutocomplete.getPlace(this, data);
                    AppConstant.searchSorceLocationModel.homeLocationName = place.getAddress().toString();
                    AppConstant.searchSorceLocationModel.home = place.getLatLng();
                    String sourceLocation = AppConstant.searchSorceLocationModel.homeLocationName;
                    sourceText.setText(sourceLocation);
                    CheckService(AppConstant.searchSorceLocationModel.home);
                    moveCamera(AppConstant.searchSorceLocationModel.home, AppConstant.DEFAULT_ZOOM, "default");

            }

        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            // TODO: Handle the error.
            Log.i(TAG, status.getStatusMessage());

        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }
    } else if (requestCode == AppConstant.SEARCH_DESTINATION_AUTOCOMPLETE) {
        if (resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(this, data);
                if (AppConstant.searchDestinationLocationModel == null) {
                    AppConstant.searchDestinationLocationModel = new WorkLocationModel();
                }
                AppConstant.searchDestinationLocationModel.workLocationName = place.getAddress().toString();
                AppConstant.searchDestinationLocationModel.work = place.getLatLng();
                String destinationLocation = AppConstant.searchDestinationLocationModel.workLocationName;
                destinationText.setText(destinationLocation);
                CheckService(AppConstant.searchDestinationLocationModel.work);
                moveCamera(AppConstant.searchDestinationLocationModel.work, AppConstant.DEFAULT_ZOOM, "default");


        }
        else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            // TODO: Handle the error.
            Log.i(TAG, status.getStatusMessage());

        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }
    }


}

    //Menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        mapStateChange(true);
        defaultImageMarker.setVisibility(View.VISIBLE);
        mMap.clear();
        moveCamera(AppConstant.searchSorceLocationModel.home,AppConstant.DEFAULT_ZOOM,"default");
        requestbtn.setVisibility(View.INVISIBLE);


        if (back_pressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(),
                    "Press once again to exit!", Toast.LENGTH_SHORT)
                    .show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_settings:
                Intent intent = new Intent(MapActivity.this, SettingActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.nav_history:
                Intent clientIntent = new Intent(MapActivity.this, ClientHistoryActivity.class);
                startActivityForResult(clientIntent, 0);
                break;
            case R.id.nav_promotions:
                Intent promotionIntent = new Intent(MapActivity.this, PromotionActivity.class);
                startActivityForResult(promotionIntent, 0);
                break;
            case R.id.nav_notifications:
                Intent notificationIntent = new Intent(MapActivity.this, NotificationActivity.class);
                startActivityForResult(notificationIntent, 0);
                break;
            case R.id.nav_about:
                Intent aboutItent = new Intent(MapActivity.this, AboutActivity.class);
                startActivityForResult(aboutItent, 0);
                break;
            case R.id.nav_help:
                Intent HelpItent = new Intent(MapActivity.this, HelpActivity.class);
                startActivityForResult(HelpItent, 0);
                break;
            default:
                break;
        }
        return true;

    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap decodeResource = BitmapFactory.decodeResource(contextOfApplication.getResources(),contextOfApplication.getResources().getIdentifier(iconName, "drawable", contextOfApplication.getPackageName()));
        return Bitmap.createScaledBitmap(decodeResource, (int) (((double) decodeResource.getWidth()) * .25d), (int) (((double) decodeResource.getHeight()) * .25d), false);
    }

    public void getPeekHeightOfScrollBar(){


        ViewTreeObserver observer = elemnentsContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                double elementsContainerHeight = elemnentsContainer.getHeight();
                totalHeight = elementsContainerHeight;
                isTotalHeightFound = true;
                check();
                Log.d(TAGHEIGHT,"A: "+ elementsContainerHeight);
                elemnentsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });

        ViewTreeObserver observer2 = actionsConainer.getViewTreeObserver();
        observer2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                double actionsContainerHeight = actionsConainer.getHeight();
                actionHeight = actionsContainerHeight;
                isActionHeightFound = true;
                check();
                //Log.d(TAGHEIGHT,"B: "+ actionsContainerHeight);
                actionsConainer.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });

        ViewTreeObserver observer3 = searchContainer.getViewTreeObserver();
        observer3.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                double searchContainerHeight = searchContainer.getHeight();
                searchHeight = searchContainerHeight;
                isSearchHeightFound = true;
                check();
                actionsConainer.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
    }

    public void check(){

        if(isActionHeightFound&&isSearchHeightFound&&isTotalHeightFound){
            peekHeight = totalHeight - (actionHeight+searchHeight);
            mBottomSheetBehavior.setPeekHeight((int) peekHeight);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            mBottomSheetBehavior.setHideable(false);
        }
    }

    public void showCaseApp(){

        editor.putString("APP_SHOWCASED","true");
        editor.commit();

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        config.setShapePadding(-320);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "ShowCaseMain" );
        sequence.setConfig(config);
        drawerLayout.openDrawer(Gravity.START);
        sequence.addSequenceItem(navigationView,
                "Manage your information!", "GOT IT").setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
            @Override
            public void onDismiss(MaterialShowcaseView materialShowcaseView, int i) {
                drawerLayout.closeDrawer(Gravity.START);
            }
        });
       // drawerLayout.closeDrawer(Gravity.START);
        config.setShapePadding(0);
        sequence.addSequenceItem(destinationText,
                "Choose your source and destination point and start off!", "GOT IT");

        sequence.addSequenceItem(mGps,
                "Get your current location on the fly!", "GOT IT");

        config.setShapePadding(0);
        sequence.addSequenceItem(bottomSheet,
                "This widget helps you to show recent updates", "GOT IT");

        sequence.start();
    }

    void mapStateChange(boolean state){
        mMap.getUiSettings().setScrollGesturesEnabled(state);
        mMap.getUiSettings().setZoomGesturesEnabled(state);
        defaultImageMarker.setVisibility(View.VISIBLE);
    }


    boolean CheckService(LatLng latLng){
        if(!AppConstant.LAT_LNG_BOUNDS_CTG_3.contains(latLng)){
            serviceNotAvailable.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.INVISIBLE);
            return false;
        }
        else{
            serviceNotAvailable.setVisibility(View.INVISIBLE);
            checkButtonState();
            return true;
        }
    }

}










