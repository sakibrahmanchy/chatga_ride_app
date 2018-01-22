package com.demoriderctg.arif.demorider.CurrentDateTimeFromServer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.demoriderctg.arif.demorider.LoginHelper;
import com.demoriderctg.arif.demorider.PhoneVerificationActivity;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserCheckActivity;
import com.demoriderctg.arif.demorider.models.ApiModels.DateTimeModel.DateTimeResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

/**
 * Created by Arif on 1/21/2018.
 */

public class CurrentDateTime {

    private ProgressDialog dialog;
    private   ApiInterface apiService ;
    private  Context mContext;
    private Long time;
    public CurrentDateTime(Context context) {
        this.mContext =context;
    }

    public  long getCurrentDateTime(){
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Call<DateTimeResponse> call = apiService.getDateTime();

        call.enqueue(new Callback<DateTimeResponse>() {
            @Override
            public void onResponse(Call<DateTimeResponse> call, Response<DateTimeResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        time = response.body().getData();
                        break;
                    case 500:

                        break;

                    default:
                         time= System.currentTimeMillis();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DateTimeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        return time;
    }
}
