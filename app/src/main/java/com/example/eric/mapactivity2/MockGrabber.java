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
    //Park - Image, Name, wind, temp, clouds
    private static Park park1 = new Park("https://img.buzzfeed.com/buzzfeed-static/static/2014-10/8/10/enhanced/webdr10/enhanced-buzz-26742-1412779892-19.jpg", "Burnaby Mountain Parks and Trails", "73f7dc6add3f129af71659be7a765da16eef7902", 49.1, 120.1, "slow", 28, "little");
    public static Park[] parks = {park1};

}
