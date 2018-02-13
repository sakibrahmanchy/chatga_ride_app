package com.demoriderctg.arif.demorider.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demoriderctg.arif.demorider.FavoritePlaces.FavoritePlacesActivity;
import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.google.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class SettingActivity extends AppCompatActivity {

    private ImageView profileImage;
    private ImageView editProfile;
    private TextView profileName;
    private TextView phoneNumber;
    private TextView email;
    private TextView homeLocation;
    private TextView workLocation;
    private TextView signOut;
    private UserInformation userInformation;
    private LoginData loginData;
    private  int PLACE_PICKER_REQUEST = 1;
    private  int PLACE_PICKER_REQUEST_FOR_WORK = 2;
    private HomeLocationModel homeLocationModel =new HomeLocationModel();
    private WorkLocationModel workLocationModel = new WorkLocationModel();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        pref = this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();


        profileImage = (ImageView) findViewById(R.id.profile_pic);
        profileName = (TextView) findViewById(R.id.profile_name);
        phoneNumber = (TextView) findViewById(R.id.profile_phone);
        email = (TextView) findViewById(R.id.profile_email);
        homeLocation = (TextView) findViewById(R.id.home_location);
        workLocation = (TextView) findViewById(R.id.work_location);
        signOut = (TextView) findViewById(R.id.action_logout);
        editProfile = (ImageView) findViewById(R.id.edit_profile);

        setAllInformation();
        setFovaritesLocation();

    }

    private void setAllInformation(){
        profileName.setText(loginData.firstName);
        phoneNumber.setText("+88"+ loginData.phone);
        email.setText(loginData.email);
        if(userInformation.getUserHomeLocation() !=null){
            homeLocation.setText(userInformation.getUserHomeLocation().homeLocationName);
        }
        if(userInformation.getUserWorkLocation() !=null){
            workLocation.setText(userInformation.getUserWorkLocation().workLocationName);
        }

        String url = loginData.getAvatar();
        Picasso.with(this).invalidate(url);
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(profileImage);

    }

    private void setFovaritesLocation(){
        homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SettingActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        workLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SettingActivity.this), PLACE_PICKER_REQUEST_FOR_WORK);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg =  place.getAddress().toString();
                homeLocation.setText(""+toastMsg);
                homeLocationModel.home=place.getLatLng();
                homeLocationModel.homeLocationName= place.getAddress().toString();
                Gson gson = new Gson();
                String json = gson.toJson(homeLocationModel);
                editor.putString("UserSetHomeLocation",json);
                editor.commit();

            }
        }

        else if (requestCode == PLACE_PICKER_REQUEST_FOR_WORK) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg =  place.getAddress().toString();
                workLocation.setText(toastMsg);
                workLocationModel.work=place.getLatLng();
                workLocationModel.workLocationName=place.getAddress().toString();
                Gson gson = new Gson();
                String json = gson.toJson(workLocationModel);
                editor.putString("UserSetWorkLocation",json);
                editor.commit();

            }
        }
    }
}
