package com.demoriderctg.arif;

import android.util.Log;

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
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        new Main(this).SetDeviceTokenToRiderTable(
                FirebaseWrapper.getInstance().getClientModelInstance(),
                recentToken
        );
        Log.d(FirebaseConstant.FIREBASE_REG_TOKEN, recentToken);
    }
}
