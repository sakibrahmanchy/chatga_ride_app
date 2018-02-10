package com.demoriderctg.arif.demorider;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.Adapters.Notification.NotificationAdapter;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.NotificationModels.Notification;
import com.demoriderctg.arif.demorider.models.ApiModels.NotificationModels.NotificationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView rv;
    SwipeRefreshLayout swiper;
    NotificationAdapter adapter;

    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Notification> notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_notification);

        rv = (RecyclerView) findViewById(R.id.notification_recycler_view);
        //  dialog = new ProgressDialog(this);

        rv.setLayoutManager(new LinearLayoutManager(this));



        try {
            getClientNotifications();
        }catch (Exception e){
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // implement Handler to wait for 3 seconds and then update UI means update value of TextView
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cancle the Visual indication of a refresh
                        swipeRefreshLayout.setRefreshing(false);
                        // Generate a random integer number
                        getClientNotifications();
                    }
                }, 3000);
            }
        });


    }


    public void getClientNotifications(){
        dialog = new ProgressDialog(this);
        dialog.dismiss();
        dialog.setMessage("Please wait...");
        dialog.show();

        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<NotificationResponse> call = apiService.getClientNotifications(authHeader);

        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            notifications = response.body().getData();
                            adapter = new NotificationAdapter(getApplicationContext(),notifications);
                            rv.setAdapter(adapter);
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occurred!",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Error Occurred!",Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }



}
