package com.poxy.eric.natureparker.Networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kai on 2017-07-22.
 */

public class Badge {
    @SerializedName("user_id") private float user_id;
    @SerializedName("session_token") private String session_token;

    public Badge(float user_id, String session_token) {
        this.user_id = user_id;
        this.session_token = session_token;
    }

    public float getUser_id() {
        return user_id;
    }
    public String getSession_token() {
        return session_token;
    }
    public void setUser_id(float user_id) {
        this.user_id = user_id;
    }
    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }
}

