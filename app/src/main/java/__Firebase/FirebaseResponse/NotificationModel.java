package __Firebase.FirebaseResponse;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 1/5/2018.
 */

public class NotificationModel {

    public String title, body, riderName, riderPhone;
    public long riderId;

    public NotificationModel() {
    }

    public NotificationModel(String title, String body, String riderName, String riderPhone, long riderId) {
        this.title = title;
        this.body = body;
        this.riderName = riderName;
        this.riderPhone = riderPhone;
        this.riderId = riderId;
    }

    public void ClearData(){
        this.title = FirebaseConstant.Empty;
        this.body = FirebaseConstant.Empty;
        this.riderName = FirebaseConstant.Empty;
        this.riderPhone = FirebaseConstant.Empty;
        this.riderId = FirebaseConstant.UNDEFINE;
    }
}
