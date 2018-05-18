package __Firebase.FirebaseModel;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 4/17/2018.
 */

public class RequestDTO {

    public long requestID;
    public long clientID;
    public long riderID;
    public long distance;
    public String sourceName;
    public String destinationName;
    public String sourceLatLng;
    public String destinationLatLng;

    public RequestDTO(long _requestID, long _clientID, long _riderID, long _distance, String _sourceName, String _destinationName, String _sourceLatLng, String _destinationLatLan) {
        this.requestID = _requestID;
        this.clientID = _clientID;
        this.riderID = _riderID;
        this.requestID = _requestID;
        this.distance = _distance;
        this.sourceName = _sourceName;
        this.destinationName = _destinationName;
        this.sourceLatLng = _sourceLatLng;
        this.destinationLatLng = _destinationLatLan;
    }

    public void request(RequestDTO requestDTO) {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.REQUEST)
                    .push()
                    .setValue(requestDTO, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.REQUEST_ADDED_TO_SERVER);
                        }
                    });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
