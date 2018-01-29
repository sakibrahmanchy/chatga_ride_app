package com.demoriderctg.arif.demorider.AppConfig;

import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by User on 12/17/2017.
 */

public class AppConstant {

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
    public static final float DEFAULT_ZOOM = 14f;
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


    /* Log Tag, Message */
    public static final String GOOGLE_PLAY_SERVICE_OK = "GOOGLE_PLAY_SERVICE_OK";
    public static final String GOOGLE_PLAY_SERVICE_ERR = "GOOGLE_PLAY_SERVICE_ERR";

    /*Message for Toast*/
    public static final String GOOGLE_PLAY_SERVICE_MSG = "you can not make request";

    /*Latlon bound*/
    public static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(90.1474141289,23.6245050799), new LatLng(90.599381674, 23.9678033413));

}
