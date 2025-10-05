package com.sta.buswayapp.data;


import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.box.CurrentBoxResponse;
import com.sta.buswayapp.model.box.UploadedBoxBody;
import com.sta.buswayapp.model.box.UploadedBoxResponse;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;
import com.sta.buswayapp.model.project.ProjectResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("api/guest")
    Call<GuestData> storeGuestData(@Body GuestData guestData);

    @GET("api/Project/customers")
    Call<ClientResponse> getClientData();

    @GET("api/Project/salesOrders")
    Call<ProjectResponse> getProjectSalesOrder(@Query("id") String id);

    @POST("api/Item/ValidateItems")
    Call<Root> validateItems(@Body ValidateItems validateItems);

    @POST("api/Box/CreateBox")
    Call<UploadedBoxResponse> storeBoxData(@Body UploadedBoxBody uploadedBoxBody);

    @GET("api/Box/GetNextBoxNumber")
    Call<CurrentBoxResponse> getBoxNumber(@Query("projectId") String projectId);

 }
