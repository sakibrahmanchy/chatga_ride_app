package __Firebase.FirebaseReqest;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

import __Firebase.Exception.FabricExceptionLog;
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
    private String _ShortestTime;
    private double _ShortestDistance;

    public FindNearestRider(ArrayList<RiderModel> RiderList, Pair<Double, Double> _Source, final ICallbackMain callBackListener) {
        this.RiderList = RiderList;
        this.Source = _Source;
        this.numberOfRider = this.RiderList.size();
        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.Rider = firebaseWrapper.getRiderViewModelInstance().NearestRider;
        this.callBackListener = callBackListener;
        this._ShortestTime = FirebaseConstant.Empty;
        this._ShortestDistance = FirebaseConstant.UNKNOWN;

        Request();
    }

    private void Request() {
        if(RiderList != null) {
            for (RiderModel Rider : RiderList) {
                Destination = Pair.create(Rider.CurrentRiderLocation.Latitude, Rider.CurrentRiderLocation.Longitude);
                new ShortestDistanceMap().getDistanceAndTime(Rider, Source, Destination, this);
            }
        }
    }

    @Override
    public void OnGetIDistanceAndDuration(RiderModel _Rider, double Distance, String Duration) {
        FabricExceptionLog.printLog(this.getClass().getSimpleName(), _Rider.FullName + " :: " + Distance);

        this.numberOfRider -= 1;
        if (FirebaseUtilMethod.IsEmptyOrNull(Duration) || _Rider == null) return;

        _Rider.DistanceFromClient = Distance;

        /*
         * If no rider found so far and current searched rider is not in block list
         */
        if (FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.RiderID <= 0 &&
                !firebaseWrapper.getRiderViewModelInstance().AlreadyRequestedRider.containsKey(_Rider.RiderID)) {

            FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DeepClone(_Rider);
            this._ShortestTime = Duration;
            this._ShortestDistance = Distance;

        } else {
            /*
             * Already has a rider but not confirm it is nearest
             * And rider is not on block list
             */
            if (!firebaseWrapper.getRiderViewModelInstance().AlreadyRequestedRider.containsKey(_Rider.RiderID) &&
                    FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DistanceFromClient >= _Rider.DistanceFromClient) {
                /*
                 * This rider not in block list and nearest than previous one
                 */
                FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.DeepClone(_Rider);
                this._ShortestTime = Duration;
                this._ShortestDistance = Distance;
            }
            FabricExceptionLog.printLog(this.getClass().getSimpleName(), Rider.RiderID + " : " + Rider.DistanceFromClient + " " + this.Rider.RiderID + " : " + this.Rider.DistanceFromClient);
        }

        if (this.numberOfRider == 0) {
            this.callBackListener.OnNearestRiderFound(true, _ShortestTime, _ShortestDistance);
        }
    }
}
