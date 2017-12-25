package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class GetRiderLocation {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public GetRiderLocation(RiderModel Rider, ICallbackMain callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        Double Latitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LATITUDE).getValue(Double.class);
                        Double Longitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LONGITUDE).getValue(Double.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(FirebaseConstant.GET_UPDATE_LOCATION,  FirebaseConstant.GET_UPDATE_LOCATION);
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.GET_UPDATE_LOCATION, databaseError.toString());
            }
        });
    }
}
