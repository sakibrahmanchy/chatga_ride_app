package __Firebase.FirebaseResponse;

import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 1/15/2018.
 */

public class RideCanceledByRider {

    private RiderModel riderModel;
    private Map<Long, Boolean> requestedRider;

    public RideCanceledByRider(){
        Response();
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
        RequestAgain();
    }

    private void RequestAgain(){
        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(false);
        /*Request for another ride*/
    }
}
