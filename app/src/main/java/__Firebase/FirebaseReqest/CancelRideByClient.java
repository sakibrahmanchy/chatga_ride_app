package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class CancelRideByClient {

    private CurrentRidingHistoryModel HistoryModel = null;
    private ClientModel Client = null;
    private ICallbackMain callBackListener = null;

    public CancelRideByClient(CurrentRidingHistoryModel HistoryModel, ClientModel Client, ICallbackMain callBackListener){
        this.HistoryModel = HistoryModel;
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){
        /*
        * If Rider == null then no initial acceptance by rider
        * If Rider == model Notify rider
        */

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                    .orderByChild(FirebaseConstant.CLIENT_HISTORY).
                    equalTo(Client.ClientID + FirebaseConstant.JOIN + HistoryModel.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            HashMap<String, Object> CancelRideByRider = new HashMap<>();
                            CancelRideByRider.put(FirebaseConstant.CANCEL_RIDE_BY_CLIENT, HistoryModel.RideCanceledByClient);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CancelRideByRider);

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.CANCEL_RIDE_BY_CLIENT);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
