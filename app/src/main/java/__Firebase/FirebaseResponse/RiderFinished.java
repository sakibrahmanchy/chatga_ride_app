package __Firebase.FirebaseResponse;

import ContactWithFirebase.Main;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;

/**
 * Created by User on 1/15/2018.
 */

public class RiderFinished implements ICallBackFinishedRide {

    public RiderFinished() {
        Response();
    }

    private void Response() {
        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(true);
        FirebaseWrapper.getInstance().getNotificationModelInstance().ClearData();
        FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().ClearData();

        RequestForFinalCost();
    }

    private void RequestForFinalCost(){
        new Main().GetCurrentRidingCost(this);
    }

    @Override
    public void OnFinishedRide(long FinalCost) {
        /* Get the final Cost */
    }
}