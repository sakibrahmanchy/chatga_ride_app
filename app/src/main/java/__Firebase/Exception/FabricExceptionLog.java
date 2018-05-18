package __Firebase.Exception;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.MainActivity;

import __Firebase.FirebaseUtility.FirebaseConstant;
import io.fabric.sdk.android.Fabric;

/**
 * Created by User on 3/22/2018.
 */

public class FabricExceptionLog {

    public FabricExceptionLog() {
    }

    public static void sendLogToFabric(boolean isLogEnable, String... tagMessage) {

        String tagClass = tagMessage.length >= 1 ? tagMessage[0] : FirebaseConstant.NO_TAG_CLASS_FOUND;
        String message = tagMessage.length >= 2 ? tagMessage[1] : FirebaseConstant.NO_TAG_MESSAGE_FOUND;

        if (isLogEnable && AppConstant.IS_LOG_ENABLE) {
            Log.d(tagClass, message);
        }
        try{
            Throwable throwable = new Exception(message);
            Fabric.with(MainActivity.getMainActivityContext(), new Crashlytics());
            Crashlytics.logException(throwable);
        }catch (Exception e){
           e.printStackTrace();
        }

    }

    public static void sendLogToFabric(String message) {

        try{
            Throwable throwable = new Exception(message);
            Fabric.with(MainActivity.getMainActivityContext(), new Crashlytics());
            Crashlytics.logException(throwable);
        }catch (Exception e){
             e.printStackTrace();
        }

    }

    public static void printLog(String... tagMessage) {
        String tagClass = tagMessage.length > 1 ? tagMessage[0] : FirebaseConstant.NO_TAG_CLASS_FOUND;
        String message = tagMessage.length > 2 ? tagMessage[1] : FirebaseConstant.NO_TAG_MESSAGE_FOUND;
        if (AppConstant.IS_LOG_ENABLE) {
            Log.d(tagClass, message);
        }
    }
}
