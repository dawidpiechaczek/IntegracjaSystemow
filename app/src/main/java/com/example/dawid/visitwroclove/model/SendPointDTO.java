package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendPointDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("placeEventId")
    @Expose
    private int placeEventId;
    @SerializedName("routeId")
    @Expose
    private int routeId;

    public SendPointDTO(int id, int placeEventId, int routeId) {
        this.id = id;
        this.placeEventId = placeEventId;
        this.routeId = routeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaceEventId() {
        return placeEventId;
    }

    public void setPlaceEventId(int placeEventId) {
        this.placeEventId = placeEventId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
