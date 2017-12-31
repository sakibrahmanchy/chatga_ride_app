package __Firebase.FirebaseReqest;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseUtility.ShortestDistanceMap;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IDistanceAndDuration;

/**
 * Created by User on 12/30/2017.
 */

public class FindNearestRider implements IDistanceAndDuration {

    private Pair<Double, Double> Source, Destination;
    private ArrayList<RiderModel> RiderList = null;
    private int numberOfRider = 0;
    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public FindNearestRider(ArrayList<RiderModel> RiderList, Pair<Double, Double> _Source, final ICallbackMain callBackListener) {
        this.RiderList = RiderList;
        this.Source = _Source;
        this.numberOfRider = this.RiderList.size();
        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.Rider = firebaseWrapper.getRiderViewModelInstance().NearestRider;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request() {
        for (RiderModel Rider : RiderList) {
            Destination = Pair.create(Rider.CurrentRiderLocation.Latitude, Rider.CurrentRiderLocation.Longitude);
            new ShortestDistanceMap().getDistanceAndTime(Rider, Source, Destination,this);
        }
    }

    @Override
    public void OnGetIDistanceAndDuration(RiderModel _Rider, String Distance, String Duration) {
        Log.d(FirebaseConstant.NEAREST_RIDER, FirebaseConstant.NEAREST_RIDER);

        this.numberOfRider -= 1;
        if (FirebaseUtilMethod.IsEmptyOrNull(Distance) || FirebaseUtilMethod.IsEmptyOrNull(Distance) || _Rider == null) return;

        _Rider.DistanceFromClient = FirebaseUtilMethod.GetDistanceInDouble(Distance);

        if (FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.RiderID == 0) {
            FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider = _Rider;
        } else {
            if (!firebaseWrapper.getRiderViewModelInstance().AlreadyRequestedRider.containsKey(_Rider.RiderID) &&
                    FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DistanceFromClient <= _Rider.DistanceFromClient) {
                FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider = _Rider;
            }
            Log.d(FirebaseConstant.SHORTEST_DISTANCE, Rider.RiderID + " : " + Rider.DistanceFromClient + " " + this.Rider.RiderID + " : " + this.Rider.DistanceFromClient);
        }

        if (this.numberOfRider == 0) {
            this.callBackListener.OnNearestRiderFound(true);
        }
    }
}