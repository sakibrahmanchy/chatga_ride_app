package __Firebase.FirebaseReqest;

import android.util.Log;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.ShortestDistanceMap;
import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class RequestForRide {

    private ICallbackMain callBackListener = null;
    private Pair<Double, Double> Source;
    private Pair<Double, Double> Destination;
    private ShortestDistanceMap shortestDistanceMap = null;

    public RequestForRide(Pair<Double, Double> Source, Pair<Double, Double> Destination, ICallbackMain callBackListener){
        this.Source = Source;
        this.Destination = Destination;
        this.callBackListener = callBackListener;
        shortestDistanceMap = new ShortestDistanceMap();

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.ONLINE_BUSY_RIDE).equalTo(FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for (DataSnapshot snp : dataSnapshot.getChildren()) {
                        RiderModel riderModel = new RiderModel();
                        riderModel.LoadData(snp);
                        FirebaseWrapper.getInstance().getRiderViewModelInstance().FindNearestRider(riderModel, Source, shortestDistanceMap);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                shortestDistanceMap = null;
                Log.d(FirebaseConstant.NEAREST_RIDER_ERROR, databaseError.toString());
            }
        });

        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.ONLINE_BUSY_RIDE).equalTo(FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shortestDistanceMap = null;
                if (callBackListener != null){
                    callBackListener.OnRequestForRide(true);
                }
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.NEAREST_RIDER_ERROR, databaseError.toString());
                shortestDistanceMap = null;
                callBackListener.OnRequestForRide(false);
            }
        });
    }
}
