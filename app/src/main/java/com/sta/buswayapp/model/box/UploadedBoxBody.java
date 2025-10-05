package com.sta.buswayapp.model.box;

import java.util.ArrayList;

public class UploadedBoxBody {
    public String barcode;
    public String rfidCode;
    public int weight;
    public String dimension;
    public int projectId;
    public ArrayList<String> barCodes;

    public UploadedBoxBody(String barcode, String rfidCode, int weight, String dimension, int projectId, ArrayList<String> barCodes) {
        this.barcode = barcode;
        this.rfidCode = rfidCode;
        this.weight = weight;
        this.dimension = dimension;
        this.projectId = projectId;
        this.barCodes = barCodes;
    }
}
