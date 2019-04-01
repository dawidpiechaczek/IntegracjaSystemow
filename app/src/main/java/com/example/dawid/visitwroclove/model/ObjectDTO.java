package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectDTO extends BaseDTO {

    @SerializedName("PlaceId")
    @Expose
    private Integer id;
    @SerializedName("CategoryPlace")
    @Expose
    private String type;
    @SerializedName("NamePlace")
    @Expose
    private String name;
    @SerializedName("DescriptionPlace")
    @Expose
    private String description;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mark")
    @Expose
    private String mark;
    @SerializedName("ImagePlace")
    @Expose
    private String image;
    @SerializedName("ThumbPlace")
    @Expose
    private String thumb;
    @SerializedName("Removed")
    @Expose
    private Integer removed = 0;
    @SerializedName("IsFavourite")
    @Expose
    private Integer isFavourite;
    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    private transient AddressDTO address;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int isRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

}
