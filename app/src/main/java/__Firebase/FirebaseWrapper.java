package __Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.*;
import __Firebase.FirebaseResponse.*;
import __Firebase.ViewModel.RiderViewModel;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseWrapper {

    private static FirebaseWrapper Instance = null;

    public DatabaseReference FirebaseRootReference;
    private FirebaseRequest FirebaseRequestInstance;
    private FirebaseResponse FirebaseResponseInstance;
    private RiderViewModel RiderViewModelInstance;
    private CurrentRidingHistoryModel CurrentRidingHistoryModelInstance;
    private RiderModel RiderModelInstance;
    private ClientModel ClientModel;

    private FirebaseWrapper(){
        FirebaseRootReference = FirebaseDatabase.getInstance().getReference();
        FirebaseRequestInstance = new FirebaseRequest();
        FirebaseResponseInstance = new FirebaseResponse();
        RiderViewModelInstance = new RiderViewModel();
        CurrentRidingHistoryModelInstance = new CurrentRidingHistoryModel();
        RiderModelInstance = new RiderModel();
        ClientModel = new ClientModel();
    }

    public static FirebaseWrapper getInstance(){
        if(Instance == null){
            Instance = new FirebaseWrapper();
        }
        return Instance;
    }

    public FirebaseRequest getFirebaseRequestInstance() {
        return FirebaseRequestInstance;
    }

    public FirebaseResponse getFirebaseResponseInstance() {
        return FirebaseResponseInstance;
    }

    public RiderViewModel getRiderViewModelInstance() {
        return RiderViewModelInstance;
    }

    public ClientModel getClientModelInstance() {
        return ClientModel;
    }

    public RiderModel getRiderModelInstance() {
        return RiderModelInstance;
    }

    public CurrentRidingHistoryModel getCurrentRidingHistoryModelInstance() {
        return CurrentRidingHistoryModelInstance;
    }

    public static String getDeviceToken(){
        return  FirebaseInstanceId.getInstance().getToken().toString();
    }
}
