package com.example.eric.mapactivity2;

import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

//Should be renamed to parklistactivity

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Context thisActivity;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Park[] parklist;
    private Marker curMarker;
    private Map<Marker, Park> parkMarkerTrackMap = new HashMap<Marker, Park>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupCurrentLocation();
    }

    private void setupCurrentLocation() {
        thisActivity = this;
        mLocationRequest = createLocationRequest();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Globals.GUSERLOCATION = currentLocation;

                if (curMarker != null){
                    curMarker.remove();
                }
                curMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("UserLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                setCameraPosition(currentLocation);

                //Reenable to get location for debug purposes.
                //CharSequence text = String.format("lat:%f lng:%f", location.getLatitude(), location.getLongitude());
                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                ParkGrabber.getInstance().grabAndParseParks(new ParkGrabber.ParkCallback() {
                    @Override
                    public void onParsingFinished(Park[] parks) {
                        parklist = parks;
                        //Populate the new parks.
                        addParksToMap(parklist);
                    }
                });
            }
        };
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        thisActivity = this;

        /*
        ParkGrabber.getInstance().grabAndParseParks(new ParkGrabber.ParkCallback() {
            @Override
            public void onParsingFinished(Park[] parks) {
                parklist = parks;
            }
        });*/

        setMapFace(R.string.style_label_retro);
        setMapUiSettings();
        mMap.setOnMarkerClickListener(this);
    }



    private void setMapUiSettings() {
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

    }

    private void setMapFace(int mSelectedStyleId) {
        MapStyleOptions style;
        if (mSelectedStyleId == R.string.style_label_retro) {
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro);
        }else{
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night);

        }
        mMap.setMapStyle(style);
    }

    public void setCameraPosition(LatLng cameraPosition) {
        CameraPosition destinationCameraPosition = new CameraPosition(cameraPosition, 15, 90, -50);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(destinationCameraPosition));
    }

    public void addParksToMap(Park[] parks) {
        for (Park park : parks) {
            LatLng ll = new LatLng(park.getLatitude(), park.getLongitude());
            MarkerOptions newMarkerOptions = new MarkerOptions()
                    .position(ll)
                    .title(park.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            //Map each marker to map
            Marker marker = mMap.addMarker(newMarkerOptions);
            parkMarkerTrackMap.put(marker, park);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getAllPermissions();
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);

    }

    public void getAllPermissions() {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) thisActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(9000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return mLocationRequest;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getTitle().equals("UserLocation")){return false;}

        Park selectedPark = parkMarkerTrackMap.get(marker);

        Intent parkDetailIntent = new Intent(getApplicationContext(), ParkDetailActivity.class);
        parkDetailIntent.putExtra("Park", selectedPark);
        startActivity(parkDetailIntent);

        Log.d("Park name:", selectedPark.getName());
        return true;
    }
}
