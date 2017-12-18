package com.demoriderctg.arif.DemoRider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demoriderctg.arif.DemoRider.AppConfig.AppConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Mainactivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServiceOk()) {
            init();
        }
    }

    private void init() {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOk() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(AppConstant.GOOGLE_PLAY_SERVICE_OK, AppConstant.GOOGLE_PLAY_SERVICE_OK);
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(AppConstant.GOOGLE_PLAY_SERVICE_ERR, AppConstant.GOOGLE_PLAY_SERVICE_ERR);
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        } else {
            Toast.makeText(this, AppConstant.GOOGLE_PLAY_SERVICE_MSG, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
