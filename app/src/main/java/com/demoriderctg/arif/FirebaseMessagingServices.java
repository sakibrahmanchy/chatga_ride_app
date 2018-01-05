package com.demoriderctg.arif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseMessagingServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationModel notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();

        if (remoteMessage.getData().size() > 0) {
            notificationModel.title = remoteMessage.getData().get("title");
            notificationModel.body = remoteMessage.getData().get("body");
            notificationModel.riderId = Long.parseLong(remoteMessage.getData().get("riderId"));
            notificationModel.riderName = remoteMessage.getData().get("riderName");
            notificationModel.riderPhone = remoteMessage.getData().get("riderPhone");
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(notificationModel.title);
        builder.setContentText(notificationModel.body);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        Log.d(FirebaseConstant.RECEIVED_NOTIFICATION, FirebaseConstant.RECEIVED_NOTIFICATION);
    }

    @Override
    public void onDeletedMessages() {

    }
}