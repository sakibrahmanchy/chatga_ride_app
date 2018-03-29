package __Firebase.FirebaseReqest;

import android.util.Log;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by Arif on 3/29/2018.
 */

public class GetCurrentSessionKey {

    private long ClientID = 0;
    private ICallbackMain iCallbackMain = null;

    public GetCurrentSessionKey(long clientID, ICallbackMain iCallbackMain) {
        this.ClientID = clientID;
        this.iCallbackMain = iCallbackMain;
        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(ClientID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {
                            String sessionKey = dataSnapshot.getChildren().iterator().next().child(FirebaseConstant.SESSION_KEY).getValue(String.class);
                            iCallbackMain.OnGetSessionKey(true, sessionKey);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iCallbackMain.OnGetSessionKey(false, FirebaseConstant.Empty);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
