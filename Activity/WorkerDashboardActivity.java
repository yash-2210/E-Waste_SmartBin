package com.example.smartbin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityWorkerDashboardBinding;
import com.example.smartbin.model.ModelBin;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.DPSecurity;
import com.example.smartbin.retrofit.RetrofitCallbacks;
import com.example.smartbin.retrofit.ServiceURL;
import com.example.smartbin.intrface.AppConstants;
import com.example.smartbin.utils.AppUtils;
import com.example.smartbin.utils.GoogleMapUtils;
import com.example.smartbin.widget.CustomInfoWindowGoogleMap;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class WorkerDashboardActivity extends AppCompatActivity
        implements OnMapReadyCallback,   EasyPermissions.PermissionCallbacks,GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private Activity activity;
    private ActivityWorkerDashboardBinding binding;
    ModelUser.LoginAuth user = AppUtils.getUserModel();
    private ArrayList<ModelBin.Bin> binList = new ArrayList<>();
    private List<LatLng> latLngList = new ArrayList<>();
    public LocationRequest locationRequest;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;
    public LatLng latLng;
    private MyLocationCallback myLocationCallback;
    LocationManager locationManager;
    Marker marker;
    GoogleMap gmap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = WorkerDashboardActivity.this;

        setContentView(R.layout.activity_worker_dashboard);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userInfo();
        init();

    }

    private void init() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20) //20 meters
                .setInterval(5000)// 5 seconds, in milliseconds
                .setFastestInterval(4000);
    }

    private void userInfo() {
        TextView username = (TextView) findViewById(R.id.signUpWorkerName);
        TextView useremail = (TextView) findViewById(R.id.workerEmail);
        //ImageView profileImage = (ImageView) findViewById(R.id.ivProfile);

        username.setText(user.getName());
        useremail.setText(user.getEmail());

            /*Glide.with(this)
                    .load(ServiceURL.imageurl + user.getProfileImage())
                    .apply(RequestOptions.circleCropTransform())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_profile_primary)
                    .into(profileImage);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, activity);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> list) {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20)
                .setInterval(5000)
                .setFastestInterval(4000);

        onMapReady(gmap);
        getBinList();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    @AfterPermissionGranted(AppConstants.LOCATION_REQUEST_CODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(activity, AppConstants.Location_PERMS)) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
            getBinList();
        } else EasyPermissions.requestPermissions(activity,
                "EWSB Want tou your current location",
                AppConstants.LOCATION_REQUEST_CODE,
                AppConstants.Location_PERMS);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap = googleMap;
        //gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.setIndoorEnabled(true);
        gmap.getUiSettings().setMapToolbarEnabled(true);
        gmap.getUiSettings().setMyLocationButtonEnabled(true);
        gmap.setMyLocationEnabled(true);
        gmap.setOnMyLocationButtonClickListener(this);
        gmap.setOnMyLocationClickListener(this);

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(activity);

        gmap.setInfoWindowAdapter(customInfoWindow);

        gmap.setOnInfoWindowClickListener(marker -> {
            int i = 0;
            for (ModelBin.Bin bin : binList) {
                if (Objects.equals(marker.getTag(), bin.getAreaName())) {
                    Intent intent = new Intent(activity, QrCodeActivity.class);
                    intent.putExtra(ServiceURL.bin, binList.get(i));
                    activity.startActivity(intent);
                    return;
                }
                i = i + 1;
            }
        });startLocationUpdates();
        getLocation();
        getBinList();
    }

    private void getBinList() {
        RetrofitCallbacks<ModelBin> callbacks = new RetrofitCallbacks<ModelBin>(activity) {
            @Override
            public void onResponse(Call<ModelBin> call, Response<ModelBin> response) {
                super.onResponse(call, response);
                // binding.loader.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            binList.clear();
                            latLngList.clear();
                            binList.addAll(response.body().getData());
                            addToTheMap();
                        }
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 404) {
                    Toast.makeText(activity, "Page Not Found..", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(activity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelBin> call, Throwable t) {
                super.onFailure(call, t);
                // binding.loader.setVisibility(View.GONE);
            }
        };
        new DPSecurity().getBinList(callbacks);
    }

    private void addToTheMap() {
        MarkerOptions markerOptions;
        if (binList != null && binList.size() > 0) {
            for (int i = 0; i < binList.size(); i++) {
                ModelBin.Bin bin = binList.get(i);
                if (bin != null && !bin.getBinLatitude().equals("") && !bin.getBinLongitude().equals(""))
                {
                    markerOptions = new MarkerOptions();
                    markerOptions.position(
                            new LatLng(Double.parseDouble(bin.getBinLatitude()),
                                    Double.parseDouble(bin.getBinLongitude())));
                    markerOptions.snippet(bin.getAreaName());
                    markerOptions.snippet(bin.getBinStorage());
                    markerOptions.visible(true);
                    Marker marker = GoogleMapUtils.setMarker(gmap, markerOptions);
                    marker.setTag(bin.getAreaName());
                    marker.showInfoWindow();
                    latLngList.add(markerOptions.getPosition());
                }
            }

        }
    }

    void getLocation()
    {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (marker != null) {
                        marker.remove();
                    }
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Log.i("LOCATION", "Latitude:" + latitude + ", Longitude:" + longitude);
                    //Toast.makeText(getApplicationContext(), "Latitude:" + latitude + ", Longitude:" + longitude, Toast.LENGTH_SHORT).show();
                        /*MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng).title("You Are here");
                        marker = gmap.addMarker(markerOptions);
                        */
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i("LOCATION", "disable");
                    Toast.makeText(getApplicationContext(), "Please enable your Location Service.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i("LOCATION", "enable");

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i("LOCATION", "status" + status);
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    private class MyLocationCallback extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                currentLocation = location;
                focusOnCurrentLocation();
            }
        }
    }

    private void startLocationUpdates() {
        if (locationRequest == null)
            return;
        if (ActivityCompat.checkSelfPermission(activity,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mFusedLocationClient == null)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                currentLocation = task.getResult();
                focusOnCurrentLocation();
            }
        });

            /*if (myLocationCallback == null) {
                myLocationCallback = new MyLocationCallback();
                locationSettingRequest();
            }*/
    }
    private void locationSettingRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> {
            if (ActivityCompat.checkSelfPermission(activity,
                    ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity,
                    ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest,
                    myLocationCallback, null);
        });

        task.addOnFailureListener(exception -> {
            exception.printStackTrace();
            if (exception instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                    resolvable.startResolutionForResult(activity,
                            AppConstants.LOCATION_RESOLVABLE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException sendEx) {
                    sendEx.printStackTrace();
                }
            }
        });
    }


    private void focusOnCurrentLocation() {
        if (currentLocation != null && gmap != null)
            GoogleMapUtils.animateToLocation(gmap,new LatLng(currentLocation.getLatitude(),
                    currentLocation.getLongitude()));
    }




    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}

