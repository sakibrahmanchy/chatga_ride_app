package com.demoriderctg.arif.demorider.GoogleMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.Dailog.BottomSheetDailogRide;
import com.demoriderctg.arif.demorider.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.Map;

import static com.demoriderctg.arif.demorider.GoogleMap.MapActivity.contextOfApplication;

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


        mMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
        mMap.addMarker(new MarkerOptions()
                .position(source).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",400,300))).anchor(.5f,.5f));
        mMap.addPolyline(polylineOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(source).include(destination);

        TypedValue tv = new TypedValue();
        int googleMapPadding=0;
        if (contextOfApplication.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            googleMapPadding = TypedValue.complexToDimensionPixelSize(tv.data,contextOfApplication.getResources().getDisplayMetrics());
        }


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), googleMapPadding+100);
        mMap.animateCamera(cameraUpdate);
        mMap.setLatLngBoundsForCameraTarget(builder.build());
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);


    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap decodeResource = BitmapFactory.decodeResource(contextOfApplication.getResources(),contextOfApplication.getResources().getIdentifier(iconName, "drawable", contextOfApplication.getPackageName()));
        return Bitmap.createScaledBitmap(decodeResource, (int) (((double) decodeResource.getWidth()) * .25d), (int) (((double) decodeResource.getHeight()) * .25d), false);
    }



}
