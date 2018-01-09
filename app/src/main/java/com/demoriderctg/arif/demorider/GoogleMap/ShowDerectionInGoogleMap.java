package com.demoriderctg.arif.demorider.GoogleMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.BottomSheetDialogFragment;

import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogRide;
import com.demoriderctg.arif.demorider.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.Map;

/**
 * Created by Arif on 11/12/2017.
 */

public class ShowDerectionInGoogleMap {

    private  GoogleMap mMap;
    private  PolylineOptions polylineOptions;
    private  MarkerOptions markerOptions;
    private LatLng source,destination;
    private Context context;

    ShowDerectionInGoogleMap( GoogleMap mMap, PolylineOptions lineOptions,LatLng src, LatLng dest){
        this.mMap = mMap;
        this.polylineOptions=lineOptions;
        this.source=src;
        this.destination=dest;
    }

    public  void placeDirection(){
        mMap.addMarker(new MarkerOptions()
                .position(destination));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
        mMap.addMarker(new MarkerOptions()
                .position(source).title("DESTINATION"));

        mMap.addPolyline(polylineOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(source).include(destination);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 500);
        mMap.animateCamera(cameraUpdate);

    }


}
