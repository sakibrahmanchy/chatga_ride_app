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
    private Main main;

    public RiderInRideMode(boolean hasRide, long HistoryID) {
        if (hasRide) {
            this.HistoryID = HistoryID;
            this.main = new Main();
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
       AppConstant.IS_RIDE=1;
    }

    private void NoRide() {
          AppConstant.IS_RIDE=0;
    }

    private void GetCurrentHistory() {
        this.main.GetCurrentRiderHistoryModel(this.HistoryID, this);
    }

    @Override
    public void OnGetCurrentRiderHistoryModel(boolean value) {
        if (value == true) {
            this.main.GetCurrentRider(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().RiderID, this);
        }
    }

    @Override
    public void OnGetCurrentRider(boolean value) {
        if (value == true) {
            HasRide();
        }
    }
}
