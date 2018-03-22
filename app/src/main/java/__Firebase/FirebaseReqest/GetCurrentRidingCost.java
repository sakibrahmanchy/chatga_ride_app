package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;

/**
 * Created by User on 1/15/2018.
 */

public class GetCurrentRidingCost {

    private ClientModel Client = null;
    private CurrentRidingHistoryModel History = null;
    private ICallBackFinishedRide callBackListener = null;

    public GetCurrentRidingCost(ClientModel Client, CurrentRidingHistoryModel History, ICallBackFinishedRide callBackListener) {
        this.Client = Client;
        this.History = History;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        final long[] FinalCost = new long[1];

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                    .orderByChild(FirebaseConstant.CLIENT_HISTORY)
                    .equalTo(Client.ClientID + FirebaseConstant.JOIN + History.HistoryID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                                if (dataSnapshot.getChildren().iterator().hasNext()) {
                                    FinalCost[0] = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.COST_SO_FAR).getValue(Long.class);
                                }
                            } else {
                                //callBackListener.OnFinishedRide(FinalCost[0]);
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

        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                    .orderByChild(FirebaseConstant.CLIENT_HISTORY)
                    .equalTo(Client.ClientID + FirebaseConstant.JOIN + History.HistoryID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), Long.toString(FinalCost[0]));
                            callBackListener.OnFinishedRide(FinalCost[0]);
                        }

                        public void onCancelled(DatabaseError databaseError) {
                            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                        }
                    });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}