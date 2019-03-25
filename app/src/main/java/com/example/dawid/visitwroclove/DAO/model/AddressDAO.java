package com.example.dawid.visitwroclove.DAO.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dawid on 07.07.2017.
 */

public class AddressDAO extends RealmObject {

    @PrimaryKey
    private int id;
    private String zip_code;
    private String city;
    private String street;
    private String home_number;
    private String lat;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String latitude) {
        this.lat = latitude;
    }


    public String getLng() {
        return lng;
    }

    public void setLng(String longitude) {
        this.lng = longitude;
    }
}
