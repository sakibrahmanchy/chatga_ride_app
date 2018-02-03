package com.demoriderctg.arif.demorider.Dailog;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

/**
 * Created by Arif on 2/3/2018.
 */

public class BottomSheetDailogInRideMode extends BottomSheetDialogFragment {
    String mString;
    private TextView userProfileName;
    private TextView userRidingPath;
    private ImageView userProfilePic;
    private ImageButton call;
    private NotificationModel notificationModel;
    private BottomSheetBehavior mBottomSheetBehavior;

    public static BottomSheetDailogInRideMode newInstance(String string) {
        BottomSheetDailogInRideMode f = new BottomSheetDailogInRideMode();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
        notificationModel  = FirebaseWrapper.getInstance().getNotificationModelInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dailog_ride, container, false);

        userProfilePic =(ImageView) v.findViewById(R.id.profile);
        call = (ImageButton) v.findViewById(R.id.calling_request);
        userRidingPath =(TextView) v.findViewById(R.id.location);

        init();
        return v;
    }

    void init(){
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0"+notificationModel.riderPhone)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


}
