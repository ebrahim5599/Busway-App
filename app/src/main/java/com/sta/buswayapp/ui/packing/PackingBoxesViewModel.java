package com.sta.buswayapp.ui.packing;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.packing.PackedBoxesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackingBoxesViewModel extends ViewModel {

    private final MutableLiveData<PackedBoxesResponse> packedBoxesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<BoxedItemsResponse> boxedItemsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<SubmittedBoxes> packingSubmitMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<PackedBoxesResponse> getPackedBoxesMutableLiveData() {
        return packedBoxesMutableLiveData;
    }

    public MutableLiveData<BoxedItemsResponse> getBoxedItemsMutableLiveData() {
        return boxedItemsMutableLiveData;
    }

    public MutableLiveData<SubmittedBoxes> getPackingSubmitMutableLiveData() {
        return packingSubmitMutableLiveData;
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

    public void submitPacking(int dept, ArrayList<Integer> boxesIDs) {
        DataBuilder.getINSTANCE().packingSubmit(dept, boxesIDs).enqueue(new Callback<SubmittedBoxes>() {
            @Override
            public void onResponse(Call<SubmittedBoxes> call, Response<SubmittedBoxes> response) {
                packingSubmitMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubmittedBoxes> call, Throwable t) {
                packingSubmitMutableLiveData.setValue(null);
            }
        });
    }
}
