package com.sta.buswayapp.model.packing;

public class PackedBoxesData {
    public int id;
    public int boxNumber;
    public String barCode;
    public boolean readyForSubmit;

    public int getBoxId() {
        return id;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public String getBarCode() {
        return barCode;
    }

    public boolean isReadyForSubmit() {
        return readyForSubmit;
    }
}
