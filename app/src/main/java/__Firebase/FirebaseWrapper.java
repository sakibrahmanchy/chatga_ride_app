package __Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.__FirebaseRequest;
import __Firebase.FirebaseResponse.FirebaseResponse;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.ViewModel.RiderViewModel;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseWrapper {

    private volatile static FirebaseWrapper Instance = null;

    public DatabaseReference FirebaseRootReference;
    private __FirebaseRequest FirebaseRequestInstance;
    private FirebaseResponse FirebaseResponseInstance;
    private RiderViewModel RiderViewModelInstance;
    private CurrentRidingHistoryModel CurrentRidingHistoryModelInstance;
    private RiderModel RiderModelInstance;
    private ClientModel ClientModel;
    private NotificationModel NotificationModel;

    private FirebaseWrapper() {
        FirebaseRootReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseWrapper getInstance() {
        if (Instance == null) {
            synchronized (FirebaseWrapper.class) {
                if (Instance == null)
                    Instance = new FirebaseWrapper();
            }
        }
        return Instance;
    }

    public __FirebaseRequest getFirebaseRequestInstance() {
        if (FirebaseRequestInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (FirebaseRequestInstance == null)
                    FirebaseRequestInstance = new __FirebaseRequest();
            }
        }
        return FirebaseRequestInstance;
    }

    public FirebaseResponse getFirebaseResponseInstance() {
        return FirebaseResponseInstance;
    }

    public RiderViewModel getRiderViewModelInstance() {
        if (RiderViewModelInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (RiderViewModelInstance == null)
                    RiderViewModelInstance = new RiderViewModel();
            }
        }
        return RiderViewModelInstance;
    }

    public ClientModel getClientModelInstance() {
        if (ClientModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (ClientModel == null)
                    ClientModel = new ClientModel();
            }
        }
        return ClientModel;
    }

    public RiderModel getRiderModelInstance() {
        if (RiderModelInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (RiderModelInstance == null)
                    RiderModelInstance = new RiderModel();
            }
        }
        return RiderModelInstance;
    }

    public CurrentRidingHistoryModel getCurrentRidingHistoryModelInstance() {
        if (CurrentRidingHistoryModelInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (CurrentRidingHistoryModelInstance == null)
                    CurrentRidingHistoryModelInstance = new CurrentRidingHistoryModel();
            }
        }
        return CurrentRidingHistoryModelInstance;
    }

    public NotificationModel getNotificationModelInstance() {
        if (NotificationModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (NotificationModel == null)
                    NotificationModel = new NotificationModel();
            }
        }
        return NotificationModel;
    }

    public static String getDeviceToken() {
        return FirebaseInstanceId.getInstance().getToken().toString();
    }
}