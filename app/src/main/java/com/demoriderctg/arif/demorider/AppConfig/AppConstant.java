package com.demoriderctg.arif.demorider.AppConfig;

import com.demoriderctg.arif.demorider.FavoritePlaces.HomeLocationModel;
import com.demoriderctg.arif.demorider.FavoritePlaces.WorkLocationModel;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 12/17/2017.
 */

public class AppConstant {

    public static final boolean IS_LOG_ENABLE = true;

    public static final String BASE_URL  = "http://139.59.90.128/chaatga_rider/";
    public static final String Empty = ("");
    public static final String Latitude = "lat";
    public static final String Longitude = "lng";
    public static final String Map_Api_Direction = "https://maps.googleapis.com/maps/api/directions/";
    public static final String JSON = "json";
    public static final String Exception = "Exception";
    public static final String DrivingMode = "mode=driving";
    public static final String Set_Sensor_False = "sensor=false";
    public static final String DestinationEqual = "destination=";
    public static final String OriginEqual = "origin=";
    public static final String AMPERSAND = "&";
    public static final String Question = "?";
    public static final String Comma = ",";
    public static  int INTERNET_CHECK=0;
    public static LatLng SOURCE,DESTINATION;
    public static String SOURCE_NAME="";
    public static String DESTINATION_NAME="";
    public static final float DEFAULT_ZOOM = 16f;
    public static final int IS_RIDE_FINISH=0;
    public static LatLng OnchangeDeviceLOcation = null;
    public static final double BASE_TAKA = 20;
    public static final double PER_KILOMITTER = 10;
    public static final double DURATION_PER_KILOMITTER = 1;
    public static String DISTANCE = ("");
    public static String DURATION = ("");
    public static int INITIAL_RIDE_ACCEPT=0;
    public static boolean FINISH_RIDE=false;
    public static long FINAL_RIDE_COST=0;
    public static boolean START_RIDE=false;
    public static boolean TREAD_FOR_FINISH_RIDE=true;
    public static UserDiscounts userDiscount = null;
    public static int HISTORY_ID=0;
    public static double RATING =0;
    public static long TOTAL_COST = 0;
    public static HomeLocationModel searchSorceLocationModel = null;
    public static WorkLocationModel searchDestinationLocationModel = null;
    public static  int NOTIFICATION_ID =0;
    public static CurrentRidingHistoryModel currentRidingHistoryModel = null;
    public static RiderModel riderModel = null;
    public static int IS_RIDE=2;
    public static double SOURCE_LATITUTE=0;
    public static double SOURCE_LOGITUTE=0;
    public static double DESTINATION_LATITUTE=0;
    public static double DESTINATION_LOGITUTE=0;
    public static String RIDER_NAME=null;
    public static String RIDER_PHONENUMBER= null;
    public static String RIDER_BIKE_NUMBER=null;
    public static boolean ONRIDEMODE_ACTIVITY=false;
    public static boolean SEARCH_ACTIVITY=false;



    /* Log Tag, Message */
    public static final String GOOGLE_PLAY_SERVICE_OK = "GOOGLE_PLAY_SERVICE_OK";
    public static final String GOOGLE_PLAY_SERVICE_ERR = "GOOGLE_PLAY_SERVICE_ERR";

    /*Message for Toast*/
    public static final String GOOGLE_PLAY_SERVICE_MSG = "you can not make request";

    /*Latlon bound*/
    public static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(23.678001,90.308731), new LatLng(23.895385, 90.447749));
    public static final LatLngBounds LAT_LNG_BOUNDS_CTG = new LatLngBounds(
            new LatLng(22.306818, 91.769043), new LatLng(22.405721,91.860118) );
    public static final LatLngBounds LAT_LNG_BOUNDS_CTG_2 = new LatLngBounds(
            new LatLng(22.235183,91.791642), new LatLng(22.321428, 91.812988));
    public static final LatLngBounds LAT_LNG_BOUNDS_CTG_3 = new LatLngBounds(
            new LatLng(22.237333, 91.717972), new LatLng(22.475470, 91.866288));

    /*Notification key*/
    public static String ACTION_TYPE = ("actionType");
    public static String FINAL_COST = ("finalCost");
    public static String RIDER_ID = ("riderId");

    /*Notification Action Type*/
    public static final int ACTION_INITIAL_ACCEPTANCE_NOTIFICATION = 5012;
    public static final int ACTION_FINAL_ACCEPTANCE_NOTIFICATION = 6012;
    public static final int ACTION_FINISH_RIDE_NOTIFICATION = 7012;
    public static final int ACTION_CANCEL_RIDE_NOTIFICATION = 8012;

    /*Notification Title and Body*/
    public static final String INITIAL_ACCEPTANCE_BODY = ("Your ride is accepted");
    public static final String FINAL_ACCEPTANCE_BODY = ("Your ride is started");
    public static final String FINISH_RIDE_BODY = ("Your ride is finished");
    public static final String CANCEL_RIDE_BODY = ("Your ride is canceled try again");

    public static final String INITIAL_ACCEPTANCE_TITLE = ("Ride Accepted");
    public static final String FINAL_ACCEPTANCE_TITLE = ("Ride Started");
    public static final String FINISH_RIDE_TITLE = ("Ride Finished");
    public static final String CANCEL_RIDE_TITLE = ("Ride Canceled");
    public static final int GET_SMS_PERMISSION = 1;
    public static final int SEARCH_SOURCE_AUTOCOMPLETE=1;
    public static final int SEARCH_DESTINATION_AUTOCOMPLETE=2;
    public static boolean SOURCE_SELECT=false;
    public  static boolean DESTINATION_SELECT = true;
    public  static double ESTIMATE_FARE_WITHOUT_DISCOUNT=0;
    public  static double ESTIMATED_FARE_AFTER_DISCOUNT=0;

}
