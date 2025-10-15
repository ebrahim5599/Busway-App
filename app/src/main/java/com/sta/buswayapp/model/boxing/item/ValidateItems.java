package com.sta.buswayapp.model.boxing.item;

import java.util.ArrayList;

public class ValidateItems {
    public int projectId;
    public ArrayList<String> barcodes;


    public ValidateItems(int projectId, ArrayList<String> barcodes) {
        this.projectId = projectId;
        this.barcodes = barcodes;
    }
}
