package __Firebase.ViewModel;

import android.util.Log;
import android.util.Pair;

import com.google.maps.android.SphericalUtil;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseUtility.ShortestDistanceMap;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IDistanceAndDuration;

/**
 * Created by User on 11/17/2017.
 */

public class RiderViewModel {

    public RiderModel NearestRider;
    public Map<Long, Boolean> AlreadyRequestedRider;

    public RiderViewModel(){
        this.NearestRider = new RiderModel();
        this.AlreadyRequestedRider = new HashMap<>();
    }
}
