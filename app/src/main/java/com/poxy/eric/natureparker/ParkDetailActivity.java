package com.poxy.eric.natureparker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ParkDetailActivity extends AppCompatActivity {
    private TextView txtName;
    private TextView txtDetails;
    private RelativeLayout mainLayout; //The base layout
    private String imageString;
    private Park parkData;
    private Button buttonSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        connectFormElements();
        parkData = getIntent().getParcelableExtra("Park");
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateData(parkData);
    }

    private void connectFormElements(){
        txtName = (TextView)findViewById(R.id.txtName);
        txtDetails = (TextView)findViewById(R.id.txtDetails);
        mainLayout = (RelativeLayout)findViewById(R.id.activity_park_layout);
        buttonSV = (Button)findViewById(R.id.btnSV);

        buttonSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGoogleMaps();
            }
        });
    }

    private void launchGoogleMaps(){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + parkData.getLatitude() + "," + parkData.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void populateData(Park thisPark){

        imageString = thisPark.getPicture_url();
        if (imageString != null && imageString.length() > 0) {
            Picasso.with(this).load(imageString).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mainLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d("oBF", "Bitmap Failed to load");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.d("oPL", "Bitmap Loading");
                }
            });
        }else{
            mainLayout.setBackgroundResource(R.drawable.noparkimage);
        }

        txtName.setText(abbrevParkName(thisPark.getName()));
        final String parkName = thisPark.getName();
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), parkName + "", Toast.LENGTH_SHORT).show();
            }
        });

        txtDetails.setText("Wind: " + thisPark.getWind() + "\n" +
                           "Temp: " + (int)thisPark.getTemperature() + "°C\n" +
                           "Clouds: " + thisPark.getCloud());

    }

    private String abbrevParkName(String parkName){
        parkName = parkName.replace("International", "Intl");
        parkName = parkName.replace("National", "Ntl");
        parkName = parkName.replace("Provincial", "Prv");
        parkName = parkName.replace("State", "St");
        //parkName = parkName.replace(" Park", "");
        parkName = parkName.replace("Mountain", "Mtn");
        return parkName;
    }
}