package com.demoriderctg.arif.demorider.Setting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import __Firebase.FirebaseWrapper;

import static com.demoriderctg.arif.demorider.MainActivity.TAG;

public class EditProfile extends AppCompatActivity  {


    private ImageView editProfile,edit_profile;
    private EditText editProfileName;
    private EditText editEmail;
    private RadioGroup editGender;
    private Button profileUpdate;
    private EditText editPhoneNunber;
    private EditText editDate;
    private LoginData loginData;
    private RadioButton male,female;
    private DatePickerDialog birthDayPickerDialog;
    private SimpleDateFormat dateFormatter;
    private String deviceToken,email,firstName,gender,birthDate;

    private UserInformation userInformation;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        editProfile = (ImageView) findViewById(R.id.edit_profile_pic);
        editProfileName = (EditText) findViewById(R.id.edit_profile_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editGender =(RadioGroup) findViewById(R.id.edit_gender_radio_group);
        editDate =(EditText) findViewById(R.id.edit_birthday_edittext);
        editPhoneNunber = (EditText) findViewById(R.id.phone_number_view);
        profileUpdate = (Button) findViewById(R.id.profile_save);
        userInformation = new UserInformation(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        editPhoneNunber.setEnabled(false);
        male = (RadioButton) findViewById(R.id.male_radio_btn);
        female = (RadioButton) findViewById(R.id.female_radio_btn);
        viewAndEditProfile();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDate.setInputType(InputType.TYPE_NULL);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayPickerDialog.show();
            }
        });

        setDateTimeField();

    }

    private void viewAndEditProfile(){

        loginData = userInformation.getuserInformation();
        editProfileName.setText(loginData.firstName);
        editEmail.setText(loginData.getEmail());
        editPhoneNunber.setText(loginData.phone);
        if(loginData.getGender() !=null ){
            if(loginData.getGender().equals("male")){
               male.setChecked(true);
            }
           if(loginData.getGender().equals("female")){
                female.setChecked(true);
           }
        }
        editDate .setText(loginData.getBirthDate());


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImageIntent.setType("image/*");
                pickImageIntent.putExtra("crop", "true");
                pickImageIntent.putExtra("outputX", 200);
                pickImageIntent.putExtra("outputY", 200);
                pickImageIntent.putExtra("aspectX", 1);
                pickImageIntent.putExtra("aspectY", 1);
                pickImageIntent.putExtra("scale", true);
                pickImageIntent.putExtra("outputFormat",

                        Bitmap.CompressFormat.JPEG);
                startActivityForResult(pickImageIntent, RESULT_LOAD_IMAGE);
            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attemptLogin()){

                }
            }
        });

    }


    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();

        birthDayPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.d(TAG, dayOfMonth+"");
                newDate.set(year, monthOfYear, dayOfMonth);
                editDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            if(extras != null ) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                editProfile .setImageBitmap(photo);
            }

        }
    }

    private boolean attemptLogin() {

        boolean ok=true;
        editEmail.setError(null);
        editProfileName.setError(null);

        email = editEmail.getText().toString();
        firstName = editProfileName.getText().toString();
        deviceToken = FirebaseWrapper.getDeviceToken();
        birthDate = editDate.getText().toString();

        int selectedId = editGender.getCheckedRadioButtonId();

        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            editProfileName.setError(getString(R.string.error_field_required));
            focusView = editProfileName;

            cancel = true;
            ok=false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.error_field_required));
            focusView = editEmail;
            cancel = true;
            ok=false;
        }

        if(TextUtils.isEmpty(birthDate)){
            ok=false;
        }
        return ok;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }


}
