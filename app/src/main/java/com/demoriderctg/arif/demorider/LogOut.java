package com.demoriderctg.arif.demorider;

import android.content.Context;
import android.content.Intent;

import com.demoriderctg.arif.demorider.ClearData.ClearData;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;

import ContactWithFirebase.Main;

/**
 * Created by Arif on 3/29/2018.
 */

public class LogOut {

    private UserInformation userInformation;
    private Context mContext;
    public LogOut(Main context){
        this.mContext = context;
        userInformation = new UserInformation(MapActivity.contextOfApplication);
    }

    public  void logOutFromApp(){
        userInformation.RemoveLoginData();
        new ClearData();
    }
}
