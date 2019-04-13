package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseDTO {
    @SerializedName("id")
    @Expose
    private int id;
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("addressForeignKey")
    @Expose
    private int address_id;
    @SerializedName("address")
    @Expose
    private AddressDTO address;
    @SerializedName("image")
    @Expose
    private String image;
    private double rank;
    private int isFavourite;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getAddressId() {
        return address_id;
    }

    public void setAddressId(int addressId) {
        this.address_id = addressId;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public int isFavourite() {
        return isFavourite;
    }

    public void setFavourite(int favourite) {
        isFavourite = favourite;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }
}

