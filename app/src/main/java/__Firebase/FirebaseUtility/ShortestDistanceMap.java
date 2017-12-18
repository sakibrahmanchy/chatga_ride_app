package __Firebase.FirebaseUtility;

import android.os.AsyncTask;
import android.util.Pair;
import android.util.Log;

import com.demoriderctg.arif.DemoRider.DirectionsJSONParser;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.ICallBackInstance.IDistanceAndDuration;

/**
 * Created by Arif on 11/12/2017.
 */

public class ShortestDistanceMap extends AsyncTask<String, Void, String> {

    private String Distance;
    private String Duration;
    private LatLng Source, Destination;
    private IDistanceAndDuration iDistanceAndDuration;
    private RiderModel Rider;

    public ShortestDistanceMap() { }

    public void getDistanceAndTime(RiderModel Rider, Pair<Double, Double> _Source, Pair<Double, Double> _Destination, IDistanceAndDuration iDistanceAndDuration) {

        this.Distance = this.Duration = null;
        this.Source = this.Destination = null;

        this.Source = new LatLng(_Source.first, _Source.second);
        this.Destination = new LatLng(_Destination.first, _Destination.second);
        this.iDistanceAndDuration = iDistanceAndDuration;
        this.Rider = Rider;

        String url = getDirectionsUrl(this.Source, this.Destination);
        this.execute(url);
        return;
    }

    @Override
    protected String doInBackground(String... url) {

        String data = FirebaseConstant.Empty;
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d(FirebaseConstant.Exception, e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ParserTask parserTask = new ParserTask();
        parserTask.execute(result);
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser(jObject);

                routes = parser.parse();
                Distance = parser.getDistance();
                Duration = parser.getDuration();
            } catch (Exception e) {
                e.printStackTrace();
            }
            iDistanceAndDuration.OnGetIDistanceAndDuration(Rider, Distance, Duration);
            return routes;
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        String Origin = FirebaseConstant.OriginEqual + origin.latitude + FirebaseConstant.Comma + origin.longitude;
        String Destination = FirebaseConstant.DestinationEqual + dest.latitude + FirebaseConstant.Comma + dest.longitude;
        String sensor = FirebaseConstant.Set_Sensor_False;
        String mode = FirebaseConstant.DrivingMode;
        String parameters = Origin + FirebaseConstant.AMPERSAND + Destination + FirebaseConstant.AMPERSAND + sensor + FirebaseConstant.AMPERSAND + mode;
        String output = FirebaseConstant.JSON;
        String url = FirebaseConstant.Map_Api_Direction + output + FirebaseConstant.Question + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {

        String data = FirebaseConstant.Empty;
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = FirebaseConstant.Empty;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d(FirebaseConstant.Exception, e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}


