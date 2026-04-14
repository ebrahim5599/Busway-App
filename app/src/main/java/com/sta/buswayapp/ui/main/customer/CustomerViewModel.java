package com.sta.buswayapp.ui.main.customer;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.client.ClientResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel{
    MutableLiveData<ClientResponse> clientResponseMutableLiveData = new MutableLiveData<>();

    public void getClientData(){
        DataBuilder.getINSTANCE().getClientData().enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
                Log.d("TAG", "onResponse: Entered");
                clientResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: Entered");
                clientResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void searchForCustomers(String customer){
        DataBuilder.getINSTANCE().searchForCustomers(customer).enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
                clientResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {
                clientResponseMutableLiveData.setValue(null);
            }
        });
    }
}
