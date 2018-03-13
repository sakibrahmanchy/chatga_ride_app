package __Firebase.FirebaseResponse;

import android.content.Intent;
import android.util.Log;

import ContactWithFirebase.Main;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.Dailog.SearchingDriver;
import com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity;
import com.demoriderctg.arif.demorider.OnrideMode.SendNotification;

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;
import __Firebase.ICallBackInstance.IGetCurrentRider;

import static com.demoriderctg.arif.demorider.Dailog.FullMapSearching.fullMapActivity;
import static com.demoriderctg.arif.demorider.Dailog.SearchingDriver.searchActivity;

/**
 * Created by User on 1/15/2018.
 */

public class InitialAcceptanceOfRideResponse implements ICallBackCurrentServerTime, IGetCurrentRider {

    private NotificationModel notificationModel;
    private CurrentRidingHistoryModel currentRidingHistoryModel;

    private RiderModel riderModel;
    private long Time;
    private Main main;

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
        FirebaseConstant.IS_RIDE_ACCEPTED_BY_RIDER = 1;
        AppConstant.HISTORY_ID = (int)currentRidingHistoryModel.HistoryID;
            Intent intent = new Intent(searchActivity,OnrideModeActivity.class);
            searchActivity.startActivity(intent);
            fullMapActivity.finish();
            searchActivity.finish();



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
            new Main().GetCurrentRider(currentRidingHistoryModel.RiderID, this);
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
        if (value == true) {
            Log.d(FirebaseConstant.RIDER_LOADED, FirebaseConstant.RIDER_LOADED + FirebaseWrapper.getInstance().getRiderModelInstance().FullName);
            FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DeepClone(FirebaseWrapper.getInstance().getRiderModelInstance());
            Response();
        }
    }


}
