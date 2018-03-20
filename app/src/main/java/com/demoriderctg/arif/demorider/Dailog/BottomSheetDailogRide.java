package com.demoriderctg.arif.demorider.Dailog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.demoriderctg.arif.demorider.Adapters.Promotion.PromotionAdapter;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.CostEstimation.CostEstimation;
import com.demoriderctg.arif.demorider.InternetConnection.ConnectionCheck;
import com.demoriderctg.arif.demorider.LoginActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.RestAPI.ApiClient;
import com.demoriderctg.arif.demorider.RestAPI.ApiInterface;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.User;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscountResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ACCESSIBILITY_SERVICE;
import static com.demoriderctg.arif.demorider.MainActivity.TAG;

/**
 * Created by Arif on 12/30/2017.
 */

public class BottomSheetDailogRide extends BottomSheetDialogFragment {

    String mString;
    private TextView pathLocation;
    private ImageView fare_info;
    private TextView total_cost;
    private Button pickUpBotton;
    private NotificationModel notificationModel;
    private static Context mContext;
    private ConnectionCheck connectionCheck;
    private Main main;
    private long totalCost;
    private CostEstimation costEstimation = new CostEstimation();
    private ProgressDialog dialog;
    private ApiInterface apiService;
    private TextView EstimateFare,EstimatedFareAfterDiscount;

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
        total_cost =(TextView) v.findViewById(R.id.total_estimate);
        pickUpBotton = (Button) v.findViewById(R.id.pickupbtn);
        connectionCheck = new ConnectionCheck(getContext());
        fare_info = (ImageView) v.findViewById(R.id.fareInfo);
        EstimateFare = v.findViewById(R.id.total_estimated_fare);
        EstimatedFareAfterDiscount = v.findViewById(R.id.total_estimated_fare_after_discount);
        EstimatedFareAfterDiscount.setVisibility(View.GONE);
        //pathLocation.setText(AppConstant.SOURCE_NAME + " To "+AppConstant.DESTINATION_NAME);


        costEstimation.getTotalCost(AppConstant.DISTANCE, AppConstant.DURATION);
        total_cost.setText("Estimated Distance: "+AppConstant.DISTANCE+" \nEstimated Time: "+AppConstant.DURATION);
        EstimateFare.setText("Estimated Fare: ৳"+AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT);
        if(AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT !=0){
            EstimatedFareAfterDiscount.setVisibility(View.VISIBLE);
            EstimateFare.setPaintFlags(EstimateFare.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            EstimatedFareAfterDiscount.setText("Estimated Fare After Discount: ৳"+AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT);
        }
        main = new Main();
        init();
        return v;
    }

    void init(){
        pickUpBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( connectionCheck.isNetworkConnected()){

                    Double SourceLat = AppConstant.SOURCE.latitude;
                    Double SourceLan = AppConstant.SOURCE.longitude;
                    Double DestinationLat = AppConstant.DESTINATION.latitude;
                    Double DestinationLan = AppConstant.DESTINATION.longitude;
                    Pair Source = Pair.create(SourceLat,SourceLan);
                    Pair Destination = Pair.create(DestinationLat,DestinationLan);
                    int DiscountId=0;
                    if( AppConstant.userDiscount !=null && AppConstant.userDiscount.getDiscountId()>0){
                        DiscountId =AppConstant.userDiscount.getDiscountId();
                    }
                    AppConstant.TOTAL_COST = (long)AppConstant.ESTIMATE_FARE_WITHOUT_DISCOUNT;
                    if(AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT !=0){
                        AppConstant.TOTAL_COST = (long)AppConstant.ESTIMATED_FARE_AFTER_DISCOUNT;

                    }


                    FirebaseWrapper.getInstance().getRiderViewModelInstance().ClearData(true);
                    main.RequestForRide(Source, Destination, AppConstant.SOURCE_NAME, AppConstant.DESTINATION_NAME, AppConstant.TOTAL_COST, DiscountId);
                    FirebaseConstant.VAR_CAN_REQUEST_FOR_RIDE = true;
                    Intent intent = new Intent(getContext(), FullMapSearching.class);
                    startActivity(intent);
                    dismiss();

                }
                else{
                    Toast.makeText(getContext(), "CONNECTION LOST", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fare_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FareCostInfo fareCostInfo = new FareCostInfo(getActivity());
                fareCostInfo.show();
            }
        });
    }







}
