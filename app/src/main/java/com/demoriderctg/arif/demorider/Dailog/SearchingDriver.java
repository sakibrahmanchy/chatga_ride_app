package com.demoriderctg.arif.demorider.Dailog;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.R;

public class SearchingDriver extends AppCompatActivity {


    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_driver);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus <=100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            if(progressStatus==50){
                                Intent  intent = new Intent(SearchingDriver.this,MapActivity.class);
                                startActivity(intent);
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

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
