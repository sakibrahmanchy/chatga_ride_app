package __Firebase.FirebaseReqest;

import android.util.Log;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.CallBackInstance.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class RequestForRide {

    private CallBackListener callBackListener = null;
    private Pair<Double, Double> Source;
    private Pair<Double, Double> Destination;

    public RequestForRide(Pair<Double, Double> Source, Pair<Double, Double> Destination, CallBackListener callBackListener){
        this.Source = Source;
        this.Destination = Destination;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.ONLINE_BUSY_RIDE).equalTo(FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snp: dataSnapshot.getChildren()){
                    RiderModel riderModel = new RiderModel();
                    riderModel.LoadData(snp);
                    //MainActivity.firebaseWrapper.getRiderViewModelInstance().FindNearestRider(riderModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.NEAREST_RIDER_ERROR, databaseError.toString());
            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.ONLINE_BUSY_RIDE).equalTo(FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(FirebaseConstant.NEAREST_RIDER, MainActivity.firebaseWrapper.getRiderViewModelInstance().NearestRider.RiderID + "");
                if (callBackListener != null){
                    callBackListener.onRequestForRide(true);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.NEAREST_RIDER_ERROR, databaseError.toString());
                callBackListener.onRequestForRide(false);
            }
        });
    }
}
