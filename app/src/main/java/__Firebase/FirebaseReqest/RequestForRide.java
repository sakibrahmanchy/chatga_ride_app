package __Firebase.FirebaseReqest;

import android.util.Log;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.ShortestDistanceMap;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 11/23/2017.
 */

public class RequestForRide {

    private ICallbackMain callBackListener = null;
    private Pair<Double, Double> Source;
    private Pair<Double, Double> Destination;
    private ArrayList<RiderModel> RiderList = null;

    public RequestForRide(Pair<Double, Double> Source, Pair<Double, Double> Destination, ICallbackMain callBackListener) {
        this.Source = Source;
        this.Destination = Destination;
        this.callBackListener = callBackListener;
        this.RiderList = new ArrayList<>();

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER)
                .orderByChild(FirebaseConstant.ONLINE_BUSY_RIDE)
                .equalTo(FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            for (DataSnapshot snp : dataSnapshot.getChildren()) {
                                RiderModel riderModel = new RiderModel();
                                riderModel.LoadData(snp);
                                RiderList.add(riderModel);
                            }
                            callBackListener.OnRequestForRide(RiderList);
                        } else {
                            callBackListener.OnRequestForRide(RiderList);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(FirebaseConstant.NEAREST_RIDER_ERROR, databaseError.toString());
                    }
                });
    }
}
