package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.IGerRiderLocation;

/**
 * Created by User on 11/24/2017.
 */

public class GetRiderLocation {

    private RiderModel Rider = null;
    private IGerRiderLocation iGerRiderLocation = null;

    public GetRiderLocation(RiderModel Rider, IGerRiderLocation iGerRiderLocation) {
        this.Rider = Rider;
        this.iGerRiderLocation = iGerRiderLocation;

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = null;
        try {
            firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {
                            Double Latitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LATITUDE).getValue(Double.class);
                            Double Longitude = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.CURRENT_RIDER_LOCATION).child(FirebaseConstant.LONGITUDE).getValue(Double.class);
                            iGerRiderLocation.OnGerRiderLocation(true, Latitude, Longitude);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iGerRiderLocation.OnGerRiderLocation(false, 0d, 0d);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }

        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.GET_UPDATE_LOCATION);
                }

                public void onCancelled(DatabaseError databaseError) {
                    iGerRiderLocation.OnGerRiderLocation(false, 0d, 0d);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
