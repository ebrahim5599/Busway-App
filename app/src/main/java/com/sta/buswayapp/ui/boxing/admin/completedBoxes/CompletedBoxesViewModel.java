package com.sta.buswayapp.ui.boxing.admin.completedBoxes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedBoxesViewModel extends ViewModel {

    private final MutableLiveData<CompletedBoxResponse> completedBoxResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<SubmittedBoxes> submittedBoxesMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CompletedBoxResponse> getCompletedBoxResponseMutableLiveData() {
        return completedBoxResponseMutableLiveData;
    }

    public MutableLiveData<SubmittedBoxes> getSubmittedBoxesMutableLiveData() {
        return submittedBoxesMutableLiveData;
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

    public void submitCompletedBoxesByAdmin(ArrayList<Integer> idsList){
        DataBuilder.getINSTANCE().submitBoxesByAdmin(idsList).enqueue(new Callback<SubmittedBoxes>() {
            @Override
            public void onResponse(Call<SubmittedBoxes> call, Response<SubmittedBoxes> response) {
                submittedBoxesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubmittedBoxes> call, Throwable t) {
                submittedBoxesMutableLiveData.setValue(null);
            }
        });
    }
}
