package com.sta.buswayapp.data.remote;


import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.boxing.box.admin.ReturnedBox.ReturnedBoxResponse;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.boxing.box.admin.boxStatus.BoxStatusResponse;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.modifyBox.ModifyBoxResponse;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemResponse;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.boxing.item.Root;
import com.sta.buswayapp.model.boxing.item.ValidateItems;
import com.sta.buswayapp.model.packing.PackedBoxesResponse;
import com.sta.buswayapp.model.project.ProjectResponse;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("api/guest")
    Call<GuestData> storeGuestData(@Body GuestData guestData);

    @GET("api/Project/customers")
    Call<ClientResponse> getClientData();

    @GET("api/Project/salesOrders")
    Call<ProjectResponse> getProjectSalesOrder(@Query("id") String id);

    // Boxing
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

    @PUT("api/Box/{BoxId}/UpdateItems")
    Call<ModifyItemResponse> updateBoxItems(@Path("BoxId") int boxId, @Body List<String> itemBarcodes);

    @PUT("api/Box/complete/{boxId}")
    Call<BoxStatusResponse> updateBoxStatusToComplete(@Path("boxId") int boxID);

    @PUT("api/Box/need-to-modify/{boxId}")
    Call<BoxStatusResponse> updateBoxStatusToModify(@Path("boxId") int boxID);

    @POST("api/Box/submit")
    Call<SubmittedBoxes> submitBoxesByAdmin(@Body List<Integer> listOfIDs);

    // Packing
    @GET("api/Box/Packing/Admin/{ProjectId}")
    Call<PackedBoxesResponse> getPackedBoxes(@Path("ProjectId") int projectID);

    @GET("api/Item/ItemsByBoxBarCode")
    Call<BoxedItemsResponse> getItemsByBoxBarcode(@Query("barCode") String boxBarcode);

    @POST("api/Box/ReadyForSubmit/{BoxId}")
    Call<SubmittedBoxes> markBoxAsReady(@Path("BoxId") int boxID);

    @POST("api/Box/PackingSubmit")
    Call<SubmittedBoxes> packingSubmit(
            @Query("type") int type,
            @Body List<Integer> boxesIDs
    );

 }
