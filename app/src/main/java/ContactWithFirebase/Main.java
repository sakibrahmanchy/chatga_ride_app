package ContactWithFirebase;

import android.util.Log;
import android.util.Pair;

import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.__FirebaseRequest;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseUtility.ShortestDistanceMap;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/9/2017.
 */

public class Main implements ICallbackMain {

    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private ClientModel clientModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private __FirebaseRequest firebaseRequestInstance;
    private ShortestDistanceMap shortestDistanceMap = null;

    public Main(){
        firebaseWrapper = FirebaseWrapper.getInstance();
    }

    public boolean CreateNewRiderFirebase(LoginData loginData , String phoneNumber){

        if(loginData == null || FirebaseUtilMethod.IsEmptyOrNull(phoneNumber))  return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        clientModel = firebaseWrapper.getClientModelInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        clientModel.ClientID = Long.parseLong(loginData.userId);
        clientModel.FullName = loginData.firstName;
        clientModel.PhoneNumber = Long.parseLong(phoneNumber);

        clientModel.DeviceToken = FirebaseWrapper.getDeviceToken();
        clientModel.IsSearchingOrOnRide = FirebaseConstant.UNSET;
        clientModel.CostOfCurrentRide = FirebaseConstant.UNDEFINE;
        clientModel.CurrentRidingHistoryID = FirebaseConstant.UNDEFINE;

        this.IsClientAlreadyCreated(clientModel);
        return true;
    }

    public boolean IsClientAlreadyCreated(ClientModel ClientModel){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        firebaseRequestInstance.IsClientAlreadyCreated(ClientModel.ClientID, Main.this);
        return true;
    }

    public boolean RequestForRide(Pair<Double, Double> Source, Pair<Double, Double> Destination){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        firebaseRequestInstance.RequestForRide(Source, Destination, Main.this);
        return true;
    }

    public boolean SentNotificationToRider(/*Firebase Client Mode, rider Model*/RiderModel Rider, ClientModel Client, Pair<Double, Double> Source, Pair<Double, Double> Destination){

        this.riderModel = Rider;
        this.clientModel = Client;
        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        firebaseRequestInstance.SentNotificationToRider(this.riderModel, this.clientModel, Source, Destination, Main.this);
        return true;
    }

    public boolean GetRiderLocation(/*Firebase nearest rider Model*/RiderModel Rider){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.riderModel = Rider;

        firebaseRequestInstance.GetRiderLocation(this.riderModel, Main.this);
        return true;
    }

    public boolean GetCurrentRiderHistoryModel(/*Firebase Client Model*/ClientModel Client, long HistoryId){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        firebaseRequestInstance.GetCurrentRiderHistoryModel(HistoryId, this.clientModel.ClientID, Main.this);
        return true;
    }

    public boolean RequestForRiderLocation(/*Firebase Rider Model*/RiderModel Rider){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.riderModel = Rider;

        riderModel.CurrentRiderLocation.RequestForUpdateLocation = FirebaseConstant.SET_REQUEST_FOR_RIDER_LOCATION;
        firebaseRequestInstance.RequestForRiderLocation(this.riderModel, Main.this);
        return true;
    }

    public boolean UpdateCostForCurrentRide(/*Firebase Client Model*/ClientModel Client, long Cost){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        this.clientModel.CostOfCurrentRide = Cost;
        firebaseRequestInstance.UpdateCostForCurrentRide(this.clientModel, Main.this);
        return true;
    }

    public boolean GetCurrentRidingHistoryID(/*Firebase Client Model*/ClientModel Client){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        firebaseRequestInstance.GetCurrentRidingHistoryID(this.clientModel, Main.this);
        return true;
    }

    public boolean ChangeDestinationLocation(/*Firebase Model*/ CurrentRidingHistoryModel HistoryModel, ClientModel Client, Pair<Double, Double> newEndingLocation){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.currentRidingHistoryModel = HistoryModel;
        this.clientModel = Client;

        this.currentRidingHistoryModel.EndingLocation.Latitude = newEndingLocation.first;
        this.currentRidingHistoryModel.EndingLocation.Longitude = newEndingLocation.second;

        firebaseRequestInstance.ChangeDestinationLocation(this.currentRidingHistoryModel, this.clientModel, Main.this);
        return true;
    }

    public boolean SetRidingCostSoFar(/*Firebase Client Model*/ClientModel Client, long Cost){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        this.clientModel.CostOfCurrentRide = Cost;
        firebaseRequestInstance.SetRidingCostSoFar(this.clientModel, Main.this);
        return true;
    }

    public boolean CancelRideByClient(/*Firebase History, Client Model*/CurrentRidingHistoryModel HistoryModel, ClientModel Client){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.currentRidingHistoryModel = HistoryModel;
        this.clientModel = Client;

        this.currentRidingHistoryModel.RideCanceledByClient = FirebaseConstant.SET_CANCEL_RIDE_BY_CLIENT;
        firebaseRequestInstance.CancelRideByClient(this.currentRidingHistoryModel, this.clientModel, Main.this);
        return true;
    }

    /* Response From Server*/

    @Override
    public void OnCreateNewRiderFirebase(boolean value) {
        Log.d(FirebaseConstant.NEW_USER_CREATED, value + "");
    }

    @Override
    public void OnRequestForRide(boolean value) {
        Log.d(FirebaseConstant.RIDER_INFO, value + "");
    }

    @Override
    public void OnSentNotificationToRider(boolean value) {

    }

    @Override
    public void OnIsClientAlreadyCreated(boolean value) {
        if(value == true)   return;
        firebaseRequestInstance.CreateClientFirstTime(clientModel, Main.this);
    }
}
