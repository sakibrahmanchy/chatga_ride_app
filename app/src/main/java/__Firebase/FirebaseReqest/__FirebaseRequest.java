package __Firebase.FirebaseReqest;

import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import __Firebase.Exception.UncaughtException;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseResponse.HasAnyRide;
import __Firebase.ICallBackInstance.CallBackListener;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IGerRiderLocation;
import __Firebase.ICallBackInstance.IGetCurrentRider;

/**
 * Created by User on 11/16/2017.
 */

public class __FirebaseRequest extends AppCompatActivity {

    private CallBackListener callBackListener;

    public __FirebaseRequest() {

    }

    public void CreateClientFirstTime(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new CreateClientFirstTime(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void IsClientAlreadyCreated(final long ClientID, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new IsClientAlreadyCreated(ClientID, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void RequestForRide(final Pair<Double, Double> Source, final Pair<Double, Double> Destination, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new RequestForRide(Source, Destination, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void SentNotificationToRider(final RiderModel Rider, final ClientModel Client, final Pair<Double, Double> Source, final Pair<Double, Double> Destination, final String SourceName, String DestinationName, String ShortestTime, String ShortestDistance, long TotalCost, long DiscountID, long Time, ICallbackMain callbackListener) {

        if (!ThrdRequestAgainForRider.CanRequest()) return;

        SentNotificationToRider pendingTask = new SentNotificationToRider(this, callbackListener);
        pendingTask.execute(
                Long.toString(Client.ClientID),
                Client.FullName,
                Long.toString(Client.PhoneNumber),
                Client.DeviceToken,
                Client.ImageUrl,
                Client.Ratting,
                Long.toString(Rider.RiderID),
                Rider.DeviceToken.toString(),
                SourceName,
                DestinationName,
                ShortestTime,
                ShortestDistance,
                Double.toString(Source.first),
                Double.toString(Source.second),
                Double.toString(Destination.first),
                Double.toString(Destination.second),
                Long.toString(TotalCost),
                Long.toString(DiscountID),
                Long.toString(Time)
        );
        finish();
    }

    public void SetDeviceTokenToRiderTable(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new SetDeviceTokenToRiderTable(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void CancelRideByClient(final CurrentRidingHistoryModel HistoryModel, final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new CancelRideByClient(HistoryModel, Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void SetHistoryIDToClient(final CurrentRidingHistoryModel HistoryModel, final ClientModel Client, final long Time, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new SetHistoryIDToClient(HistoryModel, Client, Time, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRider(final long RiderID, final ICallbackMain callBackListener, final IGetCurrentRider iGetCurrentRider) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRider(RiderID, callBackListener, iGetCurrentRider);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRider(final long RiderID, final CallBackListener callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRider(RiderID, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetRiderLocation(final RiderModel Rider, final IGerRiderLocation iGerRiderLocation) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetRiderLocation(Rider, iGerRiderLocation);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRiderHistoryModel(final long HistoryID, final long ClientID, long Time, int ActionType, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRiderHistoryModel(HistoryID, ClientID, Time, ActionType, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRiderHistoryModel(final long HistoryID, final long ClientID, final CallBackListener callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRiderHistoryModel(HistoryID, ClientID, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void HasAnyRide(final long RiderID, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new HasAnyRide(RiderID, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void SetRidingCostSoFar(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new SetRidingCostSoFar(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void ChangeDestinationLocation(final CurrentRidingHistoryModel HistoryModel, final ClientModel ClientModel, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new ChangeDestinationLocation(HistoryModel, ClientModel, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRidingHistoryID(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRidingHistoryID(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void UpdateCostForCurrentRide(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new UpdateCostForCurrentRide(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void FinishRide(final ClientModel Client, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new FinishRide(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void RequestForRiderLocation(final RiderModel Rider, final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new RequestForRiderLocation(Rider, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetCurrentRidingCost(final ClientModel Client, final CurrentRidingHistoryModel History, final ICallBackFinishedRide iCallBackFinishedRide) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetCurrentRidingCost(Client, History, iCallBackFinishedRide);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void UpdateNameImageAndRatting(ClientModel Client, ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new UpdateNameImageAndRatting(Client, callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void GetAppSettings(final ICallbackMain callBackListener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                new GetAppSettings(callBackListener);
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.start();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
}
