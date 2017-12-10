package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/5/2017.
 */

public class RequestForRiderLocation {

    private RiderModel Rider = null;
    private CallBackListener callBackListener = null;

    public RequestForRiderLocation(RiderModel Rider, CallBackListener callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query updateLocation = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);

        updateLocation.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();

                        if (dsp.getChildren().iterator().hasNext()) {

                            Map<String, Object> RequestForUpdateLocation = new HashMap<>();
                            RequestForUpdateLocation.put(FirebaseConstant.REQUEST_FOR_UPDATE_LOCATION, Rider.CurrentRiderLocation.RequestForUpdateLocation);
                            dsp.getChildren().iterator().next().getRef().updateChildren(RequestForUpdateLocation);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
