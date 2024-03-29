package com.demoriderctg.arif.demorider.GoogleMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.R;
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
    private LatLng source, dest;
    private GoogleMap mMap;
    private ProgressDialog dailog;
    private Context mContext;
    private FloatingActionButton sendButton;
    private FloatingActionButton requestbtn;
    private ImageView defaultMarker;

    public DownloadTask(Context context, GoogleMap mMap, LatLng source, LatLng dest) {
        this.mMap = mMap;
        this.source = source;
        this.dest = dest;
        this.mContext=context;
        sendButton  =(FloatingActionButton) ((Activity)context).findViewById(R.id.btnSend);
        requestbtn  =(FloatingActionButton) ((Activity)context).findViewById(R.id.pickupbtn);
        defaultMarker = (ImageView) ((Activity)context).findViewById(R.id.default_image_Marker);

    }

    @Override
    protected String doInBackground(String... url) {

        String data = "";

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

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser(jObject);

                routes = parser.parse();
                //Get Distance from source to destination
                AppConstant.DISTANCE = parser.getDistance();
                //GET Time to Source to Destination
                AppConstant.DURATION = parser.getDuration();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLACK);
                lineOptions.geodesic(true);

            }
            mMap.clear();
// Drawing polyline in the Google Map for the i-th route


// Constrain the camera target to the Adelaide bounds.
            if(lineOptions !=null){

                sendButton.setVisibility(View.INVISIBLE);
                requestbtn.setVisibility(View.VISIBLE);
                MapActivity.sendtBtnClick=true;
                mMap.getUiSettings().setZoomGesturesEnabled(false);
                mMap.getUiSettings().setScrollGesturesEnabled(false);
                defaultMarker.setVisibility(View.GONE);
                ShowDerectionInGoogleMap showDerectionInGoogleMap = new ShowDerectionInGoogleMap(mMap, lineOptions, source, dest);
                showDerectionInGoogleMap.placeDirection();

            }

        }


    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }


        return data;
    }

}


