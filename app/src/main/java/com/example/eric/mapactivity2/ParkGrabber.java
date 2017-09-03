package com.example.eric.mapactivity2;

import com.google.android.gms.common.api.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by eric on 9/3/17.
 */

public class ParkGrabber {

    Gson gson;

    private String response = "[\n" +
            "{\n" +
            "    \"picture_url\": \"http://something.com/image.jpg\",\n" +
            "    \"name\": \"park name\",\n" +
            "    \"id\": 1,\n" +
            "    \"latitude\": 49.1,\n" +
            "    \"longitude\": 120.1\n" +
            "}\n" +
            "]";



    public Park[] grabShit()
    {
        Gson gson = new Gson();
        return gson.fromJson(response, Park[].class);
    }

}
