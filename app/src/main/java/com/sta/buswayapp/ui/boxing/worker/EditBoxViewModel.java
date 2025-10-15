package com.sta.buswayapp.ui.boxing.worker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.boxing.box.worker.modifyBox.ModifyBoxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBoxViewModel extends ViewModel {

    private final MutableLiveData<ModifyBoxResponse> modifyBoxResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ModifyBoxResponse> getModifyBoxResponseMutableLiveData() {
        return modifyBoxResponseMutableLiveData;
    }

    public void boxesNeedTobeModified(int projectId){
        DataBuilder.getINSTANCE().getModifiedBoxes(projectId).enqueue(new Callback<ModifyBoxResponse>() {
            @Override
            public void onResponse(Call<ModifyBoxResponse> call, Response<ModifyBoxResponse> response) {
                modifyBoxResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModifyBoxResponse> call, Throwable t) {
                modifyBoxResponseMutableLiveData.setValue(null);
            }
        });
    }
}
