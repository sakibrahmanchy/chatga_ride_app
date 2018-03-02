package __Firebase.FirebaseUtility;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.demoriderctg.arif.demorider.GoogleMap.DirectionsJSONParser;
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

import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.ICallBackInstance.IDistanceAndDuration;

/**
 * Created by Arif on 11/12/2017.
 */

public class ShortestDistanceMap {

    private String Distance;
    private String Duration;
    private LatLng Source, Destination;
    private IDistanceAndDuration iDistanceAndDuration;
    private RiderModel Rider;

    public ShortestDistanceMap() {
    }

    public void getDistanceAndTime(RiderModel Rider, Pair<Double, Double> _Source, Pair<Double, Double> _Destination, IDistanceAndDuration iDistanceAndDuration) {

        this.Source = new LatLng(_Source.first, _Source.second);
        this.Destination = new LatLng(_Destination.first, _Destination.second);
        this.iDistanceAndDuration = iDistanceAndDuration;
        this.Rider = Rider;

        this.getNativeDistanceAndDuration();
        /*
        Thread thread =  new Thread(){
            @Override
            public void run(){
                try {
                    downloadUrl(getDirectionsUrl(Source, Destination));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        */
        return;
    }

    private void getNativeDistanceAndDuration(){

        Location SourceLocation= new Location("GPS");
        Location DestinationLocation = new Location("GPS");

        SourceLocation.setLatitude(this.Source.latitude);
        SourceLocation.setLongitude(this.Source.longitude);

        DestinationLocation.setLatitude(this.Destination.latitude);
        DestinationLocation.setLongitude(this.Destination.longitude);

        this.Distance = Float.toString(SourceLocation.distanceTo(DestinationLocation));
        this.Duration = getNativeDuration(this.Distance);

        if (iDistanceAndDuration != null) {
            iDistanceAndDuration.OnGetIDistanceAndDuration(
                    Rider,
                    Distance,
                    Duration
            );
        }
    }

    private String getNativeDuration(String distance){
        float _distance = Float.parseFloat(distance);
        return "1";
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

    private void getDistanceAndDuration(String jsonData){
        JSONObject jObject;
        try {
            jObject = new JSONObject(jsonData);
            DirectionsJSONParser parser = new DirectionsJSONParser(jObject);
            Distance = parser.getDistance();
            Duration = parser.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iDistanceAndDuration != null) {
            iDistanceAndDuration.OnGetIDistanceAndDuration(
                    Rider,
                    Distance,
                    Duration
            );
        }
    }

    private String downloadUrl(String strUrl) throws IOException {

        String data = FirebaseConstant.Empty;
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        synchronized (ShortestDistanceMap.class) {
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
            getDistanceAndDuration(data);
            return data;
        }
    }
}


