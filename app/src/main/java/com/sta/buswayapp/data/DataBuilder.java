package com.sta.buswayapp.data;

import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.model.project.ProjectResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataBuilder {
    private static final String GUEST_URL = "https://transformsapp.runasp.net/";
    private APIInterface apiInterface;
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


}
