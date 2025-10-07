package com.sta.buswayapp.ui.boxing.worker.addNewBox;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddingNewBoxViewModel extends ViewModel {

    private final MutableLiveData<CurrentBoxResponse> boxResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<CreatedBoxResponse> uploadedBoxResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CurrentBoxResponse> getBoxResponseMutableLiveData() {
        return boxResponseMutableLiveData;
    }

    public MutableLiveData<CreatedBoxResponse> getUploadedBoxResponseMutableLiveData() {
        return uploadedBoxResponseMutableLiveData;
    }

    public void getCurrentBoxNumber(String projectID){
        DataBuilder.getINSTANCE().getBoxNumber(projectID).enqueue(new Callback<CurrentBoxResponse>() {
            @Override
            public void onResponse(Call<CurrentBoxResponse> call, Response<CurrentBoxResponse> response) {
                if (response.isSuccessful()){
                    boxResponseMutableLiveData.setValue(response.body());
                }else {
                    boxResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CurrentBoxResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                boxResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void createNewBox(CreatedBoxBody boxBody){
        DataBuilder.getINSTANCE().storeBoxData(boxBody).enqueue(new Callback<CreatedBoxResponse>() {
            @Override
            public void onResponse(Call<CreatedBoxResponse> call, Response<CreatedBoxResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Log.d("TAG", "onResponse: " + response.body().message);
                        uploadedBoxResponseMutableLiveData.setValue(response.body());
                    }else {
                        Log.d("TAG", "onResponse: null body");
                    }
                }else {
                    uploadedBoxResponseMutableLiveData.setValue(null);
                    Log.d("TAG", "setValue(null)");
                }
            }

            @Override
            public void onFailure(Call<CreatedBoxResponse> call, Throwable t) {
                uploadedBoxResponseMutableLiveData.setValue(null);
                Log.d("TAG", "onFailure: " + t.getMessage());

            }
        });

    }

}
