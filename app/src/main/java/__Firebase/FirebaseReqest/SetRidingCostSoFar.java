package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class SetRidingCostSoFar {

    private long ClientID;
    private long Cost;
    private CallBackListener callBackListener = null;

    public SetRidingCostSoFar(long ClientID, long Cost, CallBackListener callBackListener){
        this.Cost = Cost;
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .orderByChild(FirebaseConstant.CLIENT_ID).equalTo(ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    HashMap<String, Object> CostSoFar = new HashMap<>();
                    CostSoFar.put(FirebaseConstant.COST_OF_CURRENT_RIDE, Cost);
                    dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CostSoFar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
