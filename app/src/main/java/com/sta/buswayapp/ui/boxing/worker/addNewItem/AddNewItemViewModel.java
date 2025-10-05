package com.sta.buswayapp.ui.boxing.worker.addNewItem;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.DataBuilder;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewItemViewModel extends ViewModel {

    private final MutableLiveData<Root> validationResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Root> getResponseMutableLiveData() {
        return validationResponseMutableLiveData;
    }

    public void validateBoxItems(ValidateItems validateItems) {
        DataBuilder.getINSTANCE().validateItems(validateItems).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                validationResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                validationResponseMutableLiveData.setValue(null);
            }
        });
    }
}
