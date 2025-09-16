package com.sta.buswayapp.ui.guestView;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.GuestDataBuilder;
import com.sta.buswayapp.model.GuestData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestDataViewModel extends ViewModel {
    private final MutableLiveData<GuestData> guestDataMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GuestData> getGuestDataMutableLiveData() {
        return guestDataMutableLiveData;
    }

    public void postNewGuest(GuestData guestData){
        GuestDataBuilder.getINSTANCE().postGuestDate(guestData).enqueue(new Callback<GuestData>() {
            @Override
            public void onResponse(Call<GuestData> call, Response<GuestData> response) {
                if (response.isSuccessful()){
                    guestDataMutableLiveData.postValue(response.body());
                }else {
                    guestDataMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GuestData> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                guestDataMutableLiveData.postValue(null);
            }
        });
    }
}
