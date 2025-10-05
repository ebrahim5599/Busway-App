package com.sta.buswayapp.ui.boxing.worker.addNewBox;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.box.CurrentBoxResponse;
import com.sta.buswayapp.model.box.UploadedBoxBody;
import com.sta.buswayapp.model.box.UploadedBoxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddingNewBoxViewModel extends ViewModel {

    private final MutableLiveData<CurrentBoxResponse> boxResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<UploadedBoxResponse> uploadedBoxResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CurrentBoxResponse> getBoxResponseMutableLiveData() {
        return boxResponseMutableLiveData;
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

    public void createNewBox(UploadedBoxBody boxBody){
        DataBuilder.getINSTANCE().storeBoxData(boxBody).enqueue(new Callback<UploadedBoxResponse>() {
            @Override
            public void onResponse(Call<UploadedBoxResponse> call, Response<UploadedBoxResponse> response) {
                if (response.isSuccessful()){
                    uploadedBoxResponseMutableLiveData.setValue(response.body());
                }else {
                    uploadedBoxResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UploadedBoxResponse> call, Throwable t) {
                uploadedBoxResponseMutableLiveData.setValue(null);
            }
        });

    }

}
