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
import android.widget.ArrayAdapter;
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
import __Firebase.FirebaseUtility.FirebaseConstant;
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
//                new AlertDialog.Builder(SearchingDriver.this)
//                        .setTitle("Really Exit?")
//                        .setMessage("Are you sure you want to finish?")
//                        .setNegativeButton(android.R.string.no, null)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchingDriver.this, android.R.layout.select_dialog_singlechoice);
//                                arrayAdapter.add("Hardik");
//                                arrayAdapter.add("Archit");
//                                arrayAdapter.add("Jignesh");
//                                arrayAdapter.add("Umang");
//                                arrayAdapter.add("Gatti");
//
//                                new ClearData();
//                                FirebaseConstant.VAR_CAN_REQUEST_FOR_RIDE = true;
//                                    Intent intent = new Intent(SearchingDriver.this, MapActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                            }
//                        }).create().show();

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchingDriver.this);
                builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Select One Name:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchingDriver.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Hardik");
                arrayAdapter.add("Archit");
                arrayAdapter.add("Jignesh");
                arrayAdapter.add("Umang");
                arrayAdapter.add("Gatti");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(SearchingDriver.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
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
