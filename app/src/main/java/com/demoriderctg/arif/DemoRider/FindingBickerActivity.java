package com.demoriderctg.arif.DemoRider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demoriderctg.arif.DemoRider.R;

public class FindingBickerActivity extends AppCompatActivity {

   private  GetCurrentLocation getCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bicker);

        getCurrentLocation = new GetCurrentLocation(this);


    }
}