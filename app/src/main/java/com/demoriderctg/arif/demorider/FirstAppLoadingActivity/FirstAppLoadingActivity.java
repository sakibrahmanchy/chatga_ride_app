package com.demoriderctg.arif.demorider.FirstAppLoadingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.maps.model.LatLng;


import ContactWithFirebase.Main;

public class FirstAppLoadingActivity extends AppCompatActivity {

   private UserInformation userInformation;
   private LoginData loginData;
    private Handler handler = new Handler();
    private Main main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_app_loading);
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        main = new Main();

        if(loginData !=null){
         //   main.HasAnyRide(Long.parseLong(loginData.getClientId()));
         //   InitializeApp();
            Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
            startActivity(intent);

        }
        else{
            Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    void InitializeApp(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                   if(AppConstant.IS_RIDE !=2 ){

                       if(AppConstant.IS_RIDE == 0){
                           Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       }
                       if(AppConstant.IS_RIDE==1){

                           AppConstant.SOURCE = new LatLng(AppConstant.currentRidingHistoryModel.StartingLocation.Latitude,
                                   AppConstant.currentRidingHistoryModel.StartingLocation.Longitude);
                           AppConstant.DESTINATION = new LatLng(AppConstant.currentRidingHistoryModel.EndingLocation.Latitude,
                                   AppConstant.currentRidingHistoryModel.EndingLocation.Longitude);
                           if(AppConstant.currentRidingHistoryModel.IsRideStart ==-1 && AppConstant.currentRidingHistoryModel.IsRideFinished==-1)
                           {
                               AppConstant.INITIAL_RIDE_ACCEPT=1;
                           }

                           else if(AppConstant.currentRidingHistoryModel.IsRideStart !=-1 && AppConstant.currentRidingHistoryModel.IsRideFinished==-1){
                               AppConstant.START_RIDE=true;
                               AppConstant.FINISH_RIDE=false;
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
}
