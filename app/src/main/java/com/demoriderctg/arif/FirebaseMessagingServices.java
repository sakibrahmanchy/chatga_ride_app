package com.demoriderctg.arif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.NotificationActivity;
import com.demoriderctg.arif.demorider.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class
FirebaseMessagingServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationModel notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        if (remoteMessage.getData().containsKey("typeId")) {
            if (remoteMessage.getData().get("typeId").equals("2")) {
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(sound);
                builder.setContentTitle(remoteMessage.getData().get("title"));
                builder.setContentText(remoteMessage.getData().get("body"));
                builder.setAutoCancel(true);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());
            }
        } else if (remoteMessage.getData().containsKey(AppConstant.ACTION_TYPE)) {

            int action = Integer.parseInt(remoteMessage.getData().get(AppConstant.ACTION_TYPE));
            switch (action) {
                case AppConstant.ACTION_INITIAL_ACCEPTANCE_NOTIFICATION: {
                    this.ACTION_INITIAL_ACCEPTANCE_NOTIFICATION(action, remoteMessage);
                    break;
                }
                case AppConstant.ACTION_FINAL_ACCEPTANCE_NOTIFICATION: {
                    this.ACTION_FINAL_ACCEPTANCE_NOTIFICATION(action, remoteMessage);
                    break;
                }
                case AppConstant.ACTION_FINISH_RIDE_NOTIFICATION: {
                    this.ACTION_FINISH_RIDE_NOTIFICATION(action, remoteMessage);
                    break;
                }
                case AppConstant.ACTION_CANCEL_RIDE_NOTIFICATION: {
                    this.ACTION_CANCEL_RIDE_NOTIFICATION(action, remoteMessage);
                    break;
                }
            }
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    private void ACTION_INITIAL_ACCEPTANCE_NOTIFICATION(int action, RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){

            if(remoteMessage.getData().containsKey(AppConstant.RIDER_ID)){
                long riderId = Long.parseLong(remoteMessage.getData().get(AppConstant.RIDER_ID));
            }
            /*Your Own Pending Intent*/
            AppConstant.INITIAL_RIDE_ACCEPT=1;
            Intent intent = new Intent(this, FirstAppLoadingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            this.Notify(AppConstant.INITIAL_ACCEPTANCE_TITLE, AppConstant.INITIAL_ACCEPTANCE_BODY, pendingIntent);
        }
    }

    private void ACTION_FINAL_ACCEPTANCE_NOTIFICATION(int action, RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){

            AppConstant.START_RIDE=true;
            if(remoteMessage.getData().containsKey(AppConstant.RIDER_ID)){
                long riderId = Long.parseLong(remoteMessage.getData().get(AppConstant.RIDER_ID));
            }
             /*Your Own Pending Intent*/
            Intent intent = new Intent(this, FirstAppLoadingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            this.Notify(AppConstant.FINAL_ACCEPTANCE_TITLE, AppConstant.FINAL_ACCEPTANCE_BODY, pendingIntent);
        }
    }

    private void ACTION_FINISH_RIDE_NOTIFICATION(int action, RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){

            AppConstant.FINISH_RIDE=true;
            if(remoteMessage.getData().containsKey(AppConstant.RIDER_ID)){
                long riderId = Long.parseLong(remoteMessage.getData().get(AppConstant.RIDER_ID));
            }
            /* Get the final cost */
            if(remoteMessage.getData().containsKey(AppConstant.FINAL_COST)){
                long finalCost = Long.parseLong(remoteMessage.getData().get(AppConstant.FINAL_COST));
                AppConstant.TOTAL_COST=finalCost;
            }

            /*Your Own Pending Intent*/
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            this.Notify(AppConstant.FINISH_RIDE_TITLE, AppConstant.FINISH_RIDE_BODY, pendingIntent);
        }
    }

    private void ACTION_CANCEL_RIDE_NOTIFICATION(int action, RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){

            if(remoteMessage.getData().containsKey(AppConstant.RIDER_ID)){
                long riderId = Long.parseLong(remoteMessage.getData().get(AppConstant.RIDER_ID));
            }
            /*Your Own Pending Intent*/
            Intent intent = new Intent(this, FirstAppLoadingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            this.Notify(AppConstant.CANCEL_RIDE_TITLE, AppConstant.CANCEL_RIDE_BODY, pendingIntent);
        }
    }

    private void Notify(String Title, String Body, PendingIntent pendingIntent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);
        builder.setContentTitle(Title);
        builder.setContentText(Body);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}