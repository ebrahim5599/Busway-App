package com.sta.buswayapp.data.remote;

import android.service.autofill.UserData;

import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.auth.BaseResponse;
import com.sta.buswayapp.model.auth.LoginRequest;
import com.sta.buswayapp.model.auth.UserDataResponse;
import com.sta.buswayapp.model.boxing.box.admin.ReturnedBox.ReturnedBoxResponse;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.boxing.box.admin.boxStatus.BoxStatusResponse;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.modifyBox.ModifyBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.updateBox.UpdateBoxData;
import com.sta.buswayapp.model.boxing.box.worker.updateBox.UpdateBoxResponse;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemResponse;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.boxing.item.Root;
import com.sta.buswayapp.model.boxing.item.ValidateItems;
import com.sta.buswayapp.model.packing.PackedBoxesResponse;
import com.sta.buswayapp.model.project.ProjectResponse;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataBuilder {
    private static final String GUEST_URL = "https://elp-hh.elsewedy.com/";
    private final APIInterface apiInterface;
    private static DataBuilder INSTANCE;


    public DataBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static DataBuilder getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new DataBuilder();
        }
        return INSTANCE;
    }

    public Call<GuestData> postGuestDate(GuestData guestData){
        return apiInterface.storeGuestData(guestData);
    }

    public Call<ClientResponse> getClientData(){
        return apiInterface.getClientData();
    }

    public Call<ProjectResponse> getProjectData(String clientID){
        return apiInterface.getProjectSalesOrder(clientID);
    }

    public Call<CurrentBoxResponse> getBoxNumber(String projectId){
        return apiInterface.getBoxNumber(projectId);
    }

    public Call<CreatedBoxResponse> storeBoxData(CreatedBoxBody uploadedBoxBody){
        return apiInterface.storeBoxData(uploadedBoxBody);
    }

    public Call<Root> validateItems(ValidateItems validateItems){
        return apiInterface.validateItems(validateItems);
    }

    public Call<CompletedBoxResponse> getCompletedBoxes(int projectId){
        return apiInterface.getCompletedBoxes(projectId);
    }

    public Call<BoxedItemsResponse> getBoxedItems(int boxId){
        return apiInterface.getItemsInsideBox(boxId);
    }

    public Call<ModifyBoxResponse> getModifiedBoxes(int projectId){
        return apiInterface.getAllBoxesNeedToModify(projectId);
    }

    public Call<ReturnedBoxResponse> getReturnedBoxData(int boxId){
        return apiInterface.getBoxData(boxId);
    }

    public Call<ModifyItemResponse> updateBoxItems(int boxId, ArrayList<String> barcodes){
        return apiInterface.updateBoxItems(boxId, barcodes);
    }

    public Call<UpdateBoxResponse> updateBoxAndItsItems(UpdateBoxData uploadBoxData){
        return apiInterface.updateBoxAndItsItems(uploadBoxData);
    }

    public Call<BoxStatusResponse> updateBoxStatusToComplete(int boxId){
        return apiInterface.updateBoxStatusToComplete(boxId);
    }

    public Call<BoxStatusResponse> updateBoxStatusToModify(int boxId){
        return apiInterface.updateBoxStatusToModify(boxId);
    }

    public Call<SubmittedBoxes> submitBoxesByAdmin(ArrayList<Integer> idsList){
        return apiInterface.submitBoxesByAdmin(idsList);
    }

    public Call<PackedBoxesResponse> getPackedBoxes(int projectID){
        return apiInterface.getPackedBoxes(projectID);
    }

    public Call<BoxedItemsResponse> getItemsByBoxBarcode(String boxBarcode){
        return apiInterface.getItemsByBoxBarcode(boxBarcode);
    }

    public Call<SubmittedBoxes> markBoxAsReady(int boxID){
        return apiInterface.markBoxAsReady(boxID);
    }

    public Call<SubmittedBoxes> packingSubmit(int dep, ArrayList<Integer> boxesIDs){
        return apiInterface.packingSubmit(dep, boxesIDs);
    }



    // AUTHENTICATION
    public Call<BaseResponse<UserDataResponse>> login(LoginRequest loginRequest){
        return apiInterface.login(loginRequest);
    }

}
