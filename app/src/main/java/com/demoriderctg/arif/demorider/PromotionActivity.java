package com.demoriderctg.arif.demorider;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.Adapters.History.HistoryAdapter;
import com.demoriderctg.arif.demorider.Adapters.Promotion.PromotionAdapter;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistoryResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.ApplyPromoCodeResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscountResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class PromotionActivity extends AppCompatActivity {

    RecyclerView rv;
    SwipeRefreshLayout swiper;
    PromotionAdapter adapter;

    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayList<UserDiscounts> userDiscounts;;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserInformation userInformation;
    private Button applyPromoCodeButton;
    private EditText promoCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userInformation = new UserInformation(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_discount);

        rv = (RecyclerView) findViewById(R.id.discount_recycler_view);
        //  dialog = new ProgressDialog(this);

        rv.setLayoutManager(new LinearLayoutManager(this));

        promoCodeText = (EditText) findViewById(R.id.edit_text_promo_code);
        applyPromoCodeButton = (Button) findViewById(R.id.apply_promo_code_button);

        applyPromoCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyPromotionCode();
            }
        });

        getClientPromotions();
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
                        getClientPromotions();
                    }
                }, 3000);
            }
        });


    }

    public void applyPromotionCode(){

        String promocode = promoCodeText.getText().toString();
        LoginData loginData = userInformation.getuserInformation();
        String userId = loginData.getUserId();
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<ApplyPromoCodeResponse> call = apiService.applyPromoCode(authHeader,promocode,userId);

        call.enqueue(new Callback<ApplyPromoCodeResponse>() {
            @Override
            public void onResponse(Call<ApplyPromoCodeResponse> call, Response<ApplyPromoCodeResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            Toast.makeText(getApplicationContext(),response.body().getMessage()+"",Toast.LENGTH_LONG).show();
                            getClientPromotions();
                        }else{
                            Toast.makeText(getApplicationContext(),response.body().getMessage()+"",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                            Toast.makeText(getApplicationContext(),"Error Occurred!",Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<ApplyPromoCodeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



    }


    public void getClientPromotions(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        LoginData loginData = userInformation.getuserInformation();
        Call<UserDiscountResponse> call = apiService.getUserDiscounts(authHeader,loginData.getUserId());

        call.enqueue(new Callback<UserDiscountResponse>() {
            @Override
            public void onResponse(Call<UserDiscountResponse> call, Response<UserDiscountResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            userDiscounts = response.body().getData();
                            adapter = new PromotionAdapter(getApplicationContext(),userDiscounts);
                            rv.setAdapter(adapter);
                        }else{
                        }
                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onFailure(Call<UserDiscountResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}
