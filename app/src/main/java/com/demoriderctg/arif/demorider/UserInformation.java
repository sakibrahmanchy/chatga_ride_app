package com.demoriderctg.arif.demorider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Arif on 12/16/2017.
 */

public class UserInformation  {

    private Gson gson;
    private SharedPreferences sharedpreferences;
    private  LoginData loginData;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public UserInformation(Context context) {
        gson = new Gson();

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
   LoginData getuserInformation(){

        String jsonString =sharedpreferences.getString("userData",null);
       loginData = gson.fromJson(jsonString, LoginData.class);
      return  loginData;
   }

}
