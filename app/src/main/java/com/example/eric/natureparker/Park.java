package com.example.eric.natureparker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eric on 9/3/17.
 */

public class Park implements Parcelable{

    private String picture_url;
    private String name;
    private String id;
    private double latitude;
    private double longitude;
    private String wind;
    private float temp;
    private String cloud;

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(wind);
        dest.writeString(cloud);
        dest.writeString(picture_url);
        dest.writeFloat(temp);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public int describeContents(){
        return 0;
    }

    private Park(Parcel in){
        this.name = in.readString();
        this.wind = in.readString();
        this.cloud = in.readString();
        this.picture_url = in.readString();
        this.temp = in.readFloat();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<Park> CREATOR = new Parcelable.Creator<Park>(){
        @Override
        public Park createFromParcel(Parcel source){
            return new Park(source);
        }

        public Park[] newArray(int size){
            return new Park[size];
        }
    };

    public Park(String picture_url, String name, String id, double latitude, double longitude, String wind, float temp, String cloud) {
        this.picture_url = picture_url;
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wind = wind;
        this.temp = temp;
        this.cloud = cloud;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWind(){return wind;}

    public float getTemperature(){return temp;}

    public String getCloud(){return cloud;}
}
