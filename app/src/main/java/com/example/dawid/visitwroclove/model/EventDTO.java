package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDTO extends BaseDTO{

    @SerializedName("eventDateTime")
    @Expose
    private String date;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("website")
    @Expose
    private String www;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

