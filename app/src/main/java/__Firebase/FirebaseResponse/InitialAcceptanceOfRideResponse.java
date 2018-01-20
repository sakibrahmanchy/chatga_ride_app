package __Firebase.FirebaseResponse;

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 1/15/2018.
 */

public class InitialAcceptanceOfRideResponse {

    private NotificationModel notificationModel;
    private CurrentRidingHistoryModel currentRidingHistoryModel;
    private RiderModel riderModel;
    public InitialAcceptanceOfRideResponse() {
        Response();
    }

    private void Response() {
        riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        currentRidingHistoryModel = FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance();

        ParseDataFromHistoryToNotification();
    }

    private void ParseDataFromHistoryToNotification(){

        notificationModel.riderPhone = String.valueOf(riderModel.PhoneNumber);
        notificationModel.riderName = riderModel.FullName;
        notificationModel.riderId = currentRidingHistoryModel.RiderID;
        /*Do the other stuff*/
    }
}
