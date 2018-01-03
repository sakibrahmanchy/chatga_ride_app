package com.demoriderctg.arif.demorider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.models.ApiModels.AccessTokenModels.AuthToken;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginModel;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

/**
 * Created by Sakib Rahman on 12/13/2017.
 */

public class LoginHelper {

    String clientId;
    String clientSecret;
    String grantType;
    String APP_ID;

    EditText mPhoneNumberField;
    Button mStartButton;

    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public LoginHelper(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
    }

    public void AccessTokenCall(String clientId,String clientSecret,final String phoneNumber){

        dialog = new ProgressDialog(context);
        dialog.setMessage("Gaining Access To App..");
        dialog.show();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<AuthToken> call = apiService.getAccessToken(phoneNumber,clientId,clientSecret);

        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getStatus();
                        if(responseCode.equals("true")){
                            //No phone verification required, redirect to home
                            String accessToken = response.body().getAccessToken();
                            editor.putString("access_token",accessToken);
                            editor.commit();
                            LoginCall(phoneNumber);

                        }else{
                            Intent intent = new Intent(context, PhoneVerificationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                            context.startActivity(intent);
                        }
                        break;
                    default:
                        Intent intent = new Intent(context, PhoneVerificationActivity.class);
                        intent.putExtra("phoneNumber",phoneNumber);
                        intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                        context.startActivity(intent);
                        break;
                }

            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    public void LoginCall(final String phoneNumber){

        dialog = new ProgressDialog(context);
        dialog.setMessage("Logging in To App..");
        dialog.show();

        String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<LoginModel> call = apiService.loginUser(authHeader,phoneNumber, deviceToken);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                int statusCode = response.code();
                dialog.dismiss();

                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getResponseCode();
                        if(responseCode.equals("auth/logged-in-successfully")){
                            //No phone verification required, redirect to home
                            LoginData data = response.body().getLoginData();

                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            editor.putString("userData",json);
                            editor.putString("phoneNumber",phoneNumber);
                            editor.commit();

                            Intent intent = new Intent(context, MapActivity.class);
                            context.startActivity(intent);

                        }else{
                            Intent intent = new Intent(context, PhoneVerificationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("loginStatus","PHONE_VERIFICATION_REQUIRED");
                            context.startActivity(intent);
                        }
                        break;
                    default:
                        try {

                            JSONObject errorBody = new JSONObject(response.errorBody().string());
                            String errorResponseCode = errorBody.getString("response_code");
                            switch(errorResponseCode){
                                case "auth/phone-verification-required":
                                    Intent intent = new Intent(context, PhoneVerificationActivity.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    intent.putExtra("loginStatus","PHONE_VERIFICATION_REQUIRED");
                                    context.startActivity(intent);
                                    break;
                                default:

                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}


