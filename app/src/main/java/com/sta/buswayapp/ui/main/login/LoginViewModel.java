package com.sta.buswayapp.ui.main.login;

import android.service.autofill.UserData;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sta.buswayapp.data.remote.DataBuilder;
import com.sta.buswayapp.model.auth.BaseResponse;
import com.sta.buswayapp.model.auth.LoginRequest;
import com.sta.buswayapp.model.auth.UserDataResponse;
import com.sta.buswayapp.model.client.ClientResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    MutableLiveData<BaseResponse<UserDataResponse>> loginResponseMutableLiveData = new MutableLiveData<>();

    public void setLoginResponse(LoginRequest loginResponse) {
        DataBuilder.getINSTANCE().login(loginResponse).enqueue(new Callback<BaseResponse<UserDataResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserDataResponse>> call, Response<BaseResponse<UserDataResponse>> response) {
                loginResponseMutableLiveData.setValue(response.body());
                if (response.isSuccessful())
                    Log.i("TAG", "onResponse: login success");
                else
                    Log.i("TAG", "onResponse: invalid credentials");
            }

            @Override
            public void onFailure(Call<BaseResponse<UserDataResponse>> call, Throwable t) {
                loginResponseMutableLiveData.setValue(null);
            }
        });
    }
}
