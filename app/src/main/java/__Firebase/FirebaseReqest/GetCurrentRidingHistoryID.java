package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class GetCurrentRidingHistoryID {

    private ClientModel Client = null;
    private ICallbackMain callBackListener = null;

    public GetCurrentRidingHistoryID(ClientModel Client, ICallbackMain callBackListener){
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        final long[] HistoryID = new long[1];

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        HistoryID[0] = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDING_HISTORY_ID).getValue(Long.class);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(FirebaseConstant.HISTORY_LOADED, HistoryID[0] + "");
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.HISTORY_LOADED, databaseError.toString());
            }
        });
    }
}
