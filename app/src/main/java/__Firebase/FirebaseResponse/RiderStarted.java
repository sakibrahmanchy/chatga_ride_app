package __Firebase.FirebaseResponse;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

/**
 * Created by User on 1/15/2018.
 */

public class RiderStarted implements ICallBackCurrentServerTime {

    long Time = 0;

    public RiderStarted(long Time) {
        this.Time = Time;
        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_ACCEPTED,
                null,
                this
        );
    }

    private void Response() {
        /*Do your Stuff*/
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if(value > 0 && type == FirebaseConstant.RIDE_ACCEPTED){
            if(Math.abs(value - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND){
                Response();
            }
        }
    }
}
