package __Firebase.FirebaseReqest;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by Arif on 3/29/2018.
 */

public class SetDeviceIDToClientTable {

    private long ClientID = 0;
    private ICallbackMain iCallbackMain;

    public SetDeviceIDToClientTable(long clientID, ICallbackMain iCallbackMain) {
        this.iCallbackMain = iCallbackMain;
        this.ClientID = clientID;
        Request();
    }

    private void Request() {

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            Map<String, Object> update = new HashMap<>();
                            update.put(FirebaseConstant.SESSION_KEY, AppConstant.SESSION_KEY);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.SESSION_KEY);
                            iCallbackMain.OnSessionKeyUpdate(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iCallbackMain.OnSessionKeyUpdate(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
