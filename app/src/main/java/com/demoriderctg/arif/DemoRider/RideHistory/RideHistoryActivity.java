package com.demoriderctg.arif.DemoRider.RideHistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demoriderctg.arif.DemoRider.R;

public class RideHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
