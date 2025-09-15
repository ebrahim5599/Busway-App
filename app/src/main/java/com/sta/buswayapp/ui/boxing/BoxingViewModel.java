package com.sta.buswayapp.ui.boxing;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataClient;
import com.sta.buswayapp.model.MealModel;
import com.sta.buswayapp.model.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxingViewModel extends ViewModel {
    MutableLiveData<Root> categoryList = new MutableLiveData<>();
    public void getCategory(){
        DataClient.getINSTATNCE().getCategory().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                categoryList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

}
