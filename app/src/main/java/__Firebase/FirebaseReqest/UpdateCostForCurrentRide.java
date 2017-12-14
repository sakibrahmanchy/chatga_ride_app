package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.CallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class UpdateCostForCurrentRide {

    private ICallbackMain callBackListener = null;
    private ClientModel Client = null;

    public UpdateCostForCurrentRide(ClientModel Client, ICallbackMain callBackListener){
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
                        UpdatedCost.put(FirebaseConstant.COST_OF_CURRENT_RIDE, Client.CostOfCurrentRide);
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        snp.getRef().updateChildren(UpdatedCost);

                        Log.d(FirebaseConstant.COST_OF_CURRENT_RIDE, FirebaseConstant.COST_OF_CURRENT_RIDE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.COST_OF_CURRENT_RIDE, databaseError.toString());
            }
        });
    }
}
