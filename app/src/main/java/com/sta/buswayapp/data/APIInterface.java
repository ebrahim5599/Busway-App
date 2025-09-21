package com.sta.buswayapp.data;


import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.client.ClientResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("api/guest")
    Call<GuestData> storeGuestData(@Body GuestData guestData);

    @GET("api/Project/customers")
    Call<ClientResponse> getClientData();

 }
