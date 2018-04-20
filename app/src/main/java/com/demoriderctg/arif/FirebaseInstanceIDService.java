package com.demoriderctg.arif;

import android.util.Log;

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

    @Override
    public void onTokenRefresh() {
        UserInformation userInformation = new UserInformation(this);
        if (userInformation.getuserInformation() != null) {
            String recentToken = FirebaseInstanceId.getInstance().getToken();
            new Main().SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getClientModelInstance(),
                    recentToken
            );
            Log.d(FirebaseConstant.FIREBASE_REG_TOKEN, recentToken);
        }
    }
}
