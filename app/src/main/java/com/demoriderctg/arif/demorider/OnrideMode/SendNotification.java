package com.demoriderctg.arif.demorider.OnrideMode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.R;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Arif on 1/13/2018.
 */

public class SendNotification {


    private NotificationCompat.Builder notification;
    private Notification note;
    private NotificationManager notificationManager;
    private Context mContext;
    private NotificationModel notificationModel;
    public SendNotification(Context context) {
        this.mContext = context;
        notification = new NotificationCompat.Builder(mContext);
        notification.setAutoCancel(false);
        notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
    }

    public  void Notification(String NotificationTicker, String NotificationTitle, String NotficationBoday){

        AppConstant.NOTIFICATION_ID=1;
        notification.setSmallIcon(R.drawable.logo);
        notification.setTicker(NotificationTicker);
        notification.setContentTitle(NotificationTicker);
        notification.setOnlyAlertOnce(true);
        notification.setContentText(NotficationBoday);
        notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        Intent intent = new Intent(mContext,FirstAppLoadingActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(mContext,0,intent,0);
        notification.setContentIntent(pendingIntent);
        note = notification.build();
        notificationManager.notify(AppConstant.NOTIFICATION_ID,note);



    }
}
