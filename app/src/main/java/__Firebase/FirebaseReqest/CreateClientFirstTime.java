package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class CreateClientFirstTime {

    private ClientModel Client = null;
    public ICallbackMain callBackListener = null;

    public CreateClientFirstTime(ClientModel Client, ICallbackMain callBackListener){
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .push()
                .setValue(Client, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        callBackListener.OnCreateNewRiderFirebase(true);
                        Log.d(FirebaseConstant.NEW_USER_CREATED, FirebaseConstant.NEW_USER_CREATED);
                    }
                });
        } catch (Exception e) {
            callBackListener.OnCreateNewRiderFirebase(false);
            e.printStackTrace();
            Log.d(FirebaseConstant.NEW_USER_ERROR, e.toString());
        }
    }
}
