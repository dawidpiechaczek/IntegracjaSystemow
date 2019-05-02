package com.example.dawid.visitwroclove.model;

import com.example.dawid.visitwroclove.model.Currently;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("currently")
    @Expose
    private Currently currently;

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

}