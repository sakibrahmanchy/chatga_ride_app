package com.demoriderctg.arif.demorider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demoriderctg.arif.demorider.models.ApiModels.AuthToken;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;
import com.demoriderctg.arif.demorider.rest.ApiClient;
import com.demoriderctg.arif.demorider.rest.ApiInterface;

import org.json.JSONObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class UserCheckActivity extends Activity {

    String clientId;
    String clientSecret;
    String grantType;
    String APP_ID;

    EditText mPhoneNumberField;
    Button mStartButton;

    private ProgressDialog dialog;
    private   ApiInterface apiService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);

        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mStartButton = (Button) findViewById(R.id.button_start_verification);

        clientId = getString(R.string.APP_CLIENT);
        clientSecret = getString(R.string.APP_CLIENT_SECRET);
        grantType = getString(R.string.GRANT_TYPE);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = "88"+mPhoneNumberField.getText().toString();
                UserExists(phoneNumber);

            }
        });

    }

    public String GenerateAppId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid;
    }

    public void UserExists(final String phoneNumber){

         apiService =
                ApiClient.getClient().create(ApiInterface.class);

        APP_ID = GenerateAppId();
        dialog = new ProgressDialog(UserCheckActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Call<UserCheckResponse> call = apiService.checkUser(phoneNumber);

        call.enqueue(new Callback<UserCheckResponse>() {
            @Override
            public void onResponse(Call<UserCheckResponse> call, Response<UserCheckResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                Snackbar.make(findViewById(android.R.id.content), testStatusCode,
                        Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getResponseCode().toString();
                        if(responseCode.equals("user-found")){
                            //No phone verification required, redirect to home
                           Intent intent = new Intent(UserCheckActivity.this, RegistrationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                              startActivity(intent);

                        }else{
                            Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            String errorCode = error.getString("response_code");
                            Snackbar.make(findViewById(android.R.id.content), errorCode,
                                    Snackbar.LENGTH_SHORT).show();

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
//                if(status.equals("true") && statusCode == 200){
//                    Intent intent = new Intent(UserCheckActivity.this, MapActivity.class);
//                    intent.putExtra("phoneNumber",phoneNumber);
//                    startActivity(intent);
//                }else{
//                    Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
//                            Snackbar.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<UserCheckResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }





}

