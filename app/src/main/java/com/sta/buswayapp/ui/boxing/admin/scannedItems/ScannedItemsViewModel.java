package com.sta.buswayapp.ui.boxing.admin.scannedItems;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannedItemsViewModel extends ViewModel {

    MutableLiveData<BoxedItemsResponse> boxedItemsResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BoxedItemsResponse> getBoxedItemsResponseMutableLiveData() {
        return boxedItemsResponseMutableLiveData;
    }

    public void getScannedItems(int boxId){
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
