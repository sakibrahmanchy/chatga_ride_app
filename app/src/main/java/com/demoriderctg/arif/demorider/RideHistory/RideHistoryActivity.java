package com.demoriderctg.arif.demorider.RideHistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demoriderctg.arif.demorider.R;

public class RideHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
