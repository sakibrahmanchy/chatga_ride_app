package com.demoriderctg.arif.demorider.Rating;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.models.ApiModels.Rating.RateDriver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Arif on 1/29/2018.
 */

public class UserRating {

    private Context mContext;
    private SharedPreferences pref;
    private ApiInterface apiService ;
    public UserRating(Context context) {
        this.mContext = context;
        apiService =   ApiClient.getClient().create(ApiInterface.class);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
    }

    public void RatingDriver(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RateDriver> call = apiService.rateRider(authHeader, AppConstant.HISTORY_ID,AppConstant.RATING);

        call.enqueue(new Callback<RateDriver>() {
            @Override
            public void onResponse(Call<RateDriver> call, Response<RateDriver> response) {

                int statusCode = response.code();

                switch(statusCode){
                    case 200:
                        if(response.body().isSuccess()){
                         double data = response.body().getData();
                        }
                        break;
                    case 500:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<RateDriver> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
