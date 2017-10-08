package com.example.eric.natureparker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.eric.natureparker.Networking.AuthCallback;
import com.example.eric.natureparker.Networking.Badge;
import com.example.eric.natureparker.Networking.PoxyServer;
import com.example.eric.natureparker.Networking.UserState;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.findViewById(android.R.id.content).setBackgroundColor(Color.BLACK);
        authenticateToken();
    }

    /**
     * Checks if the user is authenticated.
     * If so, boots them to the map activity.
     * If not, boots them to the sign in activity.
     */
    private void authenticateToken() {
        if (!isNetworkConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }

        Badge userBadge = UserState.getBadge(this);
        if (userBadge == null) {
            //TODO: show welcome message...
            Log.v("user state", "token is null");
            //direct to signup activity
            startActivity(new Intent(getBaseContext(), SigninActivity.class));
        }

        PoxyServer.authenticate(userBadge, new AuthCallback() {
            @Override
            public void completion(boolean completed) {
                if (completed){
                    startActivity(new Intent(getBaseContext(), MapsActivity.class));
                }else {
                    startActivity(new Intent(getBaseContext(), SigninActivity.class));
                }
            }
        });
    }

    /**
     * This function does a network connectivity check.
     * @return Whether or not the user is connected to the internet.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
