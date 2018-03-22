package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 11/23/2017.
 */

public class CreateClientFirstTime {

    private ClientModel Client = null;
    public ICallbackMain callBackListener = null;

    public CreateClientFirstTime(ClientModel Client, ICallbackMain callBackListener) {
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                    .push()
                    .setValue(Client, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            callBackListener.OnCreateNewRiderFirebase(true);
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.NEW_USER_CREATED);
                        }
                    });
        } catch (Exception e) {
            callBackListener.OnCreateNewRiderFirebase(false);
            e.printStackTrace();
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
