package __Firebase.FirebaseResponse;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;

/**
 * Created by User on 1/15/2018.
 */

public class RiderFinished implements ICallBackFinishedRide, ICallBackCurrentServerTime {

    private long Time;

    public RiderFinished(long Time) {
        this.Time = Time;
        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_FINISHED,
                null,
                this
        );
    }

    private void Response() {
        AppConstant.FINISH_RIDE = true;
        AppConstant.TREAD_FOR_FINISH_RIDE = true;
        AppConstant.INITIAL_RIDE_ACCEPT = 1;
        AppConstant.START_RIDE = false;
        RequestForFinalCost();
    }

    private void RequestForFinalCost(){
        new Main().GetCurrentRidingCost(this);
    }

    @Override
    public void OnFinishedRide(long FinalCost) {
        /* Get the final Cost */
        AppConstant.FINAL_RIDE_COST = FinalCost;
        ClearData();
    }

    private void ClearData(){
        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(true);
        FirebaseWrapper.getInstance().getNotificationModelInstance().ClearData();
        FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().ClearData();
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.RIDE_FINISHED){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                Response();
            }
        }
    }
}