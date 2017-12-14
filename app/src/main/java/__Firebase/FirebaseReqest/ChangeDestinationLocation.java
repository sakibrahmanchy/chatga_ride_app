package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.CallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class ChangeDestinationLocation {

    private CurrentRidingHistoryModel HistoryModel = null;
    private ClientModel ClientModel = null;
    private ICallbackMain callBackListener = null;

    public ChangeDestinationLocation(final CurrentRidingHistoryModel HistoryModel, ClientModel ClientModel, ICallbackMain callBackListener){
        this.HistoryModel = HistoryModel;
        this.ClientModel = ClientModel;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.CLIENT_HISTORY).equalTo(ClientModel.ClientID + FirebaseConstant.JOIN + HistoryModel.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildren().iterator().hasNext()) {

                    HashMap<String, Object> Latitude = new HashMap<>();
                    Latitude.put(FirebaseConstant.LATITUDE, HistoryModel.EndingLocation.Latitude);
                    dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.ENDING_LOCATION).getRef().updateChildren(Latitude);

                    HashMap<String, Object> Longitude = new HashMap<>();
                    Longitude.put(FirebaseConstant.LONGITUDE, HistoryModel.EndingLocation.Longitude);
                    dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.ENDING_LOCATION).getRef().updateChildren(Longitude);

                    Log.d(FirebaseConstant.UPDATE_LOCATION, FirebaseConstant.UPDATE_LOCATION);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
