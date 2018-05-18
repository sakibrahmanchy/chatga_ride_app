package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.ActiveContext;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.ClearData.ClearData;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.LoginHelper;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.OnrideMode.SendNotification;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.RegistrationActivity;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserCheckActivity;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;
import com.demoriderctg.arif.demorider.models.RideCancelModels.RideCancelReason;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseResponse.RideCanceledByRider;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.demoriderctg.arif.demorider.Dailog.FullMapSearching.fullMapActivity;
import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class SearchingDriver extends AppCompatActivity {

    private Handler handler = new Handler();
    private TextView cancel;
    public static Activity searchActivity;
    private UserInformation  userInformation;
    private ArrayList<RideCancelReason> rideCancelReasons;
    private HashMap<String,String> reasonMap;
    private ProgressDialog dialog;
    private   ApiInterface apiService ;
    private LoginData loginData;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searching_driver);
        userInformation = new UserInformation(this);
        rideCancelReasons = userInformation.getRideCancelReasons();
        reasonMap = new HashMap<String,String>();
        loginData = userInformation.getuserInformation();
        pref = getSharedPreferences("MyPref", 0);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel = (TextView) findViewById(R.id.cancel_search);
        searchActivity=this;
        new ActiveContext(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchingDriver.this);
                builderSingle.setTitle("Cancellation reason?");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchingDriver.this, android.R.layout.simple_list_item_1);

                for(int i=0; i<rideCancelReasons.size(); i++){
                    arrayAdapter.add(rideCancelReasons.get(i).getCancelReason());
                    reasonMap.put(rideCancelReasons.get(i).getCancelReason(),rideCancelReasons.get(i).getId());
                }

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(SearchingDriver.this);
                        int currentId = Integer.parseInt(reasonMap.get(reason));
                        CancelReason(currentId,Integer.parseInt(loginData.getClientId()),reason,AppConstant.SOURCE_NAME);
                        new ClearData();
                        FirebaseConstant.VAR_CAN_REQUEST_FOR_RIDE = true;
                        Intent intent = new Intent(SearchingDriver.this, MapActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
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

    public void CancelReason(int cancelId, int userId, String cancelReason, String areaName ){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<Void> call = apiService.logRideCancel(authHeader,cancelId+"",userId+"",cancelReason,areaName);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                int statusCode = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
