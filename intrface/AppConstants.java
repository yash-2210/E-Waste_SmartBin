package com.example.smartbin.intrface;

import android.Manifest;

public interface AppConstants {

    CharSequence DOLLER_SIGN = "$";

    int LOCATION_RESOLVABLE_REQUEST_CODE = 1000;
    int LOCATION_REQUEST_CODE = 1001;

    String[] Location_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    String RESPONSE_DATA = "data";
    String RESPONSE_STATUS = "success";
    String RESPONSE_MESSAGE = "message";

    String bin_id= "bin_id";

}