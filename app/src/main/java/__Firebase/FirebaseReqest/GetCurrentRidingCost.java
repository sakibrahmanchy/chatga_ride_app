package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallBackFinishedRide;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 1/15/2018.
 */

public class GetCurrentRidingCost {

    private ClientModel Client = null;
    private CurrentRidingHistoryModel History = null;
    private ICallBackFinishedRide callBackListener = null;

    public GetCurrentRidingCost(ClientModel Client, CurrentRidingHistoryModel History, ICallBackFinishedRide callBackListener){
        this.Client = Client;
        this.History = History;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        final long[] FinalCost = new long[1];

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                .orderByChild(FirebaseConstant.CLIENT_HISTORY)
                .equalTo(Client.ClientID + FirebaseConstant.JOIN + History.HistoryID)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        FinalCost[0] = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.COST_SO_FAR).getValue(Long.class);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                .orderByChild(FirebaseConstant.CLIENT_HISTORY)
                .equalTo(Client.ClientID + FirebaseConstant.JOIN + History.HistoryID)
                .addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(FirebaseConstant.HISTORY_LOADED, FinalCost[0] + "");
                callBackListener.OnFinishedRide(FinalCost[0]);
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.HISTORY_LOADED, databaseError.toString());
            }
        });
    }
}