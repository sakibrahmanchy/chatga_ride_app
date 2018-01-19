package __Firebase.FirebaseUtility;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseConstant {

    public static String CLIENT = "Client";
    public static String CLIENT_ID = "ClientID";
    public static String CURRENT_RIDING_HISTORY_ID = "CurrentRidingHistoryID";
    public static String RIDER = "Rider";
    public static String RIDER_LOCATION = "RiderLocation";
    public static String CURRENT_RIDER_LOCATION = "CurrentRiderLocation";
    public static String REQUEST_FOR_UPDATE_LOCATION = "RequestForUpdateLocation";
    public static String ENDING_LOCATION = "EndingLocation";
    public static String LONGITUDE = "Longitude";
    public static String LATITUDE = "Latitude";
    public static String COST_SO_FAR = "CostSoFar";
    public static String CANCEL_RIDE_BY_RIDER = "RideCanceledByRider";
    public static String CANCEL_RIDE_BY_CLIENT = "RideCanceledByClient";
    public static String COST_OF_CURRENT_RIDE = "CostOfCurrentRide";
    public static String IS_RIDE_FINISHED = "IsRideFinished";
    public static String IS_RIDE_START = "IsRideStart";
    public static String RIDE_CANCEL_BY_CLIENT = "RideCanceledByClient";
    public static String RIDE_CANCEL_BY_RIDER = "RideCanceledByRider";
    public static String HISTORY = "History";
    public static String HISTORY_ID = "HistoryID";
    public static String JOIN = "_";
    public static String CLIENT_HISTORY = "Client_History";
    public static String RIDER_HISTORY = "Rider_History";
    public static String ONLINE_BUSY_RIDE = "OnlineBusyOnRide";
    public static String RIDER_ID = "RiderID";
    public static String IS_RIDER_BUSY = "IsRiderBusy";
    public static String IS_RIDER_ON_RIDE = "IsRiderOnRide";
    public static String IS_RIDER_ON_LINE = "IsRiderOnline";
    public static String ON_LINE_BUSY_ON_RIDE = "OnlineBusyOnRide";
    public static String UTF_8 = "UTF-8";
    public static String REQUEST_METHOD = "POST";
    public static String EQUAL = "=";
    public static String AMPERSAND = "&";
    public static String DEVICE_TOKEN = "DeviceToken";

    /* For Map */
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
    public static final String Question = "?";
    public static final String Comma = ",";

    public static int ONLINE_NOT_BUSY_NO_RIDE = 100;
    public static int SET_CANCEL_RIDE_BY_CLIENT = 1;
    public static int SET_REQUEST_FOR_RIDER_LOCATION = 1;
    public static int SET = 1;
    public static int UNSET = 0;
    public static int UNDEFINE = -1;
    public static Double INFINITE_DOUBLE = Double.MAX_VALUE;
    public static Integer INFINITE_INTEGER = Integer.MAX_VALUE;

    /*Log Cat Key*/
    public static String NEW_RIDER_ERROR = "NEW_USER_ERROR";
    public static String NEW_USER_ERROR = "NEW_USER_ERROR";
    public static String NEAREST_RIDER_ERROR = "NEAREST_RIDER_ERROR";
    public static String NEAREST_RIDER = "NEAREST_RIDER";
    public static String NOTIFICATION_SEND = "NOTIFICATION_SEND";
    public static String NEW_USER_CREATED = "NEW_USER_CREATED";
    public static String LOCATION_ERROR = "LOCATION_ERROR";
    public static String RIDER_INFO = "RIDER_INFO";
    public static String NEAREST_RIDER_FOUND = "NEAREST_RIDER_FOUND";
    public static String SHORTEST_DISTANCE = "SHORTEST_DISTANCE";
    public static String ALL_RIDER_LOADED = "ALL_RIDER_LOADED";
    public static String CLIENT_LOADED = "CLIENT_LOADED";
    public static String HISTORY_LOADED = "HISTORY_LOADED";
    public static String RIDING_HISTORY_LOADED = "RIDING_HISTORY_LOADED";
    public static String UPDATE_LOCATION = "UPDATE_LOCATION";
    public static String GET_UPDATE_LOCATION = "GET_UPDATE_LOCATION";
    public static String RESPONSE_FROM_SERVER = "RESPONSE_FROM_SERVER";
    public static String FIREBASE_REG_TOKEN = "FIREBASE_REG_TOKEN";
    public static String DEVICE_TOKEN_UPDATE = "DEVICE_TOKEN_UPDATE";
    public static String RECEIVED_NOTIFICATION = "RECEIVED_NOTIFICATION";
    public static String HISTORY_ID_ADDED_TO_CLIENT = "HISTORY_ID_ADDED_TO_CLIENT";

    /*Toast Message*/
    public static String NO_RIDER_FOUND = "No rider found";

    /*Notification Action Type*/
    public static String CLIENT_TO_RIDER = "3021";

    public FirebaseConstant(){
    }
}
