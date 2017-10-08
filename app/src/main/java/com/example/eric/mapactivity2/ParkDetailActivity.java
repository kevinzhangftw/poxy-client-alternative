package com.example.eric.mapactivity2;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ParkDetailActivity extends AppCompatActivity {

    /*
    Mock JSON object for weather
    * {"coord"=>{"lon"=>-122.92, "lat"=>49.28},
 "weather"=>[{"id"=>802, "main"=>"Clouds", "description"=>"scattered clouds", "icon"=>"03d"}],
 "base"=>"stations",
 "main"=>{"temp"=>20.51, "pressure"=>1015, "humidity"=>64, "temp_min"=>20, "temp_max"=>21},
 "visibility"=>48279,
 "wind"=>{"speed"=>3.1, "deg"=>220},
 "clouds"=>{"a/ll"=>40},
 "dt"=>1502666100,
 "sys"=>{"type"=>1, "id"=>3359, "message"=>0.0041, "country"=>"CA", "sunrise"=>1502629343, "sunset"=>1502681348},
 "id"=>5911606,
 "name"=>"Burnaby",
 "cod"=>200}
    * */
    private ImageView imgView;
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
        Uri gmmIntentUri = Uri.parse("google.navigation:q=cbll=" + parkData.getLatitude() + "," + parkData.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void populateData(Park thisPark){
        //TODO: Populate image
        //imgView.setImageURI(Uri.);
        //imgView = ......thisPark.getPicture_url()
        //Picasso.with(getApplicationContext()).load(thisPark.getPicture_url()).fit().into(imgView);

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
        }
        else{
            Picasso.with(this).load(R.drawable.noparkimage).into(new Target(){
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
            //Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_LONG).show();
        }

        txtName.setText(abbrevParkName(thisPark.getName()));
        final String parkName = thisPark.getName();
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), parkName + "", Toast.LENGTH_SHORT).show();
            }
        });
        /*txtName.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {
                Toast.makeText(getApplicationContext(), parkName + "", Toast.LENGTH_SHORT);
                return true;
            }
        });*/
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
