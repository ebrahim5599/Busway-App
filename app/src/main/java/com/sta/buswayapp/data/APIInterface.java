package com.sta.buswayapp.data;


import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.box.admin.ReturnedBox.ReturnedBoxResponse;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.box.admin.completedBox.CompletedBoxResponse;
import com.sta.buswayapp.model.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxResponse;
import com.sta.buswayapp.model.box.worker.modifyBox.ModifyBoxResponse;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;
import com.sta.buswayapp.model.project.ProjectResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    Call<CreatedBoxResponse> storeBoxData(@Body CreatedBoxBody uploadedBoxBody);

    @GET("api/Box/GetNextBoxNumber")
    Call<CurrentBoxResponse> getBoxNumber(@Query("projectId") String projectId);

    @GET("api/Box/Admin/Project/{projectId}")
    Call<CompletedBoxResponse> getCompletedBoxes(@Path("projectId") int projectId);

    @GET("api/Item/ItemsByBoxId")
    Call<BoxedItemsResponse> getItemsInsideBox(@Query("boxId") int boxID);

    @GET("api/Box/project/{projectId}/ModifiedBoxes")
    Call<ModifyBoxResponse> getAllBoxesNeedToModify(@Path("projectId") int projectId);

    @GET("api/Box/{BoxId}")
    Call<ReturnedBoxResponse> getBoxData(@Path("BoxId") int boxID);
 }
