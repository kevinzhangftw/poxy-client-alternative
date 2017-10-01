package com.example.eric.mapactivity2;

import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eric.mapactivity2.Networking.PoxyServer;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;

/**
 * Created by eric on 9/3/17.
 */

public class ParkGrabber {

    private static ParkGrabber instance;
    private RequestQueue mRequestQueue;
    private MapsActivity mapsActivity;

    //String url = PoxyServer.getBaseServerUrl();
    //private String url;

    private ParkGrabber() {
        initQueue();
    }

    private void initQueue() {
        // Instantiate the cache
        Cache cache = new NoCache();
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        //url = "https://poxypoxy.localtunnel.me/parks/nearby?latitude=49.276765&longitude=-122.917957";

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
    }

    private String getParkRequestURL(){
        String baseUrl = PoxyServer.getBaseServerUrl();
        LatLng curLoc = Globals.GUSERLOCATION;
        String wholeUrl = baseUrl + "/parks/nearby?latitude=" + curLoc.latitude + "&longitude=" + curLoc.longitude;
        //Get latitude, longitude
        //"https://poxypoxy.localtunnel.me/parks/nearby?latitude=49.276765&longitude=-122.917957"
        Log.d("WHOLEURL:", wholeUrl);
        return wholeUrl;
    }

    public void grabAndParseParks(final ParkCallback callback) {
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.GET, getParkRequestURL(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        Park[] parks = instance.parseParkArray(response);
                        callback.onParsingFinished(parks);
                        //mapsActivity.addParksToMap(parks);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response: ", error.toString());
                    }
                });

        mRequestQueue.add(jsObjRequest);
    }

    public static ParkGrabber getInstance() {
        if (instance == null) {
            instance = new ParkGrabber();
        }
        return instance;
    }

    interface ParkCallback {
        void onParsingFinished(Park[] parks);
    }

    public Park[] parseParkArray(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, Park[].class);
    }

//    public void setMapsActivity(MapsActivity mapsActivity) {
//        this.mapsActivity = mapsActivity;
//    }
}

