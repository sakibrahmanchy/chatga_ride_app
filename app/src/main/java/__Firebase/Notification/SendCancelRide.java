package __Firebase.Notification;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.ICallBackInstance.INotificationListener;

/**
 * Created by User on 2/13/2018.
 */

public class SendCancelRide extends AsyncTask<String, Void, String> {

    private String path = ("http://139.59.90.128/notification.php");
    private INotificationListener iNotificationListener = null;
    private Context context = null;

    public SendCancelRide(Context context, INotificationListener iNotificationListener){
        this.iNotificationListener = iNotificationListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String actionType = FirebaseConstant.CANCEL_RIDE_NOTIFY_CLIENT;
        String clientID = FirebaseConstant.Empty;
        String riderDeviceToken = FirebaseConstant.Empty;

        try {
            clientID = params[0];
            riderDeviceToken = params[1];
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(FirebaseConstant.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, FirebaseConstant.UTF_8));

            String data = URLEncoder.encode("actionType", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(actionType, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientID, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderDeviceToken", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderDeviceToken, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND;

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, FirebaseConstant.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;

            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            try {
                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            inputStream.close();
            return responseStrBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(FirebaseConstant.RESPONSE_CANCEL_RIDE_NOTF, result);
        iNotificationListener.OnNotificationResponse(true, Integer.parseInt(FirebaseConstant.CANCEL_RIDE_NOTIFY_CLIENT));
    }
}
