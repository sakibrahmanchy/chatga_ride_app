package com.demoriderctg.arif.demorider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.demoriderctg.arif.Sqlite.DBHelper;
import com.demoriderctg.arif.Sqlite.Vmhistory;
import com.demoriderctg.arif.demorider.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class autoComplete extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;

    private AutoCompleteTextView mSearchText;
    private AutoCompleteTextView mSearchTextDestination;
    private PlaceInfo mPlace;
    public static LatLng source,dest;
    private  String LocationName="";
    private  String lat;
    private  String lon;
    private String activatyName;
    private DBHelper mydb;
    Context context;
    private RecyclerView rv;
    private RecyclerView rvSearchHistory;
    public List<SearchHistory> searchHistories,searchHistoriesList;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(54.69726685890506,-2.7379201682812226), new LatLng(55.38942944437183, -1.2456105979687226));

    //database

    private  String locationName;
    private  double latitude;
    private  double longitude;
    private  String searchTime;
    ArrayList<Vmhistory>vmhistories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_complete_search);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        LocationName="";
        activatyName =  getIntent().getStringExtra("From");
       // rv = (RecyclerView)findViewById(R.id.rv);
      //  rv.setHasFixedSize(true);
       LinearLayoutManager llm = new LinearLayoutManager(context);
      //  rv.setLayoutManager(llm);

        rvSearchHistory = (RecyclerView)findViewById(R.id.rvSearchHistory);
        rvSearchHistory.setHasFixedSize(true);
        rvSearchHistory.setLayoutManager(llm);
        mydb = new DBHelper(this);
       // mydb.crateTable();
       // mydb.deleteAllHistory();
        vmhistories = mydb.getAllSearchList();
        init();
      //  initializeData();
      //  initializeAdapter();
        initializeDataForSearch();
        initializeAdapterForSearchHistory();


    }

    void  init(){

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, autoComplete.this)
                .build();
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            //Hidding Keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);


        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {



//            if(!places.getStatus().isSuccess()){
//
//                places.release();
//                return;
//            }
            final Place place = places.get(0);

            try{

                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setId(place.getId());
                mPlace.setLatlng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setWebsiteUri(place.getWebsiteUri());

                source =place.getLatLng();
                lat =""+source.latitude;
                lon=""+source.longitude;
                LocationName =mSearchText.getText().toString();

                //database
                locationName=LocationName;
                latitude=source.latitude;
                longitude=source.longitude;
                Date date = new Date();
                searchTime=date.toString();

                places.release();

                Intent i = new Intent(autoComplete.this, MapActivity.class);
                i.putExtra("lat",source.latitude);
                i.putExtra("lon",source.longitude);
                i.putExtra("locationName",LocationName);
                if(activatyName.equals("from")){
                    i.putExtra("SearchLocation","from");
                }
                if(activatyName.equals("to")){
                    i.putExtra("SearchLocation","to");
                }

                insertHistoryTable();
                setResult(Activity.RESULT_OK, i);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();


            }catch (NullPointerException e){
                Log.d("", "onResult: NullPointerException: " + e.getMessage() );
            }
        }

    };

    @Override
    public void onBackPressed()
    {
       finish();
    }

    private void initializeData(){
        searchHistories = new ArrayList<>();
        searchHistories.add(new SearchHistory("Emma Wilson", "23 years old", R.mipmap.home));
        searchHistories.add(new SearchHistory("Emma Wilson", "23 years old", R.drawable.ic_gps));
    }

    private void initializeDataForSearch(){
        searchHistoriesList = new ArrayList<>();
        for (int i=0; i<vmhistories.size();i++){
            Vmhistory vmhistory =new Vmhistory();
            vmhistory =vmhistories.get(i);
            searchHistoriesList.add(new SearchHistory(vmhistory.locationName, "Dhaka", R.mipmap.bike));
        }


    }
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(searchHistories);
        rv.setAdapter(adapter);
    }

    private void initializeAdapterForSearchHistory(){
        RVAdapter adapter = new RVAdapter(searchHistoriesList);
        rvSearchHistory.setAdapter(adapter);
    }

    private void   insertHistoryTable(){
        Vmhistory vmhistory =new Vmhistory();
        int flag=0;
        for (int i=0; i<vmhistories.size();i++){

            vmhistory =vmhistories.get(i);
            if(vmhistory.letitude==latitude){
                flag=1;
               break;
            }
        }
        if(flag==0)
        mydb.insertContact(locationName,latitude,longitude,searchTime);
    }
}
