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

public class RiderViewModel implements IDistanceAndDuration {

    public RiderModel NearestRider;
    public Map<Long, Boolean> AlreadyRequestedRider;
    public int NumberOfRiderFound = 0;

    private ICallbackMain callBackListener = null;

    public RiderViewModel(){
        this.NearestRider = new RiderModel();
        this.AlreadyRequestedRider = new HashMap<>();
        this.NumberOfRiderFound = 0;
    }

    public void FindNearestRider(RiderModel Rider, Pair<Double, Double> Source, ShortestDistanceMap shortestDistanceMap, ICallbackMain callBackListener){

        this.callBackListener = callBackListener;
        shortestDistanceMap.getDistanceAndTime(
                Rider,
                Pair.create(Rider.CurrentRiderLocation.Latitude, Rider.CurrentRiderLocation.Longitude),
                Source,
                this
        );
    }

    @Override
    public void OnGetIDistanceAndDuration(RiderModel Rider, String Distance, String Duration) {

        if(FirebaseUtilMethod.IsEmptyOrNull(Distance) || FirebaseUtilMethod.IsEmptyOrNull(Distance) || Rider == null)   return;
        this.NumberOfRiderFound -= 1;

        if(this.NearestRider.RiderID == 0)  this.NearestRider = Rider;
        else {
            if(!AlreadyRequestedRider.containsKey(Rider.RiderID) && Rider.DistanceFromClient <= this.NearestRider.DistanceFromClient){
                this.NearestRider = Rider;
            }
            Log.d(FirebaseConstant.SHORTEST_DISTANCE, Rider.RiderID + " : " + Rider.DistanceFromClient  + " "+ this.NearestRider.RiderID + " : " + this.NearestRider.DistanceFromClient);
        }

        if(NumberOfRiderFound == 0){
            this.callBackListener.OnNearestRiderFound(true);
        }
    }
}
