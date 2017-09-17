package com.example.eric.mapactivity2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * This class can probably be turfed, but it's here for now as the main launching screen.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getBaseContext(), SplashActivity.class));
    }
}
