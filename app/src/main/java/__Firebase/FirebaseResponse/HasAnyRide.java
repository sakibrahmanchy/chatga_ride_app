package __Firebase.FirebaseResponse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 2/5/2018.
 */

public class HasAnyRide {

    private long ClientID = 0;
    private ICallbackMain callBackListener = null;

    public HasAnyRide(long ClientID, ICallbackMain callBackListener) {
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(this.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                    if (snp.exists()) {
                        firebaseWrapper.getClientModelInstance().LoadData(snp);
                        Log.d(FirebaseConstant.CLIENT_LOADED, FirebaseConstant.CLIENT_LOADED);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnHasAnyRide(false);
            }
        });
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(this.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callBackListener.OnHasAnyRide(true);
                } else {
                    callBackListener.OnHasAnyRide(false);
                }
            }
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnHasAnyRide(false);
                Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, databaseError.toString());
            }
        });
    }
}
