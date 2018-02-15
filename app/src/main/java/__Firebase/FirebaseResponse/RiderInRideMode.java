package __Firebase.FirebaseResponse;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.CallBackListener;

/**
 * Created by User on 2/6/2018.
 */

public class RiderInRideMode implements CallBackListener {

    private long HistoryID = FirebaseConstant.UNDEFINE;
    private Main main = new Main();

    public RiderInRideMode(boolean hasRide, long HistoryID) {
        if (hasRide) {
            this.HistoryID = HistoryID;
            GetCurrentHistory();
        } else {
            NoRide();
        }
    }

    private void HasRide() {
        /* Riding History Model*/
        AppConstant.currentRidingHistoryModel = FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance();
        /*Rider Model*/
        AppConstant.riderModel = FirebaseWrapper.getInstance().getRiderModelInstance();
        FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DeepClone(FirebaseWrapper.getInstance().getRiderModelInstance());
        AppConstant.IS_RIDE = 1;
    }

    private void NoRide() {
        AppConstant.IS_RIDE = 0;
    }

    private void GetCurrentHistory() {
        this.main.GetCurrentRiderHistoryModel(this.HistoryID, FirebaseWrapper.getInstance().getClientModelInstance().ClientID, this);
    }

    @Override
    public void OnGetCurrentRiderHistoryModel(boolean value) {
        if (value == true) {
            this.main.GetCurrentRider(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().RiderID, this);

            FirebaseResponse.RideStartedResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
            FirebaseResponse.RideFinishedResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
            FirebaseResponse.RideCanceledByRiderResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
        } else {
            NoRide();
        }
    }

    @Override
    public void OnGetCurrentRider(boolean value) {
        if (value == true) {
            HasRide();
        } else {
            NoRide();
        }
    }
}
