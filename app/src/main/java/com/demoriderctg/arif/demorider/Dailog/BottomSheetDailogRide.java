package com.demoriderctg.arif.demorider.Dailog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.GoogleMap.MapActivity;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.R;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;

import static java.security.AccessController.getContext;
import static java.security.AccessController.getContext;
import static java.security.AccessController.getContext;

/**
 * Created by Arif on 12/30/2017.
 */

public class BottomSheetDailogRide extends BottomSheetDialogFragment {

    String mString;
    private TextView pathLocation;
    private TextView totalCost;
    private TextView userProfilePic;
    private Button pickUpBotton;
    private NotificationModel notificationModel;
    private static Context mContext;
    private ConnectionCheck connectionCheck;
    private Main main;

    public static BottomSheetDailogRide newInstance(String string) {
        BottomSheetDailogRide f = new BottomSheetDailogRide();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cost_bottom_sheet_dailog, container, false);
        pathLocation =(TextView) v.findViewById(R.id.path_location);
        userProfilePic =(TextView) v.findViewById(R.id.total_cost);
        pickUpBotton = (Button) v.findViewById(R.id.pickupbtn);
        connectionCheck = new ConnectionCheck(getContext());
        main = new Main(getContext());
        init();
        return v;
    }

    void init(){
        pickUpBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){

                    Double SourceLat = AppConstant.SOURCE.latitude;
                    Double SourceLan = AppConstant.SOURCE.longitude;
                    Double DestinationLat = AppConstant.DESTINATION.latitude;
                    Double DestinationLan = AppConstant.DESTINATION.longitude;
                    Pair Source = Pair.create(SourceLat,SourceLan);
                    Pair Destination = Pair.create(DestinationLat,DestinationLan);
                    main.RequestForRide(Source, Destination, AppConstant.SOURCE_NAME, AppConstant.DESTINATION_NAME);
                    Intent intent = new Intent(getContext(), SearchingDriver.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}