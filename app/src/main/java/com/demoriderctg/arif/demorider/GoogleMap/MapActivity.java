package com.demoriderctg.arif.demorider.GoogleMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.About.AboutActivity;
import com.demoriderctg.arif.demorider.ActiveContext;
import com.demoriderctg.arif.demorider.Adapters.History.ClientHistoryActivity;
import com.demoriderctg.arif.demorider.Adapters.NewsCard.NewsCardAdapter;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.CostEstimation.CostEstimation;
import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogRide;
import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.FinishRideActivity.FinishRideActivity;
import com.demoriderctg.arif.demorider.Help.HelpActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.InternetConnection.InternetCheckActivity;
import com.demoriderctg.arif.demorider.NotificationActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.PlaceAutocompleteAdapter;
import com.demoriderctg.arif.demorider.PromotionActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.Referral.Refarrel;
import com.demoriderctg.arif.demorider.Setting.SettingActivity;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.VmModels.VmCurrentLocation;
import com.demoriderctg.arif.demorider.models.ApiModels.LatLongBound;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels.NewsCard;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import ContactWithFirebase.Main;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.demoriderctg.arif.demorider.AppConfig.AppConstant.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.ConnectionCallbacks, LocationListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));

        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                // Although the user’s location will update automatically on a regular basis, you can also
                // give your users a way of triggering a location update manually. Here, we’re adding a
                // ‘My Location’ button to the upper-right corner of our app; when the user taps this button,
                // the camera will update and center on the user’s current location//

                mMap.setMyLocationEnabled(true);
            }
        }
  else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnCameraMoveStartedListener(i -> {

        });

        mMap.setOnCameraIdleListener(() -> {
            // Cleaning all the markers.
            LatLng changeLocation = mMap.getCameraPosition().target;
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

        });
