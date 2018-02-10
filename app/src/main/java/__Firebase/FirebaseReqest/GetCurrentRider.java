package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.CallBackListener;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.ICallBackInstance.IGetCurrentRider;

/**
 * Created by User on 1/24/2018.
 */

public class GetCurrentRider {

    private long RiderID = 0;
    private ICallbackMain callBackListener = null;
    private IGetCurrentRider iGetCurrentRider = null;
    private CallBackListener iCallBackListener = null;

    public GetCurrentRider(long RiderID, ICallbackMain callBackListener, IGetCurrentRider iGetCurrentRider) {
        this.RiderID = RiderID;
        this.callBackListener = callBackListener;
        this.iGetCurrentRider = iGetCurrentRider;

        Request();
    }

    public GetCurrentRider(long RiderID, CallBackListener callBackListener) {
        this.RiderID = RiderID;
        this.iCallBackListener = callBackListener;

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER)
                .orderByChild(FirebaseConstant.RIDER_ID)
                .equalTo(this.RiderID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            if (dataSnapshot.getChildren().iterator().hasNext()) {
                                DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                                FirebaseWrapper.getInstance().getRiderModelInstance().LoadData(snp);
                            }
                        } else {
                            if (iGetCurrentRider != null) iGetCurrentRider.OnGetCurrentRider(false);
                            if (iCallBackListener != null) iCallBackListener.OnGetCurrentRider(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (iGetCurrentRider != null) iGetCurrentRider.OnGetCurrentRider(false);
                        if (iCallBackListener != null) iCallBackListener.OnGetCurrentRider(false);
                        Log.d(FirebaseConstant.RIDER_LOADED_ERROR, databaseError.toString());
                    }
                });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER)
                .orderByChild(FirebaseConstant.RIDER_ID)
                .equalTo(this.RiderID)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (iGetCurrentRider != null) iGetCurrentRider.OnGetCurrentRider(true);
                            if (iCallBackListener != null) iCallBackListener.OnGetCurrentRider(true);
                            Log.d(FirebaseConstant.RIDER_LOADED, FirebaseConstant.RIDER_LOADED + FirebaseWrapper.getInstance().getRiderModelInstance().FullName);
                        } else {
                            if (iGetCurrentRider != null) iGetCurrentRider.OnGetCurrentRider(false);
                            if (iCallBackListener != null) iCallBackListener.OnGetCurrentRider(false);
                        }
                    }
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(FirebaseConstant.RIDER_LOADED_ERROR, databaseError.toString());
                        if (iGetCurrentRider != null) iGetCurrentRider.OnGetCurrentRider(false);
                        if (iCallBackListener != null) iCallBackListener.OnGetCurrentRider(false);
                    }
                });
    }
}
