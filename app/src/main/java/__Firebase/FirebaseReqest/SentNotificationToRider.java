package __Firebase.FirebaseReqest;

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
import __Firebase.ICallBackInstance.ICallbackMain;

/**
 * Created by User on 12/2/2017.
 */

public class SentNotificationToRider extends AsyncTask<String, Void, String> {

    private String path = ("http://139.59.90.128/notification.php");
    private Context context;
    private ICallbackMain callbackListener;
    private JSONObject jsonObject;

    public SentNotificationToRider(Context context, ICallbackMain callbackListener) {
        this.context = context;
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String actionType = FirebaseConstant.CLIENT_TO_RIDER;
        String clientId = FirebaseConstant.Empty;
        String clientName = FirebaseConstant.Empty;
        String clientPhone = FirebaseConstant.Empty;
        String clientDeviceToken = FirebaseConstant.Empty;
        String clientImageUrl = FirebaseConstant.Empty;
        String clientRatting = FirebaseConstant.Empty;
        String riderId = FirebaseConstant.Empty;
        String riderDeviceToken = FirebaseConstant.Empty;
        String sourceName = FirebaseConstant.Empty;
        String destinationName = FirebaseConstant.Empty;
        String shortestTime = FirebaseConstant.Empty;
        String shortestDistance = FirebaseConstant.Empty;
        String sourceLatitude = FirebaseConstant.Empty;
        String sourceLongitude = FirebaseConstant.Empty;
        String destinationLatitude = FirebaseConstant.Empty;
        String destinationLongitude = FirebaseConstant.Empty;
        String totalCost = FirebaseConstant.Empty;
        String discountID = FirebaseConstant.Empty;
        String time = FirebaseConstant.Empty;

        try {
            clientId = params[0];
            clientName = params[1];
            clientPhone = params[2];
            clientDeviceToken = params[3];
            clientImageUrl = params[4];
            clientRatting = params[5];
            riderId = params[6];
            riderDeviceToken = params[7];
            sourceName = params[8];
            destinationName = params[9];
            shortestTime = params[10];
            shortestDistance = params[11];
            sourceLatitude = params[12];
            sourceLongitude = params[13];
            destinationLatitude = params[14];
            destinationLongitude = params[15];
            totalCost = params[16];
            discountID = params[17];
            time = params[18];
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
                    URLEncoder.encode("clientId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientId, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientName", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientName, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientPhone", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientPhone, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientDeviceToken", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientDeviceToken, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientImageUrl", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientImageUrl, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientRatting", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientRatting, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderId, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderDeviceToken", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderDeviceToken, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("sourceName", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(sourceName, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("destinationName", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(destinationName, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("shortestTime", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(shortestTime, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("shortestDistance", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(shortestDistance, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("sourceLatitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(sourceLatitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("sourceLongitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(sourceLongitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("destinationLatitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(destinationLatitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("destinationLongitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(destinationLongitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("totalCost", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(totalCost, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("discountID", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(discountID, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("time", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(time, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND;

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
                jsonObject = new JSONObject(responseStrBuilder.toString());
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
        Log.d(FirebaseConstant.RESPONSE_FROM_SERVER, result);
        try {
            if (jsonObject.getString("result").contains("false")) {
                ThrdRequestAgainForRider.ReduceNumberOfRide();
                ThrdRequestAgainForRider.AddRiderIntoBlockList();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FirebaseConstant.IS_RIDE_ACCEPTED_BY_RIDER = 0;
        ThrdRequestAgainForRider.Initiate();
        this.callbackListener.OnSentNotificationToRider(true);
    }
}