//        if (mLocationPermissionsGranted) {
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//            init();
//           // getDeviceLocation();
//        }
    }

    private static final String TAG = "MapActivity";
    private static final String TAGHEIGHT = "HEIGHTS";

    public static boolean sendtBtnClick =false;
    //widgets
    private FloatingActionButton sendButton;
    private ImageView mGps;
    private FloatingActionButton requestbtn;
    private TextView sourceText;
    private TextView destinationText;
    private SharedPreferences sharedpreferences;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Handler getLocationNameHandler = new Handler();
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String phonemumber;
    private LoginData loginData;
    private UserInformation userInformation;
    private Marker sourceMarker,destinationMarker=null;
    TextView userFirstName;
    TextView userPhoneNumber;
    private  ImageView defaultImageMarker;


    private Main main;
    public long back_pressed;
    private ConnectionCheck connectionCheck;
    private SharedPreferences.Editor editor;
    public static Context contextOfApplication;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private CoordinatorLayout elemnentsContainer;
    private LinearLayout actionsConainer;
    private LinearLayout searchContainer;
    private TextView serviceNotAvailable;
    private RatingBar userRating;
    private View v;
    private LinearLayout linearLayout;

    private boolean isTotalHeightFound = false;
    private boolean isActionHeightFound = false;
    private boolean isSearchHeightFound = false;
    private VmCurrentLocation  vmCurrentLocation;
    Geocoder myLocation;

    private double totalHeight, actionHeight, searchHeight,peekHeight;
    private RecyclerView newsCardListView;
    private ArrayList<NewsCard> newsCards;
    private ArrayList<LatLongBound>regionLatlonBounds;
    private float bottomSheetMinimumSize = 0.9f;
    private float bottomSheetCurrentSize;
    private ImageView navBarToggle;
    LocationRequest mLocationRequest;
    private ProgressDialog dialog;

    private boolean FirstLoad = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

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
        defaultImageMarker = findViewById(R.id.default_image_Marker);
        navBarToggle = findViewById(R.id.nav_drawer_toggle);
        navBarToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        newsCardListView = findViewById(R.id.news_card_listview);

        new ActiveContext(this);
        bottomSheet = findViewById( R.id.bottom_sheet );
        bottomSheet.setScaleX(bottomSheetMinimumSize);
        bottomSheetCurrentSize = bottomSheetMinimumSize;
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading map.....");
        dialog.setCancelable(false);
        dialog.show();
        getPeekHeightOfScrollBar();

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                {
                    findViewById(R.id.bg).setVisibility(View.GONE);
                    bottomSheetCurrentSize = bottomSheetMinimumSize;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                findViewById(R.id.bg).setVisibility(View.VISIBLE);
                findViewById(R.id.bg).setAlpha(slideOffset);
                if(slideOffset>=bottomSheetMinimumSize){
                    bottomSheet.setScaleX(bottomSheetCurrentSize);
                }
                else if(slideOffset>=0.3f && slideOffset<=0.9f){
                    if(bottomSheetCurrentSize<=0.99f)
                        bottomSheet.setScaleX(bottomSheetCurrentSize+=0.01f);
                    else
                        bottomSheetCurrentSize = 1;
                }
                else
                    bottomSheet.setScaleX(bottomSheetMinimumSize);

                if(slideOffset>=0.8){
                    findViewById(R.id.bg).setBackgroundColor(Color.BLACK);
                }
                else if(slideOffset>=0.7 ){
                    linearLayout.setVisibility(View.GONE);
                }else if(slideOffset>=0 && slideOffset<0.7){
//                    if(slideOffset>=0 && slideOffset<=3)
//                        bottomSheet.setScaleX(0.92f);
//                    else
//                        bottomSheet.setScaleX(1f);

                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    mBottomSheetBehavior.setPeekHeight((int) peekHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });
        InitializationAll();
        init();
        getSupportActionBar().hide();

    }


    @SuppressLint("SetTextI18n")
    private void InitializationAll() {
        sourceText = (TextView) findViewById(R.id.sourceText);
        destinationText = (TextView) findViewById(R.id.destinationText);
        linearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        sendButton = (FloatingActionButton) findViewById(R.id.btnSend);
        requestbtn = (FloatingActionButton) findViewById(R.id.pickupbtn);
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

        //Region LatLonBounds

        regionLatlonBounds = userInformation.getLatLongBounds();

        newsCards = userInformation.getNewsCards();
        NewsCardAdapter adapter = new NewsCardAdapter(this, newsCards);
        newsCardListView.setLayoutManager(new LinearLayoutManager(this));
        newsCardListView.setAdapter(adapter);

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

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


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
                double x = new CostEstimation(getApplicationContext()).getDistance(AppConstant.DISTANCE);
                if(x<50){
                    final BottomSheetDialogFragment myBottomSheet = BottomSheetDailogRide.newInstance("Modal Bottom Sheet");
                    myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
                }
                else{
                    serviceNotAvailable.setVisibility(View.VISIBLE);
                }

            }
        });

        sourceText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(sourceMarker !=null){
                    sourceMarker.remove();
                }

                mMap.clear();
                requestbtn.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                mapStateChange(true);
                sourceText.setBackgroundColor(getResources().getColor(R.color.grey_100));
                destinationText.setBackgroundColor(getResources().getColor(R.color.white));
                if(AppConstant.searchSorceLocationModel !=null){
                    moveCamera(AppConstant.searchSorceLocationModel.home,AppConstant.DEFAULT_ZOOM,"Default");
                }

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
                mMap.clear();
                requestbtn.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                destinationText.setBackgroundColor(getResources().getColor(R.color.grey_100));
                sourceText.setBackgroundColor(getResources().getColor(R.color.white));
                mapStateChange(true);
                if(AppConstant.searchDestinationLocationModel!=null){
                    moveCamera(AppConstant.searchDestinationLocationModel.work,AppConstant.DEFAULT_ZOOM,"Default");
                }

                AppConstant.SOURCE_SELECT = false;
                AppConstant.DESTINATION_SELECT = true;

                defaultImageMarker.setImageResource(R.drawable.ic_marker_destination);
                return false;
            }
        });

        hideSoftKeyboard();
    }


    private void getDeviceLocation() {
        if(connectionCheck.isGpsEnable()){
            Runnable runnableForStartRide = new Runnable() {
                @Override
                public void run() {
                    List<Address> myList = null;
                    try {
                        myList = myLocation.getFromLocation(vmCurrentLocation.latitude, vmCurrentLocation.logitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (myList.size() > 0) {

                        Address address = myList.get(0);
                        AppConstant.searchSorceLocationModel = new HomeLocationModel();
                        AppConstant.searchDestinationLocationModel = new WorkLocationModel();
                        AppConstant.searchSorceLocationModel.homeLocationName = address.getAddressLine(0);
                        AppConstant.searchSorceLocationModel.home = new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude);
                        AppConstant.searchDestinationLocationModel.workLocationName = AppConstant.searchSorceLocationModel.homeLocationName;
                        AppConstant.searchDestinationLocationModel.work = AppConstant.searchSorceLocationModel.home;
                        String sourceLocation = AppConstant.searchSorceLocationModel.homeLocationName;
                        sourceText.setText(sourceLocation);
                        moveCamera(new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude),
                                AppConstant.DEFAULT_ZOOM, "Default");
                        CheckService(AppConstant.searchSorceLocationModel.home);
                    } else {
                        getLocationNameHandler.postDelayed(this, 1000);
                    }
                }
            };
            getLocationNameHandler.postDelayed(runnableForStartRide,1000);

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



@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        requestbtn.setVisibility(View.INVISIBLE);
    if (requestCode == AppConstant.SEARCH_SOURCE_AUTOCOMPLETE) {
        if (resultCode == RESULT_OK) {

            if (AppConstant.searchSorceLocationModel == null) {
                AppConstant.searchSorceLocationModel = new HomeLocationModel();
            }
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
            case R.id.nav_referral:
                Intent Referral = new Intent(MapActivity.this, Refarrel.class);
                startActivityForResult(Referral, 0);
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
        config.setShapePadding(-450);
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

        LatLngBounds latLngBounds ;
        for(int i =0; i<regionLatlonBounds.size(); i++){
            latLngBounds = new LatLngBounds(
                    new LatLng(regionLatlonBounds.get(i).getNorthLatitude(),regionLatlonBounds.get(i).getNorthLongitude()),
                    new LatLng(regionLatlonBounds.get(i).getSouthLatitude(),regionLatlonBounds.get(i).getSouthLongitude()));
               if(latLngBounds.contains(latLng)){
                   serviceNotAvailable.setVisibility(View.INVISIBLE);
                   checkButtonState();
                   return true;
               }
        }

            serviceNotAvailable.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.INVISIBLE);
            return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationPermission() {

        // In Android 6.0 and higher you need to request permissions at runtime, and the user has
        // the ability to grant or deny each permission. Users can also revoke a previously-granted
        // permission at any time, so your app must always check that it has access to each
        // permission, before trying to perform actions that require that permission. Here, we’re using
        // ContextCompat.checkSelfPermission to check whether this app currently has the
        // ACCESS_COARSE_LOCATION permission

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                // If your app does have access to COARSE_LOCATION, then this method will return
                // PackageManager.PERMISSION_GRANTED//
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // If your app doesn’t have this permission, then you’ll need to request it by calling
                // the ActivityCompat.requestPermissions method//
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        AppConstant.MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // Request the permission by launching Android’s standard permissions dialog.
                // If you want to provide any additional information, such as why your app requires this
                // particular permission, then you’ll need to add this information before calling
                // requestPermission //
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        AppConstant.MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        // Use the GoogleApiClient.Builder class to create an instance of the
        // Google Play Services API client//
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        // Connect to Google Play Services, by calling the connect() method//
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Retrieve the user’s last known location//
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        List<Address> myList = null;
        myLocation = new Geocoder(MapActivity.this, Locale.getDefault());
        vmCurrentLocation = new VmCurrentLocation();
        if(location !=null){
            vmCurrentLocation.latitude=location.getLatitude();
            vmCurrentLocation.logitude=location.getLongitude();
        }
        if(FirstLoad && location!=null){
            moveCamera(new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude),
                    AppConstant.DEFAULT_ZOOM, "Default");
        }

        Runnable runnableForStartRide = new Runnable() {
            @Override
            public void run() {
                List<Address> myList = null;
                try {
                    myList = myLocation.getFromLocation(vmCurrentLocation.latitude, vmCurrentLocation.logitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (myList !=null) {
                    dialog.cancel();
                    Address address = myList.get(0);
                    AppConstant.searchSorceLocationModel = new HomeLocationModel();
                    AppConstant.searchDestinationLocationModel = new WorkLocationModel();
                    AppConstant.searchSorceLocationModel.homeLocationName = address.getAddressLine(0);
                    AppConstant.searchSorceLocationModel.home = new LatLng(vmCurrentLocation.latitude, vmCurrentLocation.logitude);
                    AppConstant.searchDestinationLocationModel.workLocationName = AppConstant.searchSorceLocationModel.homeLocationName;
                    AppConstant.searchDestinationLocationModel.work = AppConstant.searchSorceLocationModel.home;
                    String sourceLocation = AppConstant.searchSorceLocationModel.homeLocationName;
                    sourceText.setText(sourceLocation);
                    if(!sharedpreferences.getString("APP_SHOWCASED","").equals("true"))
                        showCaseApp();
                    CheckService(AppConstant.searchSorceLocationModel.home);
                } else {
                    getLocationNameHandler.postDelayed(this, 1000);
                }
            }
        };
        if(myList == null && location!=null && FirstLoad){
            FirstLoad=false;
            getLocationNameHandler.postDelayed(runnableForStartRide,1000);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {

                // If the request is cancelled, the result array will be empty (0)//
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // If the user has granted your permission request, then your app can now perform all its
                    // location-related tasks, including displaying the user’s location on the map//
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // If the user has denied your permission request, then at this point you may want to
                    // disable any functionality that depends on this permission//
                }
                return;
            }
        }
    }
}










