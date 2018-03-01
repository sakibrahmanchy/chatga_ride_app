package com.demoriderctg.arif.demorider;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.models.ApiModels.DeviceTokenModels.UpdateDeviceTokenData;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;

import org.json.JSONObject;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;


public class FacebookAccountVerificationActivity extends AppCompatActivity {

    public String TAG = "FacebookAccountVerificationActivity";
    private ProgressDialog dialog;
    private String phoneNumber;
    UIManager uiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_account_verification);
//        AccessToken accessToken = AccountKit.getCurrentAccessToken();
//
//        if (accessToken != null) {
//            //Handle Returning User
//        } else {
//            //Handle new or logged out user
//        }

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FacebookAccountVerificationActivity.this,
                            new String[]{android.Manifest.permission.READ_SMS},AppConstant.GET_SMS_PERMISSION);
        }
        else{
            startAccountVerification();
        }



    }

    public void startAccountVerification(){
            final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN

        uiManager = new SkinManager(
                SkinManager.Skin.TRANSLUCENT,
                getResources().getColor(R.color.colorAccent));
        configurationBuilder.setUIManager(uiManager);
        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                String error = loginResult.getError().toString();
                Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG);
                //showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();


                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        String accountKitId = account.getId();
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = phoneNumber.toString().substring(3,phoneNumber.toString().length());
                        UserExists(phoneNumberString);

                    }

                    @Override
                    public void onError(final AccountKitError error) {
                        // Handle Error
                        Log.d(TAG,error.toString());
                    }
                });
               // deviceTokenCheck(phoneNumber);
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    startAccountVerification();
                }
            }
        }
    }

    public void deviceTokenCheck(String phone){

        final String phoneNumner = phone;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);

        if(getIntent().getStringExtra("loginStatus").equals("REGISTRATION_REQUIRED")){
            Intent intent = new Intent(FacebookAccountVerificationActivity.this, RegistrationActivity.class);
            intent.putExtra("phoneNumber",phoneNumber);
            startActivity(intent);
        }else if(getIntent().getStringExtra("loginStatus").equals("PHONE_VERIFICATION_REQUIRED")){
            Log.e(TAG, pref.getString("access_token",null));
            dialog = new ProgressDialog(FacebookAccountVerificationActivity.this);
            dialog.setMessage("Saving your new device..");
            dialog.show();

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            String authHeader = "Bearer "+pref.getString("access_token",null);
            String deviceToken = FirebaseWrapper.getDeviceToken();
            Call<UpdateDeviceTokenData> call = apiService.updateDeviceToken(authHeader,phoneNumber, deviceToken);

            call.enqueue(new Callback<UpdateDeviceTokenData>() {
                @Override
                public void onResponse(Call<UpdateDeviceTokenData> call, Response<UpdateDeviceTokenData> response) {

                    int statusCode = response.code();
                    dialog.dismiss();

                    switch(statusCode){
                        case 200:

                            boolean responseCode = response.body().getStatus();
                            if(responseCode){
                                //No phone verification required, redirect to home

                                String clientId = getString(R.string.APP_CLIENT);
                                String clientSecret = getString(R.string.APP_CLIENT_SECRET);

                                LoginHelper loginHelper = new LoginHelper(FacebookAccountVerificationActivity.this);
                                loginHelper.AccessTokenCall(clientId, clientSecret,phoneNumber);
                            }
                            break;
                        case 500:
                            Log.d(TAG, response.errorBody().toString());
                        default:
                            Log.d(TAG, response.errorBody().toString());
                            Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<UpdateDeviceTokenData> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, "Failure "+t.toString());
                }
            });
        }

    }

    public void UserExists(final String phoneNumber){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(FacebookAccountVerificationActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        String clientId = getString(R.string.APP_CLIENT);
        String clientSecret = getString(R.string.APP_CLIENT_SECRET);

        Call<UserCheckResponse> call = apiService.checkUser(phoneNumber);

        call.enqueue(new Callback<UserCheckResponse>() {
            @Override
            public void onResponse(Call<UserCheckResponse> call, Response<UserCheckResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getResponseCode().toString();
                        if(responseCode.equals("user-found")){
                            //No phone verification required, call for access token
                            LoginHelper loginHelper = new LoginHelper(FacebookAccountVerificationActivity.this);
                            loginHelper.AccessTokenCall(clientId,clientSecret,phoneNumber);

                        }else{

                            Intent intent = new Intent(FacebookAccountVerificationActivity.this, RegistrationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            String errorCode = error.getString("response_code");

                            if(errorCode.equals("auth/user-not-found")){

                                Intent intent = new Intent(FacebookAccountVerificationActivity.this, RegistrationActivity.class);
                                intent.putExtra("phoneNumber",phoneNumber);
                                intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserCheckResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}
