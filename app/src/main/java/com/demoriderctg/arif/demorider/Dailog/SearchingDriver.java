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
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.R;
import com.google.firebase.database.ValueEventListener;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

public class SearchingDriver extends AppCompatActivity {


    private int progressStatus = 0;
    private Handler handler = new Handler();
    private NotificationModel notificationModel;
    private TextView cancel,count_number;
    private Main main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_driver);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        count_number = findViewById(R.id.count_second);

        main = new Main();
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
       searchDriver();
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
                    Intent  intent = new Intent(SearchingDriver.this,OnrideModeActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(progressStatus <18){
                    handler.postDelayed(this, 1000);
                }
                else if(progressStatus>=18){
                    AppConstant.INITIAL_RIDE_ACCEPT=0;

                    new AlertDialog.Builder(SearchingDriver.this)
                            .setTitle("Rider Not Found")
                            .setMessage("Currently All the Rider Busy, Please search again.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
                                    startActivity(myIntent);
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
