package ContactWithFirebase;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;

import java.util.ArrayList;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.FindNearestRider;
import __Firebase.FirebaseReqest.__FirebaseRequest;
import __Firebase.FirebaseResponse.FirebaseResponse;
import __Firebase.FirebaseResponse.InitialAcceptanceOfRideResponse;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IGerRiderLocation;
import __Firebase.ICallBackInstance.IGetCurrentRider;

/**
 * Created by User on 12/9/2017.
 */

public class Main implements ICallbackMain, ICallBackCurrentServerTime {

    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private ClientModel clientModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private __FirebaseRequest firebaseRequestInstance;
    private Pair<Double, Double> Source = null, Destination = null;
    private IGetCurrentRider iGetCurrentRider;
    private static String SourceName, DestinationName;
    private static long TotalCost;
    private static long DiscountID;
    private static String ShortestTime, ShortestDistance;
    private Context context = null;

    public Main() {
        firebaseWrapper = FirebaseWrapper.getInstance();
    }

    public boolean CreateNewClientFirebase(LoginData loginData, String phoneNumber) {

        if (MainActivity.check) {
            loginData = new LoginData(
                    "Jobayer",
                    "sheikh",
                    FirebaseWrapper.getDeviceToken(),
                    null,
                    null,
                    "1010",
                    "1",
                    "null",
                    "null",
                    "null"
            );
            phoneNumber = new String("01752062838");
        }

        if (loginData == null || FirebaseUtilMethod.IsEmptyOrNull(phoneNumber)) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        clientModel = firebaseWrapper.getClientModelInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        clientModel.ClientID = Long.parseLong(loginData.getClientId());
        clientModel.FullName = loginData.firstName;
        clientModel.PhoneNumber = Long.parseLong(phoneNumber);

        clientModel.DeviceToken = FirebaseWrapper.getDeviceToken();
        clientModel.IsSearchingOrOnRide = FirebaseConstant.UNDEFINE;
        clientModel.CostOfCurrentRide = FirebaseConstant.UNDEFINE;
        clientModel.CurrentRidingHistoryID = FirebaseConstant.UNDEFINE;
        clientModel.RideRejectedByRider = FirebaseConstant.UNDEFINE;

        this.IsClientAlreadyCreated(clientModel);
        return true;
    }

    public boolean IsClientAlreadyCreated(ClientModel ClientModel) {

        firebaseWrapper = FirebaseWrapper.getInstance();

        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        firebaseRequestInstance.IsClientAlreadyCreated(ClientModel.ClientID, Main.this);
        return true;
    }

    public boolean RequestForRide(Pair<Double, Double> Source, Pair<Double, Double> Destination, String _SourceName, String _DestinationName, long _TotalCost, long _DiscountID) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.Source = Source;
        this.Destination = Destination;
        SourceName = _SourceName;
        DestinationName = _DestinationName;
        TotalCost = _TotalCost;
        DiscountID = _DiscountID;

