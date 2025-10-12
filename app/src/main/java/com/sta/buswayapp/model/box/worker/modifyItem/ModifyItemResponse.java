package com.sta.buswayapp.model.box.worker.modifyItem;

import java.util.ArrayList;

public class ModifyItemResponse {
    public ArrayList<Object> data;
    public boolean isSucsess;
    public int status;
    public String message;
    public ArrayList<ModifyItemData> errors;
}
