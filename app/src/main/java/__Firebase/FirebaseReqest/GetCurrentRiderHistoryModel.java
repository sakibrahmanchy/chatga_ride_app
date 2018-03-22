package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.CallBackListener;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 11/24/2017.
 */

public class GetCurrentRiderHistoryModel {

    private long HistoryID;
    private long ClientID;
    private ICallbackMain callBackListener = null;
    private CallBackListener iCallBackListener = null;
    private long Time;
    private int ActionType;

    public GetCurrentRiderHistoryModel(long HistoryID, long ClientID, long Time, int ActionType, ICallbackMain callBackListener) {
        this.HistoryID = HistoryID;
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;
        this.Time = Time;
        this.ActionType = ActionType;

        Request();
    }

    public GetCurrentRiderHistoryModel(long HistoryID, long ClientID, CallBackListener callBackListener) {
        this.HistoryID = HistoryID;
        this.ClientID = ClientID;
        this.iCallBackListener = callBackListener;

        RequestForOnlyHistory();
    }

    private void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientID + FirebaseConstant.JOIN + HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {
                            DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                            if (snp.exists()) {
                                firebaseWrapper.getCurrentRidingHistoryModelInstance().LoadData(snp);
                                callBackListener.OnGetCurrentRiderHistoryModel(true, Time, ActionType);
                            }
                        }
                    } else {
                        callBackListener.OnGetCurrentRiderHistoryModel(false, Time, ActionType);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnGetCurrentRiderHistoryModel(false, Time, ActionType);
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }

        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientID + FirebaseConstant.JOIN + HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FabricExceptionLog.printLog(this.getClass().getSimpleName(), firebaseWrapper.getCurrentRidingHistoryModelInstance().HistoryID + "");
                }

                public void onCancelled(DatabaseError databaseError) {
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }

    private void RequestForOnlyHistory() {

        try {
            final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientID + FirebaseConstant.JOIN + HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {
                            DataSnapshot snap = dataSnapshot.getChildren().iterator().next();
                            if (snap.exists()) {
                                firebaseWrapper.getCurrentRidingHistoryModelInstance().LoadData(snap);
                                iCallBackListener.OnGetCurrentRiderHistoryModel(true);
                            }
                        } else {
                            iCallBackListener.OnGetCurrentRiderHistoryModel(false);
                        }
                    } else {
                        iCallBackListener.OnGetCurrentRiderHistoryModel(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iCallBackListener.OnGetCurrentRiderHistoryModel(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
