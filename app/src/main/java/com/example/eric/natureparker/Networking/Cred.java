package com.example.eric.natureparker.Networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kai on 2017-07-30.
 *
 * This class is for registering users on the server.
 */


public class Cred {
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("password_confirmation") private String password_confirmation;

    public Cred(String email, String password, String password_confirmation) {
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

}
