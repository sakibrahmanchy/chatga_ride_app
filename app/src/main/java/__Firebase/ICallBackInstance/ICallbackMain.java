package __Firebase.ICallBackInstance;

/**
 * Created by User on 12/10/2017.
 */

public interface ICallbackMain {
    void OnCreateNewRiderFirebase(boolean value);
    void OnRequestForRide(boolean value);
    void OnSentNotificationToRider(boolean value);
    void OnIsClientAlreadyCreated(boolean value);
    void OnNearestRiderFound(boolean value);
}
