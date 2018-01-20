package __Firebase.FirebaseModel;

import android.util.Pair;

import com.google.firebase.database.DataSnapshot;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 11/16/2017.
 */

public class CurrentRidingHistoryModel {

    public static class Location{
        public double Latitude;
        public double Longitude;

        public Location(){
        }

        public Location(double _Latitude, double _Longitude){
            this.Latitude = _Latitude;
            this.Longitude = _Longitude;
        }
    }

    public long HistoryID;
    public long  ClientID;
    public long  RiderID;
    public String Client_History;
    public String Rider_History;
    public Location StartingLocation;
    public Location EndingLocation;
    public long CostSoFar;
    public int IsRideStart;
    public int IsRideFinished;
    public int RideCanceledByClient;
    public int RideCanceledByRider;

    public CurrentRidingHistoryModel(){
        this.StartingLocation = new Location();
        this.EndingLocation = new Location();
    }

    public CurrentRidingHistoryModel(long _HistoryID, long _ClientID, long  _RiderID, String _Client_History, String _Rider_History, Pair<Double, Double> _StartingLocation, Pair<Double, Double> _EndingLocation, long _CostSoFar, int _IsRideStart, int _IsRideFinished, int _RideCanceledByClient, int _RideCanceledByRider){

        this.HistoryID = _HistoryID;
        this.ClientID = _ClientID;
        this.RiderID = _RiderID;
        this.Client_History = _Client_History;
        this.Rider_History = _Rider_History;
        this.StartingLocation = new Location(_StartingLocation.first, _StartingLocation.second);
        this.EndingLocation = new Location(_EndingLocation.first, _EndingLocation.second);;
        this.CostSoFar = _CostSoFar;
        this.IsRideStart = _IsRideStart;
        this.IsRideFinished = _IsRideFinished;
        this.RideCanceledByClient = _RideCanceledByClient;
        this.RideCanceledByRider = _RideCanceledByRider;
    }

    public void LoadData(DataSnapshot snapshot){

        CurrentRidingHistoryModel currentRidingHistoryModel = snapshot.getValue(CurrentRidingHistoryModel.class);

        this.HistoryID = currentRidingHistoryModel.HistoryID;
        this.ClientID = currentRidingHistoryModel.ClientID;
        this.RiderID = currentRidingHistoryModel.RiderID;
        this.Client_History = currentRidingHistoryModel.Client_History;
        this.Rider_History = currentRidingHistoryModel.Rider_History;
        this.StartingLocation = new Location(currentRidingHistoryModel.StartingLocation.Latitude, currentRidingHistoryModel.StartingLocation.Longitude);
        this.EndingLocation = new Location(currentRidingHistoryModel.EndingLocation.Latitude, currentRidingHistoryModel.EndingLocation.Longitude);;
        this.CostSoFar = currentRidingHistoryModel.CostSoFar;
        this.IsRideStart = currentRidingHistoryModel.IsRideStart;
        this.IsRideFinished = currentRidingHistoryModel.IsRideFinished;
        this.RideCanceledByClient = currentRidingHistoryModel.RideCanceledByClient;
        this.RideCanceledByRider = currentRidingHistoryModel.RideCanceledByRider;
    }

    public void ClearData(){

        this.HistoryID = FirebaseConstant.UNDEFINE;
        this.ClientID = FirebaseConstant.UNDEFINE;
        this.RiderID = FirebaseConstant.UNDEFINE;
        this.Client_History = FirebaseConstant.Empty;
        this.Rider_History = FirebaseConstant.Empty;
        this.StartingLocation.Longitude = FirebaseConstant.UNDEFINE;
        this.StartingLocation.Latitude = FirebaseConstant.UNDEFINE;
        this.EndingLocation.Longitude = FirebaseConstant.UNDEFINE;
        this.EndingLocation.Latitude = FirebaseConstant.UNDEFINE;
        this.CostSoFar = FirebaseConstant.UNDEFINE;
        this.IsRideStart = FirebaseConstant.UNDEFINE;
        this.IsRideFinished = FirebaseConstant.UNDEFINE;
        this.RideCanceledByClient = FirebaseConstant.UNDEFINE;
        this.RideCanceledByRider = FirebaseConstant.UNDEFINE;
    }
}
