package com.example.dawid.visitwroclove.model;

public class PointDTO {

    private String id;
    private int objectId;
    private int routeId;
    private String lat;
    private String lng;
    private String description;
    private boolean isEvent;

    public PointDTO(){}

    public PointDTO(String id, int objectId, int routeId, String lat, String lng, String description, boolean isEvent){
        this.id = id;
        this.objectId = objectId;
        this.routeId = routeId;
        this.lat = lat;
        this.lng = lng;
        this.description=description;
        this.isEvent = isEvent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }
}
