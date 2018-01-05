package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 1/4/2018.
 */

public class SetDeviceTokenToRiderTable {

    private ClientModel Client;
    private ICallbackMain iCallbackMain = null;

    public SetDeviceTokenToRiderTable(ClientModel Client, ICallbackMain iCallbackMain){
        this.Client = Client;
        this.iCallbackMain = iCallbackMain;

        this.Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        Map<String, Object> update = new HashMap<>();
                        update.put(FirebaseConstant.DEVICE_TOKEN, Client.DeviceToken);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, FirebaseConstant.DEVICE_TOKEN_UPDATE);
                        iCallbackMain.OnSetDeviceTokenToRiderTable(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                iCallbackMain.OnSetDeviceTokenToRiderTable(false);
            }
        });
    }
}