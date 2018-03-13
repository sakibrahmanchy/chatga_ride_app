package com.demoriderctg.arif.demorider.Adapters.History;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demoriderctg.arif.demorider.PhoneVerificationActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.AccessTokenModels.AuthToken;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginModel;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistoryResponse;
import com.ramotion.foldingcell.FoldingCell;


import java.util.ArrayList;
import java.util.List;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class ClientHistoryActivity extends AppCompatActivity {


    RecyclerView rv;
    SwipeRefreshLayout swiper;
    HistoryAdapter adapter;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayList<ClientHistory> clientHistories;;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserInformation userInformation;
    ListView theListView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userInformation = new UserInformation(this);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        rv = (RecyclerView) findViewById(R.id.history_recycler_view);
//      //  dialog = new ProgressDialog(this);
       theListView = findViewById(R.id.mainListView);

       // rv.setLayoutManager(new LinearLayoutManager(this));

        try{
            getClientHistory();
        }catch (Exception e){
            e.printStackTrace();
        }


//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                // implement Handler to wait for 3 seconds and then update UI means update value of TextView
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // cancle the Visual indication of a refresh
//                        swipeRefreshLayout.setRefreshing(false);
//                        // Generate a random integer number
//                        getClientHistory();
//                    }
//                }, 3000);
//            }
//        });


    }

    public void getClientHistory(){
        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

      

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        LoginData loginData = userInformation.getuserInformation();
        Call<ClientHistoryResponse> call = apiService.getClientHistory(authHeader,loginData.getClientId());

        call.enqueue(new Callback<ClientHistoryResponse>() {
            @Override
            public void onResponse(Call<ClientHistoryResponse> call, Response<ClientHistoryResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            clientHistories = response.body().getData();
                            final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getApplicationContext(), clientHistories);
                            theListView.setAdapter(adapter);

                            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                    // toggle clicked cell state
                                    ((FoldingCell) view).toggle(false);
                                    // register in adapter that state for selected cell is toggled
                                    adapter.registerToggle(pos);
                                }
                            });
                        }else{
                        }
                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onFailure(Call<ClientHistoryResponse> call, Throwable t) {
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
