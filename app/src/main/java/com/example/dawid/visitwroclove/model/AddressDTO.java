package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("zipCode")
    @Expose
    private String zip_code;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("houseNumber")
    @Expose
    private String home_number;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipCode() {
        return zip_code;
    }

    public void setZipCode(String zipCode) {
        this.zip_code = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHomeNumber() {
        return home_number;
    }

    public void setHomeNumber(String homeNumber) {
        this.home_number = homeNumber;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
