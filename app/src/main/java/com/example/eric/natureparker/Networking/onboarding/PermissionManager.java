package com.example.eric.natureparker.Networking.onboarding;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by kai on 2017-06-24.
 */

public class PermissionManager {
    //permission request codes
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1 ;
    private final int AUDIO_PERMISSION_REQUEST_CODE = 2;
    private final int STORAGE_PERMISSION_REQUEST_CODE = 3;

    private final int LOCATION_AUDIO_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private Context callerContext;

    public PermissionManager(final Context context) {
        callerContext = context;
    }

    public void getAllPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ActivityCompat.requestPermissions((Activity) callerContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    LOCATION_AUDIO_STORAGE_PERMISSION_REQUEST_CODE);
        }

    }

    public void getStoragePermission() {
        ActivityCompat.requestPermissions((Activity) callerContext,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_REQUEST_CODE);

    }

    public void getAudioPermission() {
        ActivityCompat.requestPermissions((Activity) callerContext,
                new String[]{Manifest.permission.RECORD_AUDIO},
                AUDIO_PERMISSION_REQUEST_CODE);

    }

    public void getLocationPermission() {
        ActivityCompat.requestPermissions((Activity) callerContext,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    public boolean hasAllPermissions() {
        boolean hasAllPermissions = false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (hasLocationPermission() && hasAudioPermission() && hasStoragePermission()){
            hasAllPermissions = true;
        }

        return hasAllPermissions;
    }

    public boolean hasLocationPermission() {
        return (ContextCompat.checkSelfPermission(callerContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    public boolean hasAudioPermission() {
        return (ContextCompat.checkSelfPermission(callerContext, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED);
    }

    public boolean hasStoragePermission() {
        return (ContextCompat.checkSelfPermission(callerContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
    }


}
