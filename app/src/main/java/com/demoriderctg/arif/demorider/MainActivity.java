package com.demoriderctg.arif.demorider;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.CurrentDateTimeFromServer.CurrentDateTime;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.InternetConnection.InternetCheckActivity;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.facebook.accountkit.AccountKit;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.UUID;

import io.fabric.sdk.android.Fabric;

public class    MainActivity extends AppCompatActivity {

    public static final String TAG = "Mainactivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    public static boolean check = false;
    private SharedPreferences pref;
    private ConnectionCheck connectionCheck;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static boolean IS_MAP_INITIALIZE = false;
    public static Context context = null;
    private LoginData loginData;
    private  UserInformation userInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        connectionCheck = new ConnectionCheck(this);
        new ActiveContext(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        if (pref.getString("userData", null) != null) {
            requestForSpecificPermission();


        } else {
            setContentView(R.layout.activity_main);
            if (isServiceOk()) {
                init();
            }
        }

    }

    private void init() {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (!checkIfAlreadyhavePermission()) {
                        requestForSpecificPermission();
                    } else {
                        Intent intent = new Intent(MainActivity.this, FacebookAccountVerificationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, FacebookAccountVerificationActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    public boolean isServiceOk() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "you can not make request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.INTERNET_CHECK) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3]==PackageManager.PERMISSION_GRANTED) {
                   if(loginData !=null){
                       this.getDeviceID();
                       if (connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()) {
                           Intent intent = new Intent(MainActivity.this, MapActivity.class);
                           startActivity(intent);
                           finish();
                       } else {
                           Intent intent = new Intent(this, InternetCheckActivity.class);
                           startActivity(intent);
                           finish();
                       }

                   }
                   else{
                       Intent intent = new Intent(MainActivity.this, FacebookAccountVerificationActivity.class);
                       startActivity(intent);
                       finish();
                   }

                } else {
                    //not granted
                    Toast.makeText(getApplicationContext(), "Please Restart Application", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void getDeviceID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.   
            return;
        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
       // AppConstant.SESSION_KEY = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppConstant.SESSION_KEY =deviceId;
    }

    public static Context getMainActivityContext(){
        return context;
    }
}



