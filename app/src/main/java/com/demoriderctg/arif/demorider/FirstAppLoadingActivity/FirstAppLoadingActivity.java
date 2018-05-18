package com.demoriderctg.arif.demorider.FirstAppLoadingActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.demoriderctg.arif.demorider.ActiveContext;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.FacebookAccountVerificationActivity;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.InternetConnection.InternetCheckActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.AppPreloadModel.PreloadResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.AppSettingModels.AppSettings;
import com.demoriderctg.arif.demorider.models.ApiModels.LatLongBound;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginModel;
import com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels.NewsCard;
import com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels.NewsCardResponse;
import com.demoriderctg.arif.demorider.models.RideCancelModels.RideCancelReason;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.UUID;

import ContactWithFirebase.Main;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstAppLoadingActivity extends AppCompatActivity {

   private UserInformation userInformation;
   private LoginData loginData;
    private Handler handler = new Handler();
    private Main main;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_first_app_loading);
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        new ActiveContext(this);
        new Main();
        int GET_MY_PERMISSION = 1;

        if(loginData != null){
            preLoadAppData(loginData.getClientId());
        }
        else{
            Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    void InitializeApp(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                   if(AppConstant.IS_RIDE !=2 ){

                       if(AppConstant.IS_RIDE == 0){
                           main.UpdateNameImageAndRatting(loginData.getFirstName()+" "+loginData.getLastName(),
                                   loginData.getAvatar(),loginData.getRating()+"");
                           requestForSpecificPermission();
                       }
                       if(AppConstant.IS_RIDE==1){

                           AppConstant.SOURCE = new LatLng(AppConstant.currentRidingHistoryModel.StartingLocation.Latitude,
                                   AppConstant.currentRidingHistoryModel.StartingLocation.Longitude);
                           AppConstant.DESTINATION = new LatLng(AppConstant.currentRidingHistoryModel.EndingLocation.Latitude,
                                   AppConstant.currentRidingHistoryModel.EndingLocation.Longitude);
                           if(AppConstant.currentRidingHistoryModel.IsRideStart ==-1 && AppConstant.currentRidingHistoryModel.IsRideFinished==-1)
                           {
                               AppConstant.INITIAL_RIDE_ACCEPT=1;
                               AppConstant.RIDER_PHONENUMBER = String.valueOf(AppConstant.riderModel.PhoneNumber);
                               AppConstant.RIDER_NAME=AppConstant.riderModel.FullName;
                           }

                           else if(AppConstant.currentRidingHistoryModel.IsRideStart !=-1 && AppConstant.currentRidingHistoryModel.IsRideFinished==-1){
                               AppConstant.START_RIDE=true;
                               AppConstant.FINISH_RIDE=false;
                               AppConstant.RIDER_PHONENUMBER = String.valueOf(AppConstant.riderModel.PhoneNumber);
                               AppConstant.RIDER_NAME=AppConstant.riderModel.FullName;
                           }
                           else{
                               AppConstant.FINISH_RIDE=true;
                           }
                           Intent intent = new Intent(FirstAppLoadingActivity.this, OnrideModeActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   }
                   else{
                       handler.postDelayed(this, 1000);
                   }

            }
        };
        handler.postDelayed(runnable, 1000);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==0) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    public void preLoadAppData(String client_id){

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String authHeader = "Bearer "+pref.getString("access_token",null);

        Call<PreloadResponse> call = apiService.preloadAppData(authHeader,client_id);
        call.enqueue(new Callback<PreloadResponse>() {
            @Override
            public void onResponse(Call<PreloadResponse> call, Response<PreloadResponse> response) {

                int statusCode = response.code();
                switch(statusCode){
                    case 200:
                        LoginData newLoginData = response.body().getData().getUserInformations();
                        ArrayList<NewsCard> newsCards = response.body().getData().getNewsCards();
                        ArrayList<LatLongBound> latLongBounds = response.body().getData().getLatLongBounds();
                        AppSettings appSettings = response.body().getData().getAppSettings();
                        ArrayList<RideCancelReason> rideCancelReasons = response.body().getData().getRideCancelReasons();

                        Gson userData = new Gson();
                        String userJson = userData.toJson(newLoginData);
                        editor.putString("userData",userJson);
                        editor.apply();

                        Gson newsCardData = new Gson();
                        String newsCardJson = newsCardData.toJson(newsCards);
                        editor.putString("newsCardData",newsCardJson);
                        editor.commit();

                        Gson latLongBoundData = new Gson();
                        String latLongBoundJson = latLongBoundData.toJson(latLongBounds);
                        editor.putString("latLongData",latLongBoundJson);
                        editor.commit();

                        Gson appSettingsData = new Gson();
                        String appSettingsJson = appSettingsData.toJson(appSettings);
                        editor.putString("appSettingsData",appSettingsJson);
                        editor.commit();

                        Gson rideCancelReasonsData = new Gson();
                        String rideCancelReasonsJSON = rideCancelReasonsData.toJson(rideCancelReasons);
                        editor.putString("rideCancelReasons",rideCancelReasonsJSON);
                        editor.commit();
                        main = new Main();
                        main.HasAnyRide(Long.parseLong(loginData.getClientId()));
                        InitializeApp();
                        break;
                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PreloadResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void GetClientsAllInformations(String client_id){

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String authHeader = "Bearer "+pref.getString("access_token",null);

        Call<LoginModel> call = apiService.getClientAllInformations(authHeader,client_id);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                int statusCode = response.code();
                switch(statusCode){
                    case 200:
                        LoginData newLoginData = response.body().getLoginData();
                        Gson gson = new Gson();
                        String json = gson.toJson(newLoginData);
                        editor.putString("userData",json);
                        editor.apply();
                        getNewsCards();
                        break;
                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void getNewsCards(){


        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String authHeader = "Bearer "+pref.getString("access_token",null);

        Call<NewsCardResponse> call = apiService.getClientNewsCards(authHeader);
        call.enqueue(new Callback<NewsCardResponse>() {
            @Override
            public void onResponse(Call<NewsCardResponse> call, Response<NewsCardResponse> response) {

                int statusCode = response.code();
                switch(statusCode){
                    case 200:
                        ArrayList<NewsCard> newsCard = response.body().getData();
                        Gson gson = new Gson();
                        String json = gson.toJson(newsCard);
                        editor.putString("newsCardData",json);
                        editor.apply();
                        main.HasAnyRide(Long.parseLong(loginData.getClientId()));
                        InitializeApp();
                        break;
                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<NewsCardResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3]==PackageManager.PERMISSION_GRANTED) {
                    if(loginData !=null){
                        getDeviceID();

                            Intent intent = new Intent(FirstAppLoadingActivity.this, MapActivity.class);
                            startActivity(intent);
                            finish();
                    }

                } else {
                    //not granted
                    Toast.makeText(getApplicationContext(), "Please Restart Application", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void getDeviceID() {

        try{
            final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, androidId;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            // AppConstant.SESSION_KEY = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            AppConstant.SESSION_KEY =deviceId;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
