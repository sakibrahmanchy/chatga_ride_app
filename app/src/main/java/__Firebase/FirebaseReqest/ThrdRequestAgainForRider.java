package __Firebase.FirebaseReqest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Pair;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;

import java.util.Map;

import ContactWithFirebase.Main;
import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 2/1/2018.
 */

public class ThrdRequestAgainForRider {

    public static Handler handler = null;
    private static Runnable runnable = null;
    private static SharedPreferences.Editor NumberOfRequestEdit = null;
    private static SharedPreferences NumberOfRequestGet = null;

    public ThrdRequestAgainForRider() {
    }

    public static void Run() {
        try {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (FirebaseConstant.IS_RIDE_ACCEPTED_BY_RIDER == 1 || runnable == null) {
                        Destroy();
                        return;
                    }
                    AddRiderIntoBlockList();
                    Destroy();
                }
            };
            handler.postDelayed(runnable, FirebaseConstant.CONSECUTIVE_REQUEST_INTERVAL /*One minute five second*/);
        } catch (Exception ex) {
            FabricExceptionLog.sendLogToFabric(true, ThrdRequestAgainForRider.class.getSimpleName(), ex.getMessage());
        }
    }

    public static void Initiate() {
        handler = new Handler();
        Run();
    }

    public static void RemoveHandlerCallback() {
        try {
            if (handler != null && runnable != null) {
                handler.removeCallbacks(runnable);
            }
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, ThrdRequestAgainForRider.class.getSimpleName(), e.getMessage());
        }
    }

    public static boolean CanRequest() {
        if (!FirebaseConstant.VAR_CAN_REQUEST_FOR_RIDE) return false;

        /*Initialize Shared Preferences*/
        Context context = MapActivity.getContextOfApplication();
        if (NumberOfRequestEdit == null) {
            NumberOfRequestEdit = context.getSharedPreferences(FirebaseConstant.NUMBER_OF_REQUEST_PREF, MODE_PRIVATE).edit();
        }
        if (NumberOfRequestGet == null) {
            NumberOfRequestGet = context.getSharedPreferences(FirebaseConstant.NUMBER_OF_REQUEST_PREF, MODE_PRIVATE);
        }

        String numberOfRequest = NumberOfRequestGet.getString(FirebaseConstant.LAST_REQUESTED_NUMBER, null);
        if (numberOfRequest == null) {
            /*
             * No request is perform yet
             * So save one request is done
             */
            NumberOfRequestEdit.putString(FirebaseConstant.LAST_REQUESTED_NUMBER, FirebaseConstant.ONE + (" ") + System.currentTimeMillis());
            NumberOfRequestEdit.commit();
        } else {
            Pair<Integer, Long> P = FirebaseUtilMethod.GetNumberAndTime(numberOfRequest);
            /*
             * Can Request 3 times within five/ten minutes
             */
            if (P.first < FirebaseConstant.NUMBER_OF_CONSECUTIVE_REQUEST) {
                /*
                 * Request is less than three, so can request
                 */
                long currentTime = System.currentTimeMillis();
                if (Math.abs(P.second - currentTime) > FirebaseConstant.EACH_REQUEST_BUNDLE_INTERVAL) {
                    /*
                     * If last request is done more than five minute ago
                     * So, clear previous request make this request as first one
                     */
                    NumberOfRequestEdit.putString(FirebaseConstant.LAST_REQUESTED_NUMBER, FirebaseConstant.ONE + (" ") + System.currentTimeMillis());
                } else {
                    NumberOfRequestEdit.putString(FirebaseConstant.LAST_REQUESTED_NUMBER, Integer.toString(P.first + 1) + (" ") + System.currentTimeMillis());
                }
                NumberOfRequestEdit.commit();
            } else {
                /*
                 * Request is ore than 3 times
                 */
                long currentTime = System.currentTimeMillis();
                if (Math.abs(P.second - currentTime) <= FirebaseConstant.EACH_REQUEST_BUNDLE_INTERVAL) {
                    /* Request is more than 3 and doesn't pass ten/five minutes*/
                    return false;
                } else {
                    /*
                    * Request is more than 3 but pass ten/five minutes
                    * So request again and set one request is done
                    */
                    FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(true);
                    NumberOfRequestEdit.putString(FirebaseConstant.LAST_REQUESTED_NUMBER, FirebaseConstant.ONE + (" ") + System.currentTimeMillis());
                    NumberOfRequestEdit.commit();
                }
            }
        }
        return true;
    }

    public static void Destroy() {

        RemoveHandlerCallback();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            FabricExceptionLog.sendLogToFabric(true, ThrdRequestAgainForRider.class.getSimpleName(), e.getMessage());
        }

        if (handler != null) handler = null;
        if (runnable != null) runnable = null;
    }

    public static void AddRiderIntoBlockList() {

        RiderModel riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
        Map<Long, Boolean> requestedRider = FirebaseWrapper.getInstance().getRiderViewModelInstance().AlreadyRequestedRider;
        if (riderModel != null && riderModel.RiderID > 0) {
            requestedRider.put(riderModel.RiderID, true);
        }
        FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.ClearData();
        RequestAgain();
    }

    private static void RequestAgain() {

        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(false);

        /*Request for another ride*/
        Double SourceLat = AppConstant.SOURCE.latitude;
        Double SourceLan = AppConstant.SOURCE.longitude;
        Double DestinationLat = AppConstant.DESTINATION.latitude;
        Double DestinationLan = AppConstant.DESTINATION.longitude;
        Pair Source = Pair.create(SourceLat, SourceLan);
        Pair Destination = Pair.create(DestinationLat, DestinationLan);
        int DiscountId = 0;
        if (AppConstant.userDiscount != null && AppConstant.userDiscount.getDiscountId() > 0) {
            DiscountId = AppConstant.userDiscount.getDiscountId();
        }
        new Main().RequestForRide(Source, Destination, AppConstant.SOURCE_NAME, AppConstant.DESTINATION_NAME, AppConstant.TOTAL_COST, DiscountId);
    }

    /* If the device token is invalid*/
    public static void ReduceNumberOfRide() {

        Context context = MapActivity.getContextOfApplication();
        if (NumberOfRequestEdit == null) {
            NumberOfRequestEdit = context.getSharedPreferences(FirebaseConstant.NUMBER_OF_REQUEST_PREF, MODE_PRIVATE).edit();
        }
        if (NumberOfRequestGet == null) {
            NumberOfRequestGet = context.getSharedPreferences(FirebaseConstant.NUMBER_OF_REQUEST_PREF, MODE_PRIVATE);
        }

        String numberOfRequest = NumberOfRequestGet.getString(FirebaseConstant.LAST_REQUESTED_NUMBER, null);
        if (numberOfRequest == null) {
            return;
        } else {
            Pair<Integer, Long> P = FirebaseUtilMethod.GetNumberAndTime(numberOfRequest);
            NumberOfRequestEdit.putString(FirebaseConstant.LAST_REQUESTED_NUMBER, Integer.toString(P.first - 1) + (" ") + P.second);
            NumberOfRequestEdit.commit();
        }
    }
}
