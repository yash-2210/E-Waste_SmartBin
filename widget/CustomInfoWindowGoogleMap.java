package com.example.smartbin.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartbin.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.custom_marker_info, null);

        TextView bin_info = view.findViewById(R.id.binInfo);
        TextView details_tv = view.findViewById(R.id.details);

        LinearLayout llInfo = view.findViewById(R.id.llInfo);

        bin_info.setText(marker.getTitle());//area
        details_tv.setText(marker.getSnippet());//storage
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) { return null; }
}
