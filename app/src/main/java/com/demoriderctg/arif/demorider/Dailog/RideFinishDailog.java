package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.Rating.UserRating;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseWrapper;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Arif on 1/17/2018.
 */

public class RideFinishDailog extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Button btnOk;
    private FragmentActivity myContext;
    private Main main;
    private TextView total_cost;
    private RatingBar ratingBar;
    private NotificationCompat.Builder notification;
    private NotificationManager notificationManager;


    public RideFinishDailog(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ride_finish_dailog);
        btnOk = (Button) findViewById(R.id.btnOk);
        total_cost = (TextView) findViewById(R.id.total_cost);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnOk.setOnClickListener(this);
        main = new Main();
        total_cost.setText(AppConstant.FINAL_RIDE_COST+" TK");
        notification = new NotificationCompat.Builder(getContext());
        notificationManager = (NotificationManager) myContext.getSystemService(NOTIFICATION_SERVICE);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AppConstant.RATING = ratingBar.getRating();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                notification.setAutoCancel(true);
                notificationManager.cancel(1);
                new UserRating(getContext()).RatingDriver();
                Intent intent = new Intent(getContext(), MapActivity.class);
                myContext.startActivity(intent);
                myContext.finish();
                break;
            default:
                break;
        }
        dismiss();
    }


}
