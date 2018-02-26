package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.ClearData.ClearData;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.OnrideMode.SendNotification;
import com.demoriderctg.arif.demorider.R;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;


import static com.demoriderctg.arif.demorider.Dailog.FullMapSearching.fullMapActivity;

public class SearchingDriver extends AppCompatActivity {

    private Handler handler = new Handler();
    private TextView cancel;
    public static Activity searchActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_driver);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel = (TextView) findViewById(R.id.cancel_search);
        searchActivity=this;


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchingDriver.this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to finish?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                new ClearData();
                                    Intent intent = new Intent(SearchingDriver.this, MapActivity.class);
                                    startActivity(intent);
                                    finish();

                            }
                        }).create().show();
            }
        });
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConstant.SEARCH_ACTIVITY=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConstant.SEARCH_ACTIVITY=false;
    }
}
