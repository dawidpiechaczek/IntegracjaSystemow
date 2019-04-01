package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDTO extends BaseDTO{

    @SerializedName("EventId")
    @Expose
    private Integer id;
    @SerializedName("CategoryEvent")
    @Expose
    private String type;
    @SerializedName("NameEvent")
    @Expose
    private String name;
    @SerializedName("DescriptionEvent")
    @Expose
    private String description;
    @SerializedName("DateEvent")
    @Expose
    private String date;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("ImageEvent")
    @Expose
    private String image;
    @SerializedName("ThumbEvent")
    @Expose
    private String thumb;
    @SerializedName("Website")
    @Expose
    private String www;
    @SerializedName("Removed")
    @Expose
    private Integer removed=0;
    @SerializedName("IsFavourite")
    @Expose
    private Integer isFavourite;
    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    private AddressDTO address;

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
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getStartDate() {
        return date;
    }

    public void setStartDate(String date) {
        this.date = date;
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
}

