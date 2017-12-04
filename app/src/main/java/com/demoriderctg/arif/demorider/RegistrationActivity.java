package com.demoriderctg.arif.demorider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.models.ApiModels.RegistrationModel;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;
import com.demoriderctg.arif.demorider.rest.ApiClient;
import com.demoriderctg.arif.demorider.rest.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static com.demoriderctg.arif.demorider.MainActivity.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends Activity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText Username;
    private View mProgressView;
    private View mLoginFormView;
    private EditText mPhoneNumber;
    private RadioGroup mGender;
    private EditText mConfirmPasswordView;
    private  String email,phoneNumber,userName,password,gender;
    private   ApiInterface apiService ;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPhoneNumber =(EditText) findViewById(R.id.phoneNumber);
        mGender = (RadioGroup) findViewById(R.id.gender_radio_group);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirmPassword);
        mPasswordView = (EditText) findViewById(R.id.password);
        Username = (EditText) findViewById(R.id.userName);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               if(attemptLogin()){
                   UserRegistration();
               }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    private boolean attemptLogin() {

        boolean ok=true;
        mEmailView.setError(null);
        mPasswordView.setError(null);
        Username.setError(null);
         email = mEmailView.getText().toString();
         password = mPasswordView.getText().toString();
         userName = Username.getText().toString();
        String confrimPassowrd = mConfirmPasswordView.getText().toString();
        int selectedId = mGender.getCheckedRadioButtonId();

        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
            ok=false;
        }

        // Check for a confirmposword, if the user entered one.
        if (!password.equals(confrimPassowrd)) {
            mPasswordView.setError("password not match");
            focusView = mPasswordView;
            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(userName)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = Username;
            cancel = true;
            ok=false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
            ok=false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
            ok=false;
        }

       return ok;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public void UserRegistration(){

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Date firstDate = new Date();
       // Call<RegistrationModel> call = apiService.signUpClient(userName,"shaikat",email,"01815003723",password,"","",gender);
        Call<RegistrationModel> call = apiService.signUpClient("","","","","","",firstDate,"");

        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                Snackbar.make(findViewById(android.R.id.content), testStatusCode,
                        Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getSuccess().toString();
                        if(responseCode.equals("user-found")){
                            //No phone verification required, redirect to home
                            Intent intent = new Intent(RegistrationActivity.this, UserCheckActivity.class);
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
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


}

