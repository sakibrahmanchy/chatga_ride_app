package __Firebase.FirebaseUtility;

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
}
