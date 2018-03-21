package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.AppSettingModels.AppSettings;

import ContactWithFirebase.Main;

/**
 * Created by Arif on 1/21/2018.
 */

public class FareCostInfo  extends Dialog implements android.view.View.OnClickListener  {
    public Activity activity;
    public Button btnOk;
    private FragmentActivity myContext;
    private TextView baseFare,minimumFare,perKilometer,perMinutes;
    private UserInformation userInformation;
    private AppSettings appSettings;


    public FareCostInfo(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;
        userInformation = new UserInformation(myContext);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fare_cost_info);
        btnOk = (Button) findViewById(R.id.btnOK);
        baseFare = findViewById(R.id.base_fare);
        minimumFare = findViewById(R.id.minimum_fare);
        perKilometer = findViewById(R.id.minimum_fare);
        perMinutes = findViewById(R.id.per_minute);
        btnOk.setOnClickListener(this);
        appSettings = userInformation.getAppSettings();

        setText();

    }

    private  void setText(){
        baseFare.setText("Base Fare     "+appSettings.getBaseFare());
        minimumFare.setText("Minimum Fare    "+appSettings.getMinimumFare());
        perKilometer.setText("Per Kilometer   "+appSettings.getPricePerKm());
        perMinutes.setText("Per Minute       "+appSettings.getPricePerMinute()  );
    }



    @Override
    public void onClick(View v) {

        dismiss();
    }

}
