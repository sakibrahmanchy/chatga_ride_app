package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/24/2017.
 */

public class GetRiderLocation {

    private RiderModel Rider = null;
    private CallBackListener callBackListener = null;

    public GetRiderLocation(RiderModel Rider, CallBackListener callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER)
                .orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    Double Latitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LATITUDE).getValue(Double.class);
                    Double Longitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LONGITUDE).getValue(Double.class);

                    //MainActivity.firebaseWrapper.getRiderViewModelInstance().NearestRider.CurrentRiderLocation.Latitude = Latitude;
                    //MainActivity.firebaseWrapper.getRiderViewModelInstance().NearestRider.CurrentRiderLocation.Longitude = Longitude;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER)
                .orderByChild(FirebaseConstant.RIDER_ID)
                .equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(FirebaseConstant.GET_UPDATE_LOCATION,   MainActivity.firebaseWrapper.getRiderViewModelInstance().NearestRider.CurrentRiderLocation.Longitude + "");
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.GET_UPDATE_LOCATION, databaseError.toString());
            }
        });
    }
}
