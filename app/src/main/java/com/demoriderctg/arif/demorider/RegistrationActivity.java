package com.demoriderctg.arif.demorider;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.app.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import android.widget.TextView;

import com.demoriderctg.arif.demorider.models.ApiModels.RegistrationModels.RegistrationModel;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import __Firebase.FirebaseWrapper;
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
    private EditText firstName;
    private EditText lastName;
    private View mProgressView;
    private View mLoginFormView;
    private RadioGroup mGender;
    private EditText mConfirmPasswordView;
    private  String email,phoneNumber, userFirstName,userLastName,password,gender,deviceToken,birthDate;
    private   ApiInterface apiService ;
    private ProgressDialog dialog;
    private EditText birthDayText;
    private DatePickerDialog birthDayPickerDialog;
    private SimpleDateFormat dateFormatter;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mGender = (RadioGroup) findViewById(R.id.gender_radio_group);
        firstName = (EditText) findViewById(R.id.userFirstName);
        lastName =  (EditText) findViewById(R.id.userLastName);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        birthDayText = (EditText) findViewById(R.id.birthday_edittext);
        birthDayText.setInputType(InputType.TYPE_NULL);
        birthDayText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayPickerDialog.show();
            }
        });

        setDateTimeField();

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
        firstName.setError(null);
        lastName.setError(null);
        birthDayText.setError(null);
        gender = "Male";

        email = mEmailView.getText().toString();
        userFirstName = firstName.getText().toString();
        userLastName = lastName.getText().toString();
        deviceToken = FirebaseWrapper.getDeviceToken();
        birthDate = birthDayText.getText().toString();

        int selectedId = mGender.getCheckedRadioButtonId();

        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userFirstName)) {
            firstName.setError(getString(R.string.error_field_required));
            focusView = firstName;

            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(userLastName)) {
            lastName.setError(getString(R.string.error_field_required));
            focusView = lastName;
            cancel = true;
            ok=false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
            ok=false;
        }
        if (TextUtils.isEmpty(birthDate)) {
            birthDayText.setError(getString(R.string.error_field_required));
            focusView = birthDayText;
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


    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();

        birthDayPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.d(TAG, dayOfMonth+"");
                newDate.set(year, monthOfYear+1, dayOfMonth);
                birthDayText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public void UserRegistration(){

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        String password = "";
        Call<RegistrationModel> call = apiService.signUpClient(userFirstName, userLastName,email,phoneNumber, password, deviceToken, birthDate);

        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                Snackbar.make(findViewById(android.R.id.content), testStatusCode,
                        Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
                switch(statusCode){

                    case 201:
                        String responseCode = response.body().getSuccess();

                        String clientId = getString(R.string.APP_CLIENT);
                        String clientSecret = getString(R.string.APP_CLIENT_SECRET);

                        LoginHelper loginHelper = new LoginHelper(RegistrationActivity.this);
                        loginHelper.AccessTokenCall(clientId, clientSecret,phoneNumber);
                        break;
                    case 406:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            JSONArray errors = error.getJSONArray("errors");
                            //String error = response.errorBody().string();
                            for (int i = 0; i < errors.length(); i++) {
                                JSONObject jsonObj = errors.getJSONObject(i);
                                String k = jsonObj.keys().next();
                                Log.i("Info",  jsonObj.getString(k));

                                Snackbar.make(findViewById(android.R.id.content), jsonObj.getString(k),
                                        Snackbar.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case 500:
                        try {
                            //JSONObject error = new JSONObject(response.errorBody().string());
                            //String errorCode = error.getString("message");
                            String error = response.errorBody().string();
                            Log.d(TAG, error);
                            Snackbar.make(findViewById(android.R.id.content), error,
                                    Snackbar.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),

                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        try {
                            String error = response.errorBody().string();
                            Log.d(TAG, error);
//                            String errorCode = error.getString("message");
                            Snackbar.make(findViewById(android.R.id.content), error,
                                    Snackbar.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }

                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


}

