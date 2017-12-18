package __Firebase.ICallBackInstance;

/**
 * Created by User on 11/21/2017.
 */

public interface CallBackListener {
    void onRequestCompletion(boolean flag);
    void onCreateClientFirstTime(boolean value);
    void onRequestForRide(boolean value);
    void onUpdateCostForCurrentRide(String value);
}
