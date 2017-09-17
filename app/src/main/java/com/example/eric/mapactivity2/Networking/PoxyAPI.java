package com.example.eric.mapactivity2.Networking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by kai on 2017-07-21.
 */


/**
 * Created by kai on 2017-07-21.
 */

public interface PoxyAPI {

    @Headers("Accept: application/json")
    @POST("/authenticate")
    Call<Badge> authenticate(@Body Badge userBadge);

    @Headers("Accept: application/json")
    @POST("/users")
    Call<Object> register(@Body Cred userCred);

    @Headers("Accept: application/json")
    @POST("/login")
    Call<Object> login(@Body LoginCred userLoginCred);


}