package com.sta.buswayapp.ui.main.project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.project.ProjectResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectViewModel extends ViewModel {

    MutableLiveData<ProjectResponse> projectResponseMutableLiveData = new MutableLiveData<>();

    public void getClientData(String clientId){
        DataBuilder.getINSTANCE().getProjectData(clientId).enqueue(new Callback<ProjectResponse>() {
            @Override
            public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                projectResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProjectResponse> call, Throwable t) {
                projectResponseMutableLiveData.setValue(null);
            }
        });
    }

}
