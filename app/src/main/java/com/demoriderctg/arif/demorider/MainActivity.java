package com.demoriderctg.arif.demorider;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {


    public  static  final String TAG ="Mainactivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        if(isServiceOk()){
//           // Intent intent = new Intent(MainActivity.this, MapActivity.class);
//            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
//            startActivity(intent);
//
//
//        }
    }
private  void init(){

  Button btnLogin =(Button)findViewById(R.id.btnLogin);

    /*btnmp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }
    });*/

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
            startActivity(intent);
        }
    });
}

    public  boolean isServiceOk(){
        Log.d(TAG,"isServiceOk: check google service version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //Every thing is ok and user can make map service

            Log.d(TAG,"isServiceOk: Google play service is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occur but we can resolve it
            Log.d(TAG,"isServiceOk: resolveable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else {
            Toast.makeText(this,"you can not make request",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
