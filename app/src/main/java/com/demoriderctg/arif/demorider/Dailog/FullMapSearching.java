package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demoriderctg.arif.demorider.R;

public class FullMapSearching extends AppCompatActivity {

    public static Activity fullMapActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_map_searching);
        fullMapActivity = this;
        Intent intent = new Intent(FullMapSearching.this, SearchingDriver.class);
        startActivity(intent);
    }
}
