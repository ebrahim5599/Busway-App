package com.sta.buswayapp.ui.packing;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.packing.PackedBoxesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackingBoxesViewModel extends ViewModel {

    private final MutableLiveData<PackedBoxesResponse> packedBoxesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<BoxedItemsResponse> boxedItemsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<BoxedItemsResponse> boxBarcodeMutableLiveData = new MutableLiveData();

    public MutableLiveData<PackedBoxesResponse> getPackedBoxesMutableLiveData() {
        return packedBoxesMutableLiveData;
    }

    public MutableLiveData<BoxedItemsResponse> getBoxedItemsMutableLiveData() {
        return boxedItemsMutableLiveData;
    }

    public void getPackedBoxes(int projectID){
        DataBuilder.getINSTANCE().getPackedBoxes(projectID).enqueue(new Callback<PackedBoxesResponse>() {
            @Override
            public void onResponse(Call<PackedBoxesResponse> call, Response<PackedBoxesResponse> response) {
                packedBoxesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PackedBoxesResponse> call, Throwable t) {
                packedBoxesMutableLiveData.setValue(null);
            }
        });
    }

    public void getBoxItemsByBarcod(String barcode){
        DataBuilder.getINSTANCE().getItemsByBoxBarcode(barcode).enqueue(new Callback<BoxedItemsResponse>() {
            @Override
            public void onResponse(Call<BoxedItemsResponse> call, Response<BoxedItemsResponse> response) {
                boxBarcodeMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BoxedItemsResponse> call, Throwable t) {
                boxBarcodeMutableLiveData.setValue(null);
            }
        });
    }


}
