package __Firebase.FirebaseUtility;

import android.content.Context;
import android.util.Pair;

import __Firebase.FirebaseReqest.GetCurrentTimeAndDate;
import __Firebase.ICallBackInstance.ICallBackCurrentServerTime;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseUtilMethod {

    public FirebaseUtilMethod() {
    }

    public static boolean IsEmptyOrNull(String data) {
        if (data == null || data.trim().isEmpty()) return true;
        return false;
    }

    public static Double GetDistanceInDouble(String data) {
        String ret = FirebaseConstant.Empty;
        for (char c : data.toCharArray()) {
            if ((c >= '0' && c <= '9') || c == '.') {
                ret = ret + c;
            }
        }
        return Double.parseDouble(ret);
    }

    public static Pair<Long, Long> GetHistoryAndTime(String value){
        String[] Data = value.trim().split("\\s+");
        return Pair.create(Long.parseLong(Data[0]), Long.parseLong(Data[1]));
    }

    public static boolean getNetworkTime(final int type, final Context context, final ICallBackCurrentServerTime iCallBackCurrentServerTime) {
        GetCurrentTimeAndDate pendingTask = new GetCurrentTimeAndDate(type, context, iCallBackCurrentServerTime);
        pendingTask.execute();
        return true;
    }
}
