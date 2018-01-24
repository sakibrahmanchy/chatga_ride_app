package com.demoriderctg.arif.demorider.FavoritePlaces;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class FavoritePlacesActivity extends AppCompatActivity {


    private TextView homeLocation;
    private  TextView workLocation;
    private LinearLayout homeLinearLayout;
    private  LinearLayout workLinearLayout;
    private  int PLACE_PICKER_REQUEST = 1;
    private  int PLACE_PICKER_REQUEST_FOR_WORK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_places);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }

    void init(){

        homeLinearLayout = (LinearLayout) findViewById(R.id.homeLayout);
        workLinearLayout =(LinearLayout) findViewById(R.id.workLayout);
        workLocation = (TextView) findViewById(R.id.work_location);
        homeLocation = (TextView) findViewById(R.id.home_location);

        homeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(FavoritePlacesActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

       workLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(FavoritePlacesActivity.this), PLACE_PICKER_REQUEST_FOR_WORK);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg =  place.getName().toString();
                homeLocation.setText("HOME\n "+toastMsg);

            }
        }

        if (requestCode == PLACE_PICKER_REQUEST_FOR_WORK) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg =  place.getName().toString();
                workLocation.setText(toastMsg);

            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
