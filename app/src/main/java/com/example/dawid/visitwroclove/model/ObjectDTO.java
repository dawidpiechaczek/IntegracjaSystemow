package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectDTO extends BaseDTO {

    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("openingHours")
    @Expose
    private String openingHours;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("website")
    @Expose
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }
}
