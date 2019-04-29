package com.example.dawid.visitwroclove.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendRouteDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isPremium")
    @Expose
    private boolean isPremium;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("length")
    @Expose
    private double length;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userId")
    @Expose
    private int userId;

    public SendRouteDTO(int id, String description, String name, boolean isPremium, boolean isPublic, double length, String type, int userId) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.isPremium = isPremium;
        this.isPublic = isPublic;
        this.length = length;
        this.type = type;
        this.userId = userId;
    }
}
