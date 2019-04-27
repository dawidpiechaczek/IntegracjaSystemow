package com.example.dawid.visitwroclove.model;

public class ReviewDTO {

    public ReviewDTO(int mark, int userId, int placeEventId){
        this.Mark = mark;
        this.PlaceEventId = placeEventId;
        this.UserId = userId;
    }

    private int Mark;
    private int UserId;
    private int PlaceEventId;

    public int getPlaceEventId() {
        return PlaceEventId;
    }

    public void setPlaceEventId(int placeEventId) {
        PlaceEventId = placeEventId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getMark() {
        return Mark;
    }

    public void setMark(int mark) {
        Mark = mark;
    }
}
