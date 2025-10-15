package com.sta.buswayapp.model.boxing.box.admin.boxItems;

public class BoxedItemsData {
    public int id;
    public String barcode;
    public boolean isBoxed;

    public BoxedItemsData(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }
}
