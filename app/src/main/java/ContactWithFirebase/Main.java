package ContactWithFirebase;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;

import java.util.ArrayList;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.FindNearestRider;
import __Firebase.FirebaseReqest.__FirebaseRequest;
import __Firebase.FirebaseResponse.FirebaseResponse;
import __Firebase.FirebaseResponse.InitialAcceptanceOfRideResponse;
import __Firebase.FirebaseResponse.RiderInRideMode;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.CallBackListener;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IGerRiderLocation;
import __Firebase.ICallBackInstance.IGetCurrentRider;
import __Firebase.Notification.NotificationWrapper;

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
    private static long HistoryID;
    private Context context = null;

    public Main() {
        firebaseWrapper = FirebaseWrapper.getInstance();
    }

    public boolean CreateNewClientFirebase(LoginData loginData, String phoneNumber) {

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
        clientModel.CurrentRidingHistoryID = FirebaseConstant.UNKNOWN_STRING;
        clientModel.RideRejectedByRider = FirebaseConstant.UNKNOWN_STRING;
        clientModel.ImageUrl = loginData.getAvatar();
        clientModel.Ratting = loginData.getRating()+"";

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

        if (Rider == null || Rider.RiderID < 1 || Rider.DeviceToken.isEmpty() || Client == null || Source == null || Destination == null || Time == 0)
            return false;

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

    public boolean GetCurrentRider(long RiderID, IGetCurrentRider iGetCurrentRider) {

        if (RiderID < 1 || iGetCurrentRider == null) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentRider(RiderID, this, iGetCurrentRider);
        return true;
    }

    public boolean GetCurrentRider(long RiderID, CallBackListener callBackListener) {

        if (RiderID < 1 || callBackListener == null) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentRider(RiderID, callBackListener);
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

    public boolean GetCurrentRiderHistoryModel(long HistoryID, long ClientID, CallBackListener callBackListener) {

        if (HistoryID < 1) return false;

        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        firebaseRequestInstance.GetCurrentRiderHistoryModel(HistoryID, ClientID, callBackListener);
        return true;
    }

    public boolean HasAnyRide(long ClientID) {

        if (ClientID < 1) return false;
        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().HasAnyRide(ClientID, Main.this);
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

    public boolean UpdateNameImageAndRatting(String newName, String newImageUrl, String newRatting) {

        if (FirebaseUtilMethod.IsEmptyOrNull(newName) && FirebaseUtilMethod.IsEmptyOrNull(newImageUrl) && FirebaseUtilMethod.IsEmptyOrNull(newRatting))
            return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        clientModel = firebaseWrapper.getClientModelInstance();

        if (!FirebaseUtilMethod.IsEmptyOrNull(newName)) clientModel.FullName = newName;
        if (!FirebaseUtilMethod.IsEmptyOrNull(newImageUrl)) clientModel.ImageUrl = newImageUrl;
        if(!FirebaseUtilMethod.IsEmptyOrNull(newRatting))   clientModel.Ratting = newRatting;

        firebaseRequestInstance.UpdateNameImageAndRatting(clientModel, Main.this);
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

    public boolean FinishRide(/*Firebase Client Model*/ClientModel Client) {

        if (Client == null || Client.ClientID < 1) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.clientModel = Client;

        this.clientModel.CurrentRidingHistoryID = FirebaseConstant.UNKNOWN_STRING;
        firebaseRequestInstance.FinishRide(this.clientModel, Main.this);
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

    public boolean CancelRideByClient(/*Firebase HistoryAdapter, Client Model*/CurrentRidingHistoryModel HistoryModel, ClientModel Client, long Time) {

        if (HistoryModel.HistoryID < 1 || Client.ClientID < 1) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        this.currentRidingHistoryModel = HistoryModel;
        this.clientModel = Client;

        this.currentRidingHistoryModel.RideCanceledByClient = Time;
        firebaseRequestInstance.CancelRideByClient(this.currentRidingHistoryModel, this.clientModel, Main.this);
        return true;
    }

    /*Force Finish Ride*/
    public void ForcedFinishRide() {
        FinishRide(FirebaseWrapper.getInstance().getClientModelInstance());
    }

    /*Force Cancel Ride*/
    public void ForceCancelRide() {

        FirebaseUtilMethod.getNetworkTime(
                FirebaseConstant.RIDE_CANCELED_BY_CLIENT,
                null,
                this
        );

        HistoryID = FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID;
        FirebaseWrapper.getInstance().getClientModelInstance().CurrentRidingHistoryID = FirebaseConstant.UNKNOWN_STRING;
        FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID = FirebaseConstant.UNKNOWN;

        firebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        firebaseRequestInstance.SetHistoryIDToClient(
                FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance(),
                FirebaseWrapper.getInstance().getClientModelInstance(),
                -1,
                this
        );

        /* Send Notification to Rider */
        new NotificationWrapper().SendCancelRide(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseWrapper.getInstance().getClientModelInstance(),
                null
        );
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
    }

    @Override
    public void OnHasAnyRide(boolean value) {
        if (value == true) {
            ClientModel Client = FirebaseWrapper.getInstance().getClientModelInstance();
            Pair<Long, Long> P = FirebaseUtilMethod.GetHistoryAndTime(Client.CurrentRidingHistoryID, true);
            if (P != null && P.first > 0 && P.second > 0) {
                /*Rider has a ride*/
                new RiderInRideMode(true, P.first);
            } else {
                /*Rider has no ride*/
                new RiderInRideMode(false, FirebaseConstant.UNKNOWN);
            }
        } else {
            new RiderInRideMode(false, FirebaseConstant.UNKNOWN);
        }
    }

    @Override
    public void OnUpdateNameAndImage(boolean value) {
        Log.d(FirebaseConstant.UPDATE_NAME_IMAGE, Boolean.toString(value));
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
                case FirebaseConstant.RIDE_CANCELED_BY_CLIENT: {
                    FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().HistoryID = HistoryID;
                    CancelRideByClient(
                            FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance(),
                            FirebaseWrapper.getInstance().getClientModelInstance(),
                            Time
                    );
                    break;
                }
            }
        }
    }
}
