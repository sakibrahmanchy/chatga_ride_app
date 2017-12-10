package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class UpdateCostForCurrentRide {

    private CallBackListener callBackListener = null;
    private ClientModel Client = null;
    private long Cost = 0;

    public UpdateCostForCurrentRide(ClientModel Client, long Cost, CallBackListener callBackListener){
        this.Client = Client;
        this.callBackListener = callBackListener;
        this.Cost = Cost;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(FirebaseConstant.COST_OF_CURRENT_RIDE, FirebaseConstant.COST_OF_CURRENT_RIDE);
                HashMap<String, Object> UpdatedCost = new HashMap<>();
                UpdatedCost.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, Cost);
                DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                snp.getRef().updateChildren(UpdatedCost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.COST_OF_CURRENT_RIDE, databaseError.toString());
            }
        });
    }
}
