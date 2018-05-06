package com.demoriderctg.arif;

import android.content.SharedPreferences;
import android.util.Log;

import com.demoriderctg.arif.demorider.LoginHelper;
import com.demoriderctg.arif.demorider.UserInformation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private LoginHelper loginHelper;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    public void onTokenRefresh() {
        UserInformation userInformation = new UserInformation(this);
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        editor.putString("DeviceToken",recentToken);
        editor.apply();
        if (userInformation.getuserInformation() != null) {
            loginHelper = new LoginHelper(this);
            loginHelper.updateDeviceToken(userInformation.getuserInformation().getPhone(),recentToken);
            new Main().SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getClientModelInstance(),
                    recentToken
            );
            Log.d(FirebaseConstant.FIREBASE_REG_TOKEN, recentToken);
        }
    }
}
