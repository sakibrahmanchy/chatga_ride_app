package __Firebase.FirebaseReqest;

import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import __Firebase.ICallBackInstance.CallBackListener;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 11/16/2017.
 */

public class __FirebaseRequest extends AppCompatActivity {

    private CallBackListener callBackListener;

    public __FirebaseRequest(){

    }

    public void CreateClientFirstTime(final ClientModel Client, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new CreateClientFirstTime(Client, callBackListener);
            }
        };
        thread.start();
    }

    public void IsClientAlreadyCreated(final long ClientID, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new IsClientAlreadyCreated(ClientID, callBackListener);
            }
        };
        thread.start();
    }

    public void RequestForRide(final Pair<Double, Double> Source, final Pair<Double, Double> Destination, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new RequestForRide(Source, Destination, callBackListener);
            }
        };
        thread.start();
    }

    public void SentNotificationToRider(final RiderModel Rider, final ClientModel Client, final Pair<Double, Double> Source, final Pair<Double, Double> Destination, ICallbackMain callbackListener){

        SentNotificationToRider pendingTask = new SentNotificationToRider(this, callbackListener);
        pendingTask.execute(
                    Long.toString(Client.ClientID),
                    Long.toString(Rider.RiderID),
                    Double.toString(Source.first),
                    Double.toString(Source.second),
                    Double.toString(Destination.first),
                    Double.toString(Destination.second)
                );
        finish();
    }

    public void CancelRideByClient(final CurrentRidingHistoryModel HistoryModel, final ClientModel Client, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new CancelRideByClient(HistoryModel, Client, callBackListener);
            }
        };
        thread.start();
    }

    public void GetRiderLocation(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetRiderLocation(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void GetCurrentRiderHistoryModel(final long HistoryID, final long ClientID, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
          @Override
            public void run(){
                new GetCurrentRiderHistoryModel(HistoryID, ClientID, callBackListener);
          }
        };
        thread.start();
    }

    public void SetRidingCostSoFar(final ClientModel Client, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRidingCostSoFar(Client, callBackListener);
            }
        };
        thread.start();
    }

    public void ChangeDestinationLocation(final CurrentRidingHistoryModel HistoryModel, final ClientModel ClientModel, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new ChangeDestinationLocation(HistoryModel, ClientModel, callBackListener);
            }
        };
        thread.start();
    }

    public void GetCurrentRidingHistoryID(final ClientModel Client, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetCurrentRidingHistoryID(Client, callBackListener);
            }
        };
        thread.start();
    }

    public void UpdateCostForCurrentRide(final ClientModel Client, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new UpdateCostForCurrentRide(Client, callBackListener);
            }
        };
        thread.start();
    }

    public void RequestForRiderLocation(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new RequestForRiderLocation(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
}
