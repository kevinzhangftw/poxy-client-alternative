package com.example.eric.mapactivity2;

/**
 * Created by eric on 9/3/17.
 */

public class Park {

    private String picture_url;
    private String name;
    private int id;
    private double latitude;
    private double longitude;
    private String wind;
    private int temperature;
    private String cloud;

    public Park() {
    }

    public Park(String picture_url, String name, int id, double latitude, double longitude, String wind, int temperature, String cloud) {
        this.picture_url = picture_url;
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wind = wind;
        this.temperature = temperature;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getTemperature(){return temperature;}

    public String getCloud(){return cloud;}
}
