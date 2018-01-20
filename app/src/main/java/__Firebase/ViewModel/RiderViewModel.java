package __Firebase.ViewModel;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 11/17/2017.
 */

public class RiderViewModel {

    public RiderModel NearestRider;
    public Map<Long, Boolean> AlreadyRequestedRider;

    public RiderViewModel() {
        this.NearestRider = new RiderModel();
        this.AlreadyRequestedRider = new HashMap<>();
    }

    public void ClearData(boolean fullData) {

        if(fullData == true) {
            this.AlreadyRequestedRider.clear();
        }

        this.NearestRider.RiderID = FirebaseConstant.UNDEFINE;
        this.NearestRider.FullName = FirebaseConstant.Empty;
        this.NearestRider.PhoneNumber = FirebaseConstant.UNDEFINE;
        this.NearestRider.DeviceToken = FirebaseConstant.Empty;
        this.NearestRider.CurrentRiderLocation.RequestForUpdateLocation = FirebaseConstant.UNDEFINE;
        this.NearestRider.CurrentRiderLocation.Latitude = FirebaseConstant.UNDEFINE;
        this.NearestRider.CurrentRiderLocation.Longitude = FirebaseConstant.UNDEFINE;
        this.NearestRider.IsRiderOnRide = FirebaseConstant.UNDEFINE;
        this.NearestRider.IsRiderBusy = FirebaseConstant.UNDEFINE;
        this.NearestRider.OnlineBusyOnRide = FirebaseConstant.UNDEFINE;
        this.NearestRider.IsRiderOnline = FirebaseConstant.UNDEFINE;
        this.NearestRider.CurrentRidingHistoryID = FirebaseConstant.UNDEFINE;
    }
}
