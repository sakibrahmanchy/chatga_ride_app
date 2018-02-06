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
        if ((data.indexOf("km") >= 0) || (data.indexOf("KM") >= 0) || (data.indexOf("Km") >= 0)) {
            return Double.parseDouble(ret) * 1000d;
        }
        return Double.parseDouble(ret);
    }

    public static Pair<Long, Long> GetHistoryAndTime(String value) {
        if(value == null)   return null;
        String[] Data = value.trim().split("\\s+");
        if (Data.length == 2) {
            return Pair.create(Long.parseLong(Data[0]), Long.parseLong(Data[1]));
        } else return null;
    }

    public static Pair<Integer, Long> GetNumberAndTime(String value) {
        String[] Data = value.trim().split("\\s+");
        if (Data.length == 2) {
            return Pair.create(Integer.parseInt(Data[0]), Long.parseLong(Data[1]));
        } else return null;
    }

    public static Pair<Long, Long> GetHistoryAndTime(String value, boolean isOne) {
        if(value == null)   return null;
        String[] Data = value.trim().split("\\s+");
        if (Data.length == 2) {
            return Pair.create(Long.parseLong(Data[0]), Long.parseLong(Data[1]));
        } else if (Data.length == 1) {
            return Pair.create(Long.parseLong(Data[0]), Long.parseLong(Data[0]));
        }
        return null;
    }

    public static boolean getNetworkTime(final int type, final Context context, final ICallBackCurrentServerTime iCallBackCurrentServerTime) {
        GetCurrentTimeAndDate pendingTask = new GetCurrentTimeAndDate(type, context, iCallBackCurrentServerTime);
        pendingTask.execute();
        return true;
    }
}
