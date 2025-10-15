package com.sta.buswayapp.ui.boxing.admin.scannedItems;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.boxing.box.admin.boxStatus.BoxStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannedItemsViewModel extends ViewModel {

    private final MutableLiveData<BoxedItemsResponse> boxedItemsResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<BoxStatusResponse> boxStatusResponseMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<SubmittedBoxes> boxIsReadyMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<SubmittedBoxes> getBoxIsReady() {
        return boxIsReadyMutableLiveData;
    }

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

    public void changeStatusToModify(int boxId){
        DataBuilder.getINSTANCE().updateBoxStatusToModify(boxId).enqueue(new Callback<BoxStatusResponse>() {
            @Override
            public void onResponse(Call<BoxStatusResponse> call, Response<BoxStatusResponse> response) {
                boxStatusResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BoxStatusResponse> call, Throwable t) {
                boxStatusResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void changeStatusToComplete(int boxId){
        DataBuilder.getINSTANCE().updateBoxStatusToComplete(boxId).enqueue(new Callback<BoxStatusResponse>() {
            @Override
            public void onResponse(Call<BoxStatusResponse> call, Response<BoxStatusResponse> response) {
                boxStatusResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BoxStatusResponse> call, Throwable t) {
                boxStatusResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void markBoxAsReady(int boxId){
        DataBuilder.getINSTANCE().markBoxAsReady(boxId).enqueue(new Callback<SubmittedBoxes>() {
            @Override
            public void onResponse(Call<SubmittedBoxes> call, Response<SubmittedBoxes> response) {
                boxIsReadyMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubmittedBoxes> call, Throwable t) {
                boxIsReadyMutableLiveData.setValue(null);
            }
        });
    }

}
