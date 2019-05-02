package com.example.dawid.visitwroclove.model;

import com.example.dawid.visitwroclove.model.Currently;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("currently")
    @Expose
    private Currently currently;

    @SerializedName("daily")
    @Expose
    private Daily daily;

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

}