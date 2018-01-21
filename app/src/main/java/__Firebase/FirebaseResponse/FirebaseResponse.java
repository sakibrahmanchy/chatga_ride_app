package __Firebase.FirebaseResponse;

import android.util.Log;
import android.util.Pair;

import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogRide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseResponse {

    private static boolean IS_INITIALIZED = false;
    private static long ClientID = 0;
    private static long HistoryID = 0;
    private static long Time = 0;

    public FirebaseResponse() {
    }

    public static void InitialAcceptanceOfRideResponse(ClientModel Client) {

        ClientID = Client.ClientID;

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(ClientID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().child(FirebaseConstant.CURRENT_RIDING_HISTORY_ID).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    if (IS_INITIALIZED == true) {
                                        Pair Data = FirebaseUtilMethod.GetHistoryAndTime(dataSnapshot.getValue().toString());
                                        HistoryID = (long)Data.first;
                                        Time = (long)Data.second;
                                        if (HistoryID > 0) {
                                            new Main().GetCurrentRiderHistoryModel(
                                                   FirebaseWrapper.getInstance().getClientModelInstance(),
                                                    HistoryID,
                                                    Time
                                            );
                                        }
                                    }
                                    IS_INITIALIZED = true;
                                    Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_CLIENT, ":: " + dataSnapshot.getValue().toString());
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void RideStartedResponse(long historyID) {

        HistoryID = historyID;
        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(HistoryID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().child(FirebaseConstant.IS_RIDE_START).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    long Time = Long.parseLong(dataSnapshot.getValue().toString());
                                    if (Time != -1) {
                                        firebaseWrapper.getCurrentRidingHistoryModelInstance().IsRideStart = Time;
                                        new RiderStarted(Time);
                                    }
                                }
                                Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_CLIENT, ":: " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void RideFinishedResponse(long historyID) {

        HistoryID = historyID;
        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(HistoryID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().child(FirebaseConstant.IS_RIDE_FINISHED).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    long Time = Integer.parseInt(dataSnapshot.getValue().toString());
                                    if (Time != -1) {
                                        firebaseWrapper.getCurrentRidingHistoryModelInstance().IsRideFinished = Time;
                                        new RiderFinished(Time);
                                    }
                                }
                                Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_CLIENT, ":: " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void RideCanceledByRiderResponse(long historyID) {

        HistoryID = historyID;
        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(HistoryID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().child(FirebaseConstant.RIDE_CANCEL_BY_RIDER).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    long Time = Integer.parseInt(dataSnapshot.getValue().toString());
                                    if (Time != -1) {
                                        firebaseWrapper.getCurrentRidingHistoryModelInstance().RideCanceledByRider = Time;
                                        new RideCanceledByRider(Time);
                                    }
                                }
                                Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_CLIENT, ":: " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}