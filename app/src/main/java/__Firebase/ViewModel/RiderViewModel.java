package __Firebase.ViewModel;

import android.util.Log;

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

    public RiderViewModel(){
        NearestRider = new RiderModel();
        AlreadyRequestedRider = new HashMap<>();
    }

    public void FindNearestRider(RiderModel Rider){
        if(this.NearestRider.RiderID == 0)  this.NearestRider = Rider;
        else {
            if(!AlreadyRequestedRider.containsKey(Rider.RiderID) && Rider.DistanceFromClient <= this.NearestRider.DistanceFromClient){
                this.NearestRider = Rider;
            }
            Log.d(FirebaseConstant.SHORTEST_DISTANCE, Rider.RiderID + " : " + Rider.DistanceFromClient  + " "+ this.NearestRider.RiderID + " : " + this.NearestRider.DistanceFromClient);
        }
    }
}
