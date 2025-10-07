package com.sta.buswayapp.model.box.admin.completedBox;

public class CompletedBoxData {
    public int id;
    public String status;
    public int boxNumber;

    public CompletedBoxData(int boxNumber, String status) {
        this.status = status;
        this.boxNumber = boxNumber;
    }

    public String getStatus() {
        return status;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public int getBoxId() {
        return id;
    }
}
