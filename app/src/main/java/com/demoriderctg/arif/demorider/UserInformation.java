package com.demoriderctg.arif.demorider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.VmModels.VmCurrentLocation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.google.gson.Gson;

public class UserInformation {

    private Gson gson;
    private SharedPreferences sharedpreferences;
    private LoginData loginData;
    private  HomeLocationModel homeLocationModel;
    private WorkLocationModel workLocationModel;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences.Editor editor;
    private VmCurrentLocation vmCurrentLocation;

    public UserInformation(Context context) {
        gson = new Gson();
        sharedpreferences = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = sharedpreferences.edit();

    }

    public LoginData getuserInformation() {

        String jsonString = sharedpreferences.getString("userData", null);
        loginData = gson.fromJson(jsonString, LoginData.class);
        return loginData;
    }

    public String getRiderPhoneNumber(){
        String phoneNumber = sharedpreferences.getString("phoneNumber", null);
        return phoneNumber;
    }

    public HomeLocationModel getUserHomeLocation(){
        String jsonString = sharedpreferences.getString("UserSetHomeLocation", null);
        homeLocationModel = gson.fromJson(jsonString, HomeLocationModel.class);
        return homeLocationModel;
    }

    public WorkLocationModel getUserWorkLocation(){
        String jsonString = sharedpreferences.getString("UserSetWorkLocation", null);
        workLocationModel = gson.fromJson(jsonString, WorkLocationModel.class);
        return workLocationModel;
    }
    public VmCurrentLocation getUserCurrentLocation(){
        String jsonString = sharedpreferences.getString("currentLocation", null);
        vmCurrentLocation = gson.fromJson(jsonString, VmCurrentLocation.class);
        return vmCurrentLocation;
    }
    public void RemoveLoginData(){
        editor.remove("userData");
        editor.commit();
    }
}
