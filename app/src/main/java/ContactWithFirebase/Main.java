package ContactWithFirebase;

import android.util.Log;

import __Firebase.CallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.FirebaseRequest;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/9/2017.
 */

public class Main implements ICallbackMain {

    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private ClientModel clientModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private FirebaseRequest FirebaseRequestInstance;

    public Main(){}

    public boolean CreateNewRiderFirebase(/*Main Client Mode*/){

        firebaseWrapper = FirebaseWrapper.getInstance();
        clientModel = firebaseWrapper.getClientModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        clientModel.ClientID = 1104006;
        clientModel.FullName = "FirebaseWrapper";
        clientModel.PhoneNumber = Long.parseLong("01752062838");

        clientModel.DeviceToken = FirebaseWrapper.getDeviceToken();
        clientModel.IsSearchingOrOnRide = FirebaseConstant.UNSET;
        clientModel.CostOfCurrentRide = FirebaseConstant.UNDEFINE;
        clientModel.CurrentRidingHistoryID = FirebaseConstant.UNDEFINE;

        FirebaseRequestInstance.CreateClientFirstTime(clientModel, Main.this);
        return true;
    }

    @Override
    public void OnCreateNewRiderFirebase(boolean value) {
        Log.d(FirebaseConstant.NEW_USER_CREATED, value + "");
    }
}
