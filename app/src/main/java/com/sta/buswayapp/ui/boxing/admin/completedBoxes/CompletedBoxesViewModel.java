package com.sta.buswayapp.ui.boxing.admin.completedBoxes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.box.admin.completedBox.CompletedBoxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedBoxesViewModel extends ViewModel {

    private final MutableLiveData<CompletedBoxResponse> completedBoxResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CompletedBoxResponse> getCompletedBoxResponseMutableLiveData() {
        return completedBoxResponseMutableLiveData;
    }

    public void getCompletedBoxes(int projectId){
        DataBuilder.getINSTANCE().getCompletedBoxes(projectId).enqueue(new Callback<CompletedBoxResponse>() {
            @Override
            public void onResponse(Call<CompletedBoxResponse> call, Response<CompletedBoxResponse> response) {
                completedBoxResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CompletedBoxResponse> call, Throwable t) {
                completedBoxResponseMutableLiveData.setValue(null);
            }
        });
    }
}
