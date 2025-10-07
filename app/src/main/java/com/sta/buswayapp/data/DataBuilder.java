package com.sta.buswayapp.data;

import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.box.admin.completedBox.CompletedBoxResponse;
import com.sta.buswayapp.model.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.box.worker.createBox.CreatedBoxResponse;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;
import com.sta.buswayapp.model.project.ProjectResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataBuilder {
    private static final String GUEST_URL = "https://transformsapp.runasp.net/";
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




}
