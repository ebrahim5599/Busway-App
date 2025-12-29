package com.sta.buswayapp.model.boxing.box.worker.updateBox;
import java.util.ArrayList;
public class UpdateBoxData {
    public int boxId;
    public String dimension;
    public double weight;
    public ArrayList<String> barcodes;

    public UpdateBoxData(int boxId, String dimension, double weight, ArrayList<String> barcodes) {
        this.boxId = boxId;
        this.dimension = dimension;
        this.weight = weight;
        this.barcodes = barcodes;
    }
}
