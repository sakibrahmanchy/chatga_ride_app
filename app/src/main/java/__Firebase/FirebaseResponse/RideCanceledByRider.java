package __Firebase.FirebaseResponse;

import android.content.Intent;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;

import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

import static com.demoriderctg.arif.demorider.OnrideMode.OnrideModeActivity.OnrideModeContext;

/**
 * Created by User on 1/15/2018.
 */

public class RideCanceledByRider implements ICallBackCurrentServerTime {

    private RiderModel riderModel;
    private Map<Long, Boolean> requestedRider;
    private long Time;

    public RideCanceledByRider(long Time) {
        this.Time = Time;
        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_CANCELED_BY_RIDER,
                null,
                this
        );
    }

    private void Response() {
        AddRiderIntoBlockList();
        if (AppConstant.ONRIDEMODE_ACTIVITY) {
            Intent intent = new Intent(OnrideModeContext, MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            OnrideModeContext.startActivity(intent);
        }
    }

    private void ResponseTimeOver(){

    }

    private void AddRiderIntoBlockList() {

        riderModel = FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider;
        requestedRider = FirebaseWrapper.getInstance().getRiderViewModelInstance().AlreadyRequestedRider;
        if (riderModel.RiderID > 0) {
            requestedRider.put(riderModel.RiderID, true);
        }
        /*Do no request again rather just notify client to request again*/
        //RequestAgain();
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if (value > 0 && type == FirebaseConstant.RIDE_CANCELED_BY_RIDER) {
            if (Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND) {
                Response();
            }
        } else {
            ResponseTimeOver();
        }
    }
}
