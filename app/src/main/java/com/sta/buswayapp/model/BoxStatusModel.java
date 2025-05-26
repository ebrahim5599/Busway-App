package com.sta.buswayapp.model;

public class BoxStatusModel {
    private String boxNumber, boxStatus;

    public BoxStatusModel(String boxNumber, String boxStatus) {
        this.boxNumber = boxNumber;
        this.boxStatus = boxStatus;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }
}
