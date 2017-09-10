package com.example.eric.mapactivity2;

import android.util.Log;
import android.util.LruCache;

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
    String url = "http://4cd5f260.ngrok.io/parks/nearby?latitude=49.276765&longitude=-122.917957";


    private String mockResponse = "[\n" +
            "{\n" +
            "    \"picture_url\": \"http://something.com/image.jpg\",\n" +
            "    \"name\": \"park name\",\n" +
            "    \"id\": 1,\n" +
            "    \"latitude\": 49.1,\n" +
            "    \"longitude\": 120.1\n" +
            "}\n" +
            "]";

    private ParkGrabber() {
        initQueue();
    }


    private void initQueue() {
        // Instantiate the cache
        Cache cache = new NoCache();
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
    }

    public void grabAndParseParks() {
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        Park[] parks = instance.parseParkArray(response);

                        mapsActivity.addParksToMap(parks);
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

    public Park[] parseParkArray(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, Park[].class);
    }

    public void setMapsActivity(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }
}
