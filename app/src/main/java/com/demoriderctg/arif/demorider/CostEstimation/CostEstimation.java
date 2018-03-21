package com.demoriderctg.arif.demorider.CostEstimation;

import android.content.Context;
import android.util.Log;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.AppSettingModels.AppSettings;

/**
 * Created by Arif on 1/4/2018.
 */

public class CostEstimation {

    private double distance;
    private double duration;
    private double total;
    private UserInformation userInformation;
    private AppSettings appSettings;
    private Context mContex;
    public CostEstimation(Context context) {
        this.mContex =context;
        userInformation = new UserInformation(mContex);
    }

   public void getTotalCost(String Stringdistance,String Stringduration){
        appSettings = userInformation.getAppSettings();
        double distance = getDistance(Stringdistance);
        double duration = getDuration(Stringduration);
        double totalCost=0;
        if(AppConstant.userDiscount !=null){
            if(AppConstant.userDiscount.getDiscountPercentage()>0){
                AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT= totalCost = appSettings.getBaseFare()+appSettings.getPricePerKm()*distance + appSettings.getPricePerMinute()*duration;
                AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT = totalCost-(totalCost *(AppConstant.userDiscount.getDiscountPercentage()/100.0));
            }
            else if(AppConstant.userDiscount.getDiscountAmount()>0){
              AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT=  totalCost =  appSettings.getBaseFare()+appSettings.getPricePerKm()*distance + appSettings.getPricePerMinute()*duration;
               AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT = totalCost - AppConstant.userDiscount.getDiscountAmount();
            }
        }
        else{
            AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT =  appSettings.getBaseFare()+appSettings.getPricePerKm()*distance + appSettings.getPricePerMinute()*duration;
        }
        if(AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT<appSettings.getMinimumFare()){
            AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT =appSettings.getMinimumFare();
        }
        if(AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT<appSettings.getMinimumFare() && AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT !=0  ){
            AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT=appSettings.getMinimumFare();
        }


    }

    public double getDistance(String StringDistance){
             String value="0";
             double distance =0;
             try {

                 for(int i=0; i<StringDistance.length()-3; i++){
                     value+=StringDistance.charAt(i);
                 }

                  distance =  Double.parseDouble(value);
             }catch (Exception e){
                 Log.d("Distance Execption",e+"");
             }

       return distance;
    }


    public long getDuration(String StringDuration){
        String value="0";
        long duration =0;
        try{
            for(int i=0; i<StringDuration.length()-4; i++){
                value+=StringDuration.charAt(i);
            }

            duration = (long) Double.parseDouble(value);
        }
        catch (Exception e){
            Log.d("Duration Execption",e+"");
        }

        return duration;
    }

    public int TotalCost(int minutes, double distance){

           return (int) (AppConstant.BASE_TAKA +(distance*10) +minutes*.5);
    }
}
