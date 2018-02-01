package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class GetCurrentRiderHistoryModel {

    private long HistoryID;
    private long ClientID;
    private ICallbackMain callBackListener = null;
    private long Time;
    private int ActionType;

    public GetCurrentRiderHistoryModel(long HistoryID, long ClientID, long Time, int ActionType, ICallbackMain callBackListener){
        this.HistoryID = HistoryID;
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;
        this.Time = Time;
        this.ActionType = ActionType;

        Request();
    }

    private void Request(){

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientID + FirebaseConstant.JOIN + HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        firebaseWrapper.getCurrentRidingHistoryModelInstance().LoadData(dataSnapshot.getChildren().iterator().next());
                        callBackListener.OnGetCurrentRiderHistoryModel(true, Time, ActionType);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientID + FirebaseConstant.JOIN + HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(FirebaseConstant.RIDING_HISTORY_LOADED, firebaseWrapper.getCurrentRidingHistoryModelInstance().HistoryID + "");
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.RIDING_HISTORY_LOADED, databaseError.toString());
            }
        });
    }
}
