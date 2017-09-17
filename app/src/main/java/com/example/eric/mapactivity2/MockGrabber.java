package com.example.eric.mapactivity2;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by kai on 2017-09-17.
 */

public class MockGrabber {
    private static Park park1 = new Park("http://something.com/image.jpg", "park name", 1, 49.1, 120.1);
    public static Park[] parks = {park1};

}
