package com.poxy.eric.natureparker.Networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import junit.framework.Assert;

import retrofit2.*;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kai on 2017-07-21.
 */

public class PoxyServer {
    //private static final String SERVER_URL = "https://poxypoxy.localtunnel.me";
    private static final String SERVER_URL = "https://poxypoxy.herokuapp.com";

    public static String getBaseServerUrl(){
        return SERVER_URL;
    }

    private static Retrofit getRetrofitConnection(){
        return (new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build());
    }

    public static void authenticate(Badge userbadge, final AuthCallback authCallBack){
        PoxyAPI poxyAPI = getRetrofitConnection().create(PoxyAPI.class);
        Call<Badge> request = poxyAPI.authenticate(userbadge);
        request.enqueue(new Callback<Badge>() {
            @Override
            public void onResponse(Call<Badge> request, Response<Badge> response) {
                if (response.isSuccessful()){
                    authCallBack.completion(true);
                } else {
                    authCallBack.completion(false);
                    Log.d("Response Err Code",new Gson().toJson(response));
                }
            }
            @Override
            public void onFailure(Call<Badge> request, Throwable t) {
                authCallBack.completion(false);
                Log.d("Response Err Code", "PoxyServer authenticate onFailure...");
            }
        });
    }

    public static void register(Cred userCred, final BadgeCallback badgeCallback){
        PoxyAPI poxyAPI = getRetrofitConnection().create(PoxyAPI.class);
        Call<Object> call = poxyAPI.register(userCred);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()){
                    badgeCallback.completion(true, makeBadge(response));
                    Log.d("Response Success", new Gson().toJson(response.body()));
                }else{
                    badgeCallback.completion(false, null);
                    Log.d("Response Err Code",new Gson().toJson(response));
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                badgeCallback.completion(false, null);
                Log.d("Response Err Code", "Cannot Reach Server");
            }
        });
    }

    private static Badge makeBadge(Response<Object> response) {
        Assert.assertTrue(((LinkedTreeMap) response.body()).containsKey("user_id"));
        Assert.assertTrue(((LinkedTreeMap) response.body()).containsKey("session_token"));

        LinkedTreeMap body = (LinkedTreeMap) response.body();
        float user_id = ((Double) body.get("user_id")).floatValue();
        String session_token = (String) body.get("session_token");
        return new Badge(user_id, session_token);
    }

    public static void login(LoginCred userLoginCred, final BadgeCallback badgeCallback){
        PoxyAPI poxyAPI = getRetrofitConnection().create(PoxyAPI.class);
        Call<Object> call = poxyAPI.login(userLoginCred);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()){
                    badgeCallback.completion(true, makeBadge(response));
                    Log.d("Response Success", new Gson().toJson(response.body()));
                }else{
                    badgeCallback.completion(false, null);
                    Log.d("Response Err Code",new Gson().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                badgeCallback.completion(false, null);
                Log.d("Response Err Code", "Cannot Reach Server");
            }
        });
    }
}