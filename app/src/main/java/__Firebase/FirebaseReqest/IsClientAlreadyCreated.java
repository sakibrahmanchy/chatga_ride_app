package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.CallBackInstance.ICallbackMain;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/14/2017.
 */

public class IsClientAlreadyCreated {

    private long ClientID;
    private ICallbackMain callBackListener = null;

    public IsClientAlreadyCreated(long ClientID, ICallbackMain callBackListener) {
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(ClientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    callBackListener.OnIsClientAlreadyCreated(true);
                } else {
                    callBackListener.OnIsClientAlreadyCreated(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
