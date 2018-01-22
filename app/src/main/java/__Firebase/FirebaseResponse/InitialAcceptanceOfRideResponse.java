package __Firebase.FirebaseResponse;

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

/**
 * Created by User on 1/15/2018.
 */

public class InitialAcceptanceOfRideResponse implements ICallBackCurrentServerTime {

    private NotificationModel notificationModel;
    private CurrentRidingHistoryModel currentRidingHistoryModel;
    private RiderModel riderModel;
    private long Time;
    public InitialAcceptanceOfRideResponse(long Time) {
        this.Time = Time;
        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.INITIAL_AC_OF_RIDE_NOTIFY,
                null,
                this
        );
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

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.INITIAL_AC_OF_RIDE_NOTIFY){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                Response();
            }
        }
    }
}
