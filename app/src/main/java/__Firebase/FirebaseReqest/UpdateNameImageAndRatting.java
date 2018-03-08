package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 3/8/2018.
 */

public class UpdateNameImageAndRatting {

    private ClientModel Client;
    private ICallbackMain iCallbackMain;

    public UpdateNameImageAndRatting(ClientModel Client, ICallbackMain iCallbackMain) {
        this.Client = Client;
        this.iCallbackMain = iCallbackMain;
        this.Request();
    }

    private void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        Map<String, Object> updateName = new HashMap<>();
                        updateName.put(FirebaseConstant.FULL_NAME, Client.FullName);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateName);

                        Map<String, Object> updateImage = new HashMap<>();
                        updateImage.put(FirebaseConstant.IMAGE_URL, Client.ImageUrl);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateImage);

                        Map<String, Object> updateRatting = new HashMap<>();
                        updateImage.put(FirebaseConstant.RATTING, Client.Ratting);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateRatting);

                        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, FirebaseConstant.DEVICE_TOKEN_UPDATE);
                        iCallbackMain.OnUpdateNameAndImage(true);
                    } else {
                        iCallbackMain.OnUpdateNameAndImage(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                iCallbackMain.OnUpdateNameAndImage(false);
            }
        });
    }
}