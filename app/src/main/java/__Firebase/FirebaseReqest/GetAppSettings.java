package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 3/13/2018.
 */

public class GetAppSettings {

    private ICallbackMain iCallbackMain;

    public GetAppSettings(ICallbackMain iCallbackMain) {
        this.iCallbackMain = iCallbackMain;
        this.Request();
    }

    public void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.APP_SETTINGS_INFO).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        DataSnapshot snp = dataSnapshot;
                        if (snp.exists()) {
                            firebaseWrapper.getAppSettingsModelInstance().LoadData(snp);
                            iCallbackMain.OnAppSettingsLoaded(true);
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.APP_SETTINGS_LOADED);
                        } else {
                            iCallbackMain.OnAppSettingsLoaded(false);
                        }
                    } else {
                        iCallbackMain.OnAppSettingsLoaded(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iCallbackMain.OnAppSettingsLoaded(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
