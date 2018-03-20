package com.demoriderctg.arif.demorider.CostEstimation;

import android.util.Log;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

/**
 * Created by Arif on 1/4/2018.
 */

public class CostEstimation {

    private double distance;
    private double duration;
    private double total;
    public CostEstimation() {

    }

   public void getTotalCost(String Stringdistance,String Stringduration){
        double distance = getDistance(Stringdistance);
        double duration = getDuration(Stringduration);
        double totalCost=0;
        if(AppConstant.userDiscount !=null){
            if(AppConstant.userDiscount.getDiscountPercentage()>0){
                AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT= totalCost =  AppConstant.BASE_TAKA+AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
                AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT= totalCost = totalCost-(totalCost *(AppConstant.userDiscount.getDiscountPercentage()/100.0));
            }
            else if(AppConstant.userDiscount.getDiscountAmount()>0){
              AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT=  totalCost =  AppConstant.BASE_TAKA+AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
               AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT= totalCost = totalCost - AppConstant.userDiscount.getDiscountAmount();
            }
        }
        else{
            AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT=totalCost =  AppConstant.BASE_TAKA+AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
        }
        if(AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT<40){
            AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT =40;
        }
        if(AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT<40 && AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT !=0  ){
            AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT=40;
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
