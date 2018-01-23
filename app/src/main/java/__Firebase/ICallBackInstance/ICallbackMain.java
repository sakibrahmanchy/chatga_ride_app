package __Firebase.ICallBackInstance;

import java.util.ArrayList;

import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 12/10/2017.
 */

public interface ICallbackMain {
    void OnCreateNewRiderFirebase(boolean value);
    void OnRequestForRide(ArrayList<RiderModel> RiderLIst);
    void OnSentNotificationToRider(boolean value);
    void OnIsClientAlreadyCreated(boolean value);
    void OnNearestRiderFound(boolean value, String shortestTime, String shortestDistance);
    void OnSetDeviceTokenToRiderTable(boolean value);
    void OnGetCurrentRiderHistoryModel(boolean value, long Time);
    void OnGetCurrentRider(boolean value);
}