        firebaseRequestInstance.RequestForRide(Source, Destination, Main.this);
        return true;
    }

    public boolean SentNotificationToRider(/*Firebase Client Mode, rider Model*/RiderModel Rider, ClientModel Client, Pair<Double, Double> Source, Pair<Double, Double> Destination, String SourceName, String DestinationName, String ShortestTime, String ShortestDistance, long TotalCost, long DiscountID, long Time) {

        if (Rider == null || Client == null || Source == null || Destination == null || Time == 0) return false;

        this.riderModel = Rider;
        this.clientModel = Client;
        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        firebaseRequestInstance.SentNotificationToRider(this.riderModel, this.clientModel, Source, Destination, SourceName, DestinationName, ShortestTime, ShortestDistance, TotalCost, DiscountID, Time, Main.this);
        return true;
    }

    public boolean SetDeviceTokenToRiderTable(/*Firebase Rider Model*/ ClientModel Client, String deviceToken) {

        if (Client == null || Client.ClientID < 1 || FirebaseUtilMethod.IsEmptyOrNull(deviceToken))
            return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.clientModel = Client;
        this.clientModel.DeviceToken = deviceToken;

        firebaseWrapper.getFirebaseRequestInstance().SetDeviceTokenToRiderTable(this.clientModel, Main.this);
        return true;
    }

    public boolean GetCurrentRider(long RiderID) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentRider(RiderID, this);
        return true;
    }

    public boolean GetRiderLocation(/*Firebase nearest rider Model*/RiderModel Rider, IGerRiderLocation iGerRiderLocation) {

        if (Rider == null || Rider.RiderID < 1 || iGerRiderLocation == null) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.riderModel = Rider;

        firebaseRequestInstance.GetRiderLocation(this.riderModel, iGerRiderLocation);
        return true;
    }

    public boolean GetCurrentRiderHistoryModel(/*Firebase Client Model*/ClientModel Client, long HistoryId, long Time, int ActionType) {

        if (Client == null || Client.ClientID < 1 || HistoryId < 1 || Time <= 0) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        firebaseRequestInstance.GetCurrentRiderHistoryModel(HistoryId, this.clientModel.ClientID, Time, ActionType, Main.this);
        return true;
    }

    public boolean RequestForRiderLocation(/*Firebase Rider Model*/RiderModel Rider) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.riderModel = Rider;

        riderModel.CurrentRiderLocation.RequestForUpdateLocation = FirebaseConstant.SET_REQUEST_FOR_RIDER_LOCATION;
        firebaseRequestInstance.RequestForRiderLocation(this.riderModel, Main.this);
        return true;
    }

    public boolean GetCurrentRidingCost(ICallBackFinishedRide iCallBackFinishedRide) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.currentRidingHistoryModel = firebaseWrapper.getCurrentRidingHistoryModelInstance();
        this.clientModel = firebaseWrapper.getClientModelInstance();

        firebaseRequestInstance.GetCurrentRidingCost(
                clientModel,
                currentRidingHistoryModel,
                iCallBackFinishedRide
        );
        return true;
    }

    public boolean UpdateCostForCurrentRide(/*Firebase Client Model*/ClientModel Client, long Cost) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        this.clientModel.CostOfCurrentRide = Cost;
        firebaseRequestInstance.UpdateCostForCurrentRide(this.clientModel, Main.this);
        return true;
    }

    public boolean GetCurrentRidingHistoryID(/*Firebase Client Model*/ClientModel Client) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        firebaseRequestInstance.GetCurrentRidingHistoryID(this.clientModel, Main.this);
        return true;
    }

    public boolean ChangeDestinationLocation(/*Firebase Model*/ CurrentRidingHistoryModel HistoryModel, ClientModel Client, Pair<Double, Double> newEndingLocation) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.currentRidingHistoryModel = HistoryModel;
        this.clientModel = Client;

        this.currentRidingHistoryModel.EndingLocation.Latitude = newEndingLocation.first;
        this.currentRidingHistoryModel.EndingLocation.Longitude = newEndingLocation.second;

        firebaseRequestInstance.ChangeDestinationLocation(this.currentRidingHistoryModel, this.clientModel, Main.this);
        return true;
    }

    public boolean SetRidingCostSoFar(/*Firebase Client Model*/ClientModel Client, long Cost) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        this.clientModel.CostOfCurrentRide = Cost;
        firebaseRequestInstance.SetRidingCostSoFar(this.clientModel, Main.this);
        return true;
    }

    public boolean CancelRideByClient(/*Firebase HistoryAdapter, Client Model*/CurrentRidingHistoryModel HistoryModel, ClientModel Client) {

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
        if (value == true) {
            FirebaseResponse.InitialAcceptanceOfRideResponse(FirebaseWrapper.getInstance().getClientModelInstance());
            FirebaseResponse.RideRejectedByRiderResponse(FirebaseWrapper.getInstance().getClientModelInstance());
        }
        Log.d(FirebaseConstant.NEW_USER_CREATED, Boolean.toString(value));
    }

    @Override
    public void OnRequestForRide(ArrayList<RiderModel> RiderList) {
        if (RiderList != null && RiderList.size() > 0) {
            firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
            new FindNearestRider(RiderList, this.Source, this);
        }
    }

    @Override
    public void OnSentNotificationToRider(boolean value) {
        Log.d(FirebaseConstant.NOTIFICATION_SEND, FirebaseConstant.NOTIFICATION_SEND);
    }

    @Override
    public void OnIsClientAlreadyCreated(boolean value) {
        if (value == true) {
            SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getClientModelInstance(),
                    FirebaseWrapper.getDeviceToken()
            );
            FirebaseResponse.InitialAcceptanceOfRideResponse(FirebaseWrapper.getInstance().getClientModelInstance());
            FirebaseResponse.RideRejectedByRiderResponse(FirebaseWrapper.getInstance().getClientModelInstance());
            return;
        }
        firebaseRequestInstance.CreateClientFirstTime(clientModel, Main.this);
    }

    @Override
    public void OnNearestRiderFound(boolean value, String shortestTime, String shortestDistance) {
        if (value == true) {

            Log.d(FirebaseConstant.NEAREST_RIDER_FOUND, FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider.FullName);
            ShortestTime = shortestTime;
            ShortestDistance = shortestDistance;

            FirebaseUtilMethod.getNetworkTime(
                    FirebaseConstant.SEND_NOTIFICATION_TO_RIDER,
                    null,
                    this
            );
        } else {
            //Toast.makeText(context, FirebaseConstant.NO_RIDER_FOUND, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnSetDeviceTokenToRiderTable(boolean value) {
        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, Boolean.toString(value));
    }

    @Override
    public void OnGetCurrentRiderHistoryModel(boolean value, long Time, int ActionType) {
        if (value == true) {
            if (ActionType == FirebaseConstant.GET_HISTORY_FOR_INITIAL_ACCEPTANCE) {
                iGetCurrentRider = new InitialAcceptanceOfRideResponse(Time);
                FirebaseResponse.RideStartedResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
                FirebaseResponse.RideFinishedResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
                FirebaseResponse.RideCanceledByRiderResponse(FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID);
            } else if (ActionType == FirebaseConstant.GET_HISTORY_FOR_GENERAL) {
                /*Do the stuff*/
            }
        }
    }

    @Override
    public void OnGetCurrentRider(boolean value) {
        if (value == true) {
            iGetCurrentRider.OnGetCurrentRider(true);
            Log.d(FirebaseConstant.RIDER_LOADED, FirebaseConstant.RIDER_LOADED + FirebaseWrapper.getInstance().getRiderModelInstance().FullName);
            Log.d(FirebaseConstant.RIDER_LOADED, FirebaseConstant.RIDER_LOADED);
        } else {
            iGetCurrentRider.OnGetCurrentRider(false);
        }
    }

    @Override
    public void OnResponseServerTime(long Time, int type) {
        if (Time > 0) {
            switch (type) {
                case FirebaseConstant.SEND_NOTIFICATION_TO_RIDER: {
                    this.SentNotificationToRider(
                            FirebaseWrapper.getInstance().getRiderViewModelInstance().NearestRider,
                            firebaseWrapper.getClientModelInstance(),
                            this.Source,
                            this.Destination,
                            SourceName,
                            DestinationName,
                            ShortestTime,
                            ShortestDistance,
                            TotalCost,
                            DiscountID,
                            Time
                    );
                    break;
                }
            }
        }
    }
}
