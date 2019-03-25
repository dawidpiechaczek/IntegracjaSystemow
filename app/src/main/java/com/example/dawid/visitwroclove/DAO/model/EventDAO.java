package com.example.dawid.visitwroclove.DAO.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dawid on 24.07.2017.
 */

public class EventDAO extends RealmObject {

    @PrimaryKey
    private int id;
    private String type;
    private String name;
    private String description;
    private String date;
    private int address_id;
    private AddressDAO address;
    private String image;
    private int removed = 0;
    private double rank;
    private int isFavourite;
    private String price;
    private String www;

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public int isFavourite() {
        return isFavourite;
    }

    public void setFavourite(int favourite) {
        isFavourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AddressDAO getAddressDAO() {
        return address;
    }

    public void setAddressDAO(AddressDAO address) {
        this.address = address;
    }

    public int getRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }
}
