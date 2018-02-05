package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 2/6/2018.
 */

public class FinishRide {

    private ICallbackMain callBackListener = null;
    private ClientModel Client = null;

    public FinishRide(ClientModel Client, ICallbackMain callBackListener){
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if(dataSnapshot.getChildren().iterator().hasNext()) {
                        HashMap<String, Object> UpdatedCost = new HashMap<>();
                        UpdatedCost.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, Client.CurrentRidingHistoryID);
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        snp.getRef().updateChildren(UpdatedCost);

                        Log.d(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, FirebaseConstant.CURRENT_RIDING_HISTORY_ID);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, databaseError.toString());
            }
        });
    }
}
