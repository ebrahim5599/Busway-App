package com.sta.buswayapp.ui.boxing.worker.addNewItem;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.box.worker.modifyItem.ModifyItemResponse;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewItemViewModel extends ViewModel {

    private final MutableLiveData<Root> validationResponseMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<BoxedItemsResponse> boxedItemsResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ModifyItemResponse> itemResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BoxedItemsResponse> getBoxedItemsResponseMutableLiveData() {
        return boxedItemsResponseMutableLiveData;
    }

    public MutableLiveData<Root> getResponseMutableLiveData() {
        return validationResponseMutableLiveData;
    }

    public MutableLiveData<ModifyItemResponse> getItemResponseMutableLiveData() {
        return itemResponseMutableLiveData;
    }

    public void validateBoxItems(ValidateItems validateItems) {
        DataBuilder.getINSTANCE().validateItems(validateItems).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                validationResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                validationResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void updateItemsList(int boxId, ArrayList<String> barcodes){
        DataBuilder.getINSTANCE().updateBoxItems(boxId, barcodes).enqueue(new Callback<ModifyItemResponse>() {
            @Override
            public void onResponse(Call<ModifyItemResponse> call, Response<ModifyItemResponse> response) {
                itemResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModifyItemResponse> call, Throwable t) {
                itemResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void getBoxItems(int boxId){
        DataBuilder.getINSTANCE().getBoxedItems(boxId).enqueue(new Callback<BoxedItemsResponse>() {
            @Override
            public void onResponse(Call<BoxedItemsResponse> call, Response<BoxedItemsResponse> response) {
                boxedItemsResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BoxedItemsResponse> call, Throwable t) {
                boxedItemsResponseMutableLiveData.setValue(null);
            }
        });
    }
}
