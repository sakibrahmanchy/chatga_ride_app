package __Firebase.FirebaseUtility;

import __Firebase.FirebaseWrapper;

/**
 * Created by User on 2/1/2018.
 */

public class ClearData {
    public ClearData(){}

    public static void ClearAllData(){
        ClearHistory();
        ClearRiderModel();
    }

    public static void ClearHistory(){
        FirebaseWrapper.getInstance().getCurrentRidingHistoryModelInstance().ClearData();
    }

    public static void ClearRiderModel(){
        FirebaseWrapper.getInstance().getRiderModelInstance().ClearData();
    }
}
