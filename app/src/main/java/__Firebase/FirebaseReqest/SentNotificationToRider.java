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

import __Firebase.ICallBackInstance.ICallbackMain;
import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 12/2/2017.
 */

public class SentNotificationToRider extends AsyncTask<String, Void, String> {

    private String path = ("https://jobayersheikhbd.000webhostapp.com/notification.php");
    private Context context;
    private ICallbackMain callbackListener;

    public SentNotificationToRider(Context context, ICallbackMain callbackListener){
        this.context = context;
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String clientId = params[0];
        String riderId = params[1];
        String sourceLatitude = params[2];
        String sourceLongitude = params[3];
        String destinationLatitude = params[4];
        String destinationLongitude = params[5];

        try{
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod(FirebaseConstant.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, FirebaseConstant.UTF_8));

            String data = URLEncoder.encode("clientId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientId, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderId, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("sourceLatitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(sourceLatitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("sourceLongitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(sourceLongitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("destinationLatitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(destinationLatitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("destinationLongitude", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(destinationLongitude, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND;

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

        }catch(MalformedURLException e){
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
        this.callbackListener.OnSentNotificationToRider(true);
    }
}