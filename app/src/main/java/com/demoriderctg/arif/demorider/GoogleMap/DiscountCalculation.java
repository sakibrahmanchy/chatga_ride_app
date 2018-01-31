package com.demoriderctg.arif.demorider.GoogleMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.demoriderctg.arif.demorider.Adapters.Promotion.PromotionAdapter;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscountResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

/**
 * Created by Arif on 1/28/2018.
 */

public class DiscountCalculation {
    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private UserInformation userInformation;
    private ArrayList<UserDiscounts> userDiscounts;
    private  Context mContex;
    public DiscountCalculation(Context context) {
        this.mContex = context;
        userInformation = new UserInformation(mContex);
        pref = mContex.getSharedPreferences("MyPref", 0);
    }

    public void getClientPromotions(){
        dialog = new ProgressDialog(mContex);
        dialog.setMessage("Please wait...");
        dialog.show();

        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        LoginData loginData = userInformation.getuserInformation();
        Call<UserDiscountResponse> call = apiService.getUserDiscounts(authHeader,loginData.getClientId());

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
                            if(userDiscounts.size()>0){
                                AppConstant.userDiscount = userDiscounts.get(0);
                            }

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
