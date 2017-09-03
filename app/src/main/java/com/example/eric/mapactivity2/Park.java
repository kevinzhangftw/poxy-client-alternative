package com.example.eric.mapactivity2;

/**
 * Created by eric on 9/3/17.
 */

public class Park {

    String picture_url;
    String name;
    int id;
    double latitude;
    double longitude;

    public Park() {
    }

    public Park(String picture_url, String name, int id, double latitude, double longitude) {
        this.picture_url = picture_url;
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
