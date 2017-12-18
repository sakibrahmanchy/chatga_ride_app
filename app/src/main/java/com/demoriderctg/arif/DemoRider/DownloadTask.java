package com.demoriderctg.arif.DemoRider;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.demoriderctg.arif.DemoRider.AppConfig.AppConstant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arif on 11/12/2017.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {

    private String distance;
    private String duration;
    private LatLng source, destination;
    private GoogleMap mMap;

    public DownloadTask(GoogleMap mMap, LatLng source, LatLng dest) {

        this.mMap = mMap;
        this.source = source;
        this.destination = dest;
    }

    @Override
    protected String doInBackground(String... url) {

        String data = AppConstant.Empty;
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
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
                distance = parser.getDistance();
                duration = parser.getDuration();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList points;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double Latitude = Double.parseDouble(point.get(AppConstant.Latitude));
                    double Longitude = Double.parseDouble(point.get(AppConstant.Longitude));
                    LatLng position = new LatLng(Latitude, Longitude);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);
            }
            mMap.clear();
            ShowDerectionInGoogleMap showDerectionInGoogleMap = new ShowDerectionInGoogleMap(mMap, lineOptions, source, destination);
            showDerectionInGoogleMap.placeDirection();
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        String str_origin = AppConstant.OriginEqual + origin.latitude + AppConstant.Comma + origin.longitude;
        String str_dest = AppConstant.DestinationEqual + dest.latitude + AppConstant.Comma + dest.longitude;
        String sensor = AppConstant.Set_Sensor_False;
        String mode = AppConstant.DrivingMode;
        String parameters = str_origin + AppConstant.AMPERSAND + str_dest + AppConstant.AMPERSAND + sensor + AppConstant.AMPERSAND + mode;
        String output = AppConstant.JSON;
        String url = AppConstant.Map_Api_Direction + output + AppConstant.Question + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {

        String data = AppConstant.Empty;
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = AppConstant.Empty;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d(AppConstant.Exception, e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}


