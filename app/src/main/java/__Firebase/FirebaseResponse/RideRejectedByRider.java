package __Firebase.FirebaseResponse;

import android.util.Pair;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

import java.util.Map;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.ThrdRequestAgainForRider;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

/**
 * Created by User on 2/1/2018.
 */

public class RideRejectedByRider implements ICallBackCurrentServerTime {

    private RiderModel riderModel;
    private Map<Long, Boolean> requestedRider;
    private long RiderID, Time;

    public RideRejectedByRider(long RiderID, long Time){
        this.RiderID = RiderID;
        this.Time = Time;

        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_REJECT_BY_RIDER,
                null,
                this
        );
    }

    private void Response(){
        AddRiderIntoBlockList();
    }

    private void AddRiderIntoBlockList(){
        /*
        * riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
        * requestedRider = FirebaseWrapper.getInstance().getRiderViewModelInstance().AlreadyRequestedRider;
        * if(riderModel.RiderID > 0){
        *     requestedRider.put(riderModel.RiderID, true);
        * }
        */
        /*
        * Do not request again, a timer is on which will automatically do another request within 70 sec
        */
    }

    private void RequestAgain(){
        FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(false);

        /*Request for another ride*/
        Double SourceLat = AppConstant.SOURCE.latitude;
        Double SourceLan = AppConstant.SOURCE.longitude;
        Double DestinationLat = AppConstant.DESTINATION.latitude;
        Double DestinationLan = AppConstant.DESTINATION.longitude;
        Pair Source = Pair.create(SourceLat,SourceLan);
        Pair Destination = Pair.create(DestinationLat,DestinationLan);
        int DiscountId = 0;
        if( AppConstant.userDiscount != null && AppConstant.userDiscount.getDiscountId() > 0){
            DiscountId = AppConstant.userDiscount.getDiscountId();
        }
        new Main().RequestForRide(Source, Destination, AppConstant.SOURCE_NAME, AppConstant.DESTINATION_NAME, AppConstant.TOTAL_COST, DiscountId);

    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.RIDE_REJECT_BY_RIDER){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                Response();
            }
        }
    }
}
