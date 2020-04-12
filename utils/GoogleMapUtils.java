package com.example.smartbin.utils;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface GoogleMapUtils {

    float MAP_ZOOM = 16F;
    int ROUTE_PATH_WIDTH = 6;
//            -73.9946783, 40.7527102
//            -73.9613216, 40.7418457
//            -73.9515032, 40.7304701
//            -73.9649124, 40.720844
//            -73.9565551, 40.7043921
//            -73.9957784, 40.7034801
//            -74.00625, 40.7032959


    //    https://maps.googleapis.com/maps/api/directions/json?origin=40.7527102,-73.9946783&destination=40.7304701,-73.9515032&waypoints=40.7418457,-73.9613216&key=AIzaSyCZlfZMa0i4jyuG9q2zjDrpYgC_Jtw0zhY
    static Marker setMarker(GoogleMap googleMap, LatLng latLng, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.snippet(snippet);
        return googleMap.addMarker(markerOptions);
    }

    static Marker setMarker(GoogleMap googleMap, MarkerOptions markerOptions) {
        return googleMap.addMarker(markerOptions);
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------------AnimateToLocation----------------------------------------
    //----------------------------------------------------------------------------------------------
    static void animateToLocation(GoogleMap mMap, double latitude, double longitude) {
        animateToLocation(mMap, new LatLng(latitude, longitude));
    }

    static void animateToLocation(GoogleMap mMap, LatLng latLng) {
        animateToLocation(mMap, latLng, MAP_ZOOM);
    }

    static void animateToLocation(GoogleMap mMap, LatLng latLng, float mapZoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
    }

    static void animateToLocationWithDefaultZoom(GoogleMap mMap, LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                mMap.getCameraPosition().zoom));
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------MoveToCenter------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static void moveToCenter(GoogleMap mMap, Double fromLatitude, Double fromLongitude,
                                    Double toLatitude, Double toLongitude) {
        moveToCenter(mMap, new LatLng(fromLatitude, fromLongitude),
                new LatLng(toLatitude, toLongitude));
    }

    public static void moveToCenter(GoogleMap mMap, LatLng... latLng) {
        moveToCenter(mMap, Arrays.asList(latLng));
    }

    public static void moveToCenter(GoogleMap mMap, List<LatLng> latLngList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < latLngList.size(); i++) {
            builder.include(latLngList.get(i));
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);

        mMap.setOnMapLoadedCallback(() -> {
            mMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
                public void onCancel() {
                }

                public void onFinish() {
                    CameraUpdate zout = CameraUpdateFactory.zoomBy(-0.3f);
                    mMap.animateCamera(zout);
                }
            });
        });
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //AddPolyline
    public static Polyline addPolylineToMap(GoogleMap googleMap, List<LatLng> latLngList, int color,
                                            boolean isClickable) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(latLngList);
        polylineOptions.width(ROUTE_PATH_WIDTH);
        polylineOptions.color(color);
        polylineOptions.geodesic(false);
        polylineOptions.clickable(isClickable);
        return googleMap.addPolyline(polylineOptions);
    }

    //DecodeStringToPolylineList
    static List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

}