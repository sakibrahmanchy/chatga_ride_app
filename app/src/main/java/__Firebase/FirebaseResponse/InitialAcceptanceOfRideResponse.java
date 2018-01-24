package __Firebase.FirebaseResponse;

import ContactWithFirebase.Main;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;
import __Firebase.ICallBackInstance.IGetCurrentRider;

/**
 * Created by User on 1/15/2018.
 */

public class InitialAcceptanceOfRideResponse implements ICallBackCurrentServerTime, IGetCurrentRider {

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
        AppConstant.INITIAL_RIDE_ACCEPT = 1;
        /*Do the stuff*/
    }

    private void ParseDataFromHistoryToNotification(){

        notificationModel.riderPhone = String.valueOf(riderModel.PhoneNumber);
        notificationModel.riderName = riderModel.FullName;
        notificationModel.riderId = currentRidingHistoryModel.RiderID;
    }

    private void CheckValidity(){

        currentRidingHistoryModel = FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance();
        if(FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.RiderID == currentRidingHistoryModel.RiderID) {
            riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
            notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
            ParseDataFromHistoryToNotification();
            Response();
        } else {
            new Main().GetCurrentRider(currentRidingHistoryModel.RiderID);
        }
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.INITIAL_AC_OF_RIDE_NOTIFY){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                CheckValidity();
            }
        }
    }

    @Override
    public void OnGetCurrentRider(boolean value) {
        if(value == true){
            FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider = FirebaseWrapper.getInstance().getRiderModelInstance();
            Response();
        }
    }
}
