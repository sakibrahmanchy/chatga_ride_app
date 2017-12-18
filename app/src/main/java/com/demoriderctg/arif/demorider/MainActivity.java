package com.demoriderctg.arif.DemoRider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demoriderctg.arif.DemoRider.LoginActivity;
import com.demoriderctg.arif.DemoRider.UserCheckActivity;
import com.demoriderctg.arif.demorider.R;
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
            // Intent intent = new Intent(MainActivity.this, MapActivity.class);
            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
            startActivity(intent);
            //  init();
        }
    }

    private void init() {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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
}
