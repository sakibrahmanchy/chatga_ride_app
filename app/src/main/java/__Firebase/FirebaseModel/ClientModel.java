package __Firebase.FirebaseModel;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by User on 11/16/2017.
 */

public class ClientModel {

    public long ClientID;
    public long PhoneNumber;
    public String DeviceToken;
    public String FullName;
    public int IsSearchingOrOnRide;
    public long CostOfCurrentRide;
    public String CurrentRidingHistoryID;
    public long RideRejectedByRider;

    public ClientModel(){

    }
    public ClientModel(long _ClientID, long _PhoneNumber, String _DeviceToken, String _FullName, int _IsSearchingOrOnRide, long _CostOfCurrentRide, String _CurrentRidingHistoryID, long _RideRejectedByRider){
        this.ClientID = _ClientID;
        this.PhoneNumber = _PhoneNumber;
        this.DeviceToken = _DeviceToken;
        this.FullName = _FullName;
        this.IsSearchingOrOnRide = _IsSearchingOrOnRide;
        this.CostOfCurrentRide = _CostOfCurrentRide;
        this.CurrentRidingHistoryID = _CurrentRidingHistoryID;
        this.RideRejectedByRider = _RideRejectedByRider;
    }

    public void LoadData(DataSnapshot snapshot){

        ClientModel clientModel = snapshot.getValue(ClientModel.class);

        this.ClientID = clientModel.ClientID;
        this.PhoneNumber = clientModel.PhoneNumber;
        this.DeviceToken = clientModel.DeviceToken;
        this.FullName = clientModel.FullName;
        this.IsSearchingOrOnRide = clientModel.IsSearchingOrOnRide;
        this.CostOfCurrentRide = clientModel.CostOfCurrentRide;
        this.CurrentRidingHistoryID = clientModel.CurrentRidingHistoryID;
        this.RideRejectedByRider = clientModel.RideRejectedByRider;
    }
}
