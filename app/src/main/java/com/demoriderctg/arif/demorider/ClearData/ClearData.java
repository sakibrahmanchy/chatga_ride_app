package com.demoriderctg.arif.demorider.ClearData;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;

import java.util.Calendar;

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by Arif on 2/15/2018.
 */

public class ClearData {

    public ClearData(){
       AppConstant.FINISH_RIDE=false;
        AppConstant. INITIAL_RIDE_ACCEPT=0;
        AppConstant. FINAL_RIDE_COST=0;
        AppConstant. START_RIDE=false;
        AppConstant. TREAD_FOR_FINISH_RIDE=true;
        AppConstant.userDiscount = null;
        AppConstant. RATING =0;
        AppConstant. TOTAL_COST = 0;
        AppConstant.currentRidingHistoryModel = null;
        AppConstant. riderModel = null;
        AppConstant. IS_RIDE=2;
        AppConstant. SOURCE_LATITUTE=0;
        AppConstant. SOURCE_LOGITUTE=0;
        AppConstant. DESTINATION_LATITUTE=0;
        AppConstant. DESTINATION_LOGITUTE=0;
        AppConstant. RIDER_NAME=null;
        AppConstant. RIDER_PHONENUMBER= null;
        AppConstant. RIDER_BIKE_NUMBER=null;
    }
}
