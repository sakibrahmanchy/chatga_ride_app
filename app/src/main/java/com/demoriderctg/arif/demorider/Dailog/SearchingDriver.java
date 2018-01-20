package com.demoriderctg.arif.demorider.Dailog;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    private TextView cancel;
    private Main main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_driver);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cancel = (TextView) findViewById(R.id.cancle);
        main = new Main();
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingDriver.this,MapActivity.class);
                startActivity(intent);
            }
        });
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
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus <=100) {
                    progressStatus += 1;

                    handler.post(new Runnable() {
                        public void run() {
                            if(notificationModel.body !=null){
                                progressStatus=101;
                                Intent  intent = new Intent(SearchingDriver.this,OnrideModeActivity.class);
                                startActivity(intent);
                                return;
                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
