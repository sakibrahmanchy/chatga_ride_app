package __Firebase.FirebaseResponse;

import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.ThrdRequestAgainForRider;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

/**
 * Created by User on 1/15/2018.
 */

public class RideCanceledByRider implements ICallBackCurrentServerTime {

    private RiderModel riderModel;
    private Map<Long, Boolean> requestedRider;
    private long Time;

    public RideCanceledByRider(long Time){
        this.Time = Time;
        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_CANCELED_BY_RIDER,
                null,
                this
        );
    }

    private void Response(){
        AddRiderIntoBlockList();
    }

    private void AddRiderIntoBlockList(){

        riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
        requestedRider = FirebaseWrapper.getInstance().getRiderViewModelInstance().AlreadyRequestedRider;
        if(riderModel.RiderID > 0){
            requestedRider.put(riderModel.RiderID, true);
        }
        /*Do no request again rather just notify client to request again*/
        //RequestAgain();
    }

    private void RequestAgain(){
        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(false);
        /*Request for another ride*/
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.RIDE_CANCELED_BY_RIDER){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                Response();
            }
        }
    }
}
