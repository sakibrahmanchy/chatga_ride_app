package __Firebase.Notification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.MainActivity;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.ICallBackInstance.INotificationListener;

/**
 * Created by User on 2/13/2018.
 */

public class NotificationWrapper extends AppCompatActivity implements INotificationListener {

    private static Context context = MapActivity.getContextOfApplication();
    public NotificationWrapper(){ }

    public boolean SendCancelRide(final RiderModel Rider, final ClientModel Client, INotificationListener iNotificationListener){

        if(iNotificationListener == null)   iNotificationListener = this;

        if(Rider == null || Client == null || iNotificationListener == null)    return false;
        if(Rider.RiderID < 1 || Client.ClientID < 1 || Rider.DeviceToken.isEmpty())    return false;

        SendCancelRide pendingTask = new SendCancelRide(this, iNotificationListener);
        pendingTask.execute(
                Long.toString(Client.ClientID),
                Rider.DeviceToken
        );
        finish();
        return true;
    }

    @Override
    public void OnNotificationResponse(boolean value, int action) {

    }
}
