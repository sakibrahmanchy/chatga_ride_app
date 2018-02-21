package com.demoriderctg.arif.demorider.Dailog;

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


    private int progressStatus = 0;
    private Handler handler = new Handler();
    private NotificationModel notificationModel;
    private TextView cancel,count_number;
    private Main main;
    private SendNotification sendNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_driver);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        count_number = findViewById(R.id.count_second);
        cancel = (TextView) findViewById(R.id.cancel_search);
        main = new Main();
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        searchDriver();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchingDriver.this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to finish?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                    Intent intent = new Intent(SearchingDriver.this, MapActivity.class);
                                    startActivity(intent);
                                    finish();

                            }
                        }).create().show();
            }
        });
    }


    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
   */



    void searchDriver(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressStatus+=1;
                count_number.setText(progressStatus+"");
                if (AppConstant.INITIAL_RIDE_ACCEPT==1 ) {
                    progressStatus = 181;
                    sendNotification = new SendNotification(SearchingDriver.this);
                    sendNotification.Notification("FOUND","RIDER FOUND","Click to view rider");
                    Intent  intent = new Intent(SearchingDriver.this,OnrideModeActivity.class);
                    startActivity(intent);
                    fullMapActivity.finish();
                    finish();
                }
                else if(progressStatus <180){
                    handler.postDelayed(this, 1000);
                }
                else if(progressStatus>=180){
                    new ClearData();
                    new AlertDialog.Builder(SearchingDriver.this)
                            .setTitle("Rider Not Found")
                            .setMessage("Currently All the Rider Busy, Please search again.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
                                    startActivity(myIntent);
                                    fullMapActivity.finish();
                                    finish();
                                }
                            }).show();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    @Override
    public void onBackPressed() {

    }

}
