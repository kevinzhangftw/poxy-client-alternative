package com.poxy.eric.natureparker.Networking.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.poxy.eric.natureparker.R;

import com.poxy.eric.natureparker.MapsActivity;

public class LocationPermissionActivity extends AppCompatActivity {

    private PermissionManager permissionMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_permission);
        permissionMan = new PermissionManager(this);

        //TODO: set this in onclicklistener
        permissionMan.getLocationPermission();
    }


//  success is grantresults = 0, failure is -1.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults){

        printPermissionResults(requestCode, permissions, grantResults);

        if (grantResults.length == 1){
            Log.d("grantResultsArray", "length is 1");
            if (grantResults[0]==0){
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
            }
        }
    }

    private void printPermissionResults(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("reqCode", "" + requestCode);
        for (int i = 0; i < permissions.length; i++)
        {
            Log.d("permArray", permissions[i]);
        }
        for (int i = 0; i < grantResults.length; i++)
        {
            Log.d("grResArray", "" + grantResults[i]);
        }
    }

}
