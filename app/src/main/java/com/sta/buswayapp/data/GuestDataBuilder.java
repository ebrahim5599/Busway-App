package com.sta.buswayapp.data;

import com.sta.buswayapp.model.GuestData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuestDataBuilder {
    private static final String GUEST_URL = "https://transformsapp.runasp.net/";
    private APIInterface apiInterface;
    private static GuestDataBuilder INSTANCE;


    public GuestDataBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static GuestDataBuilder getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new GuestDataBuilder();
        }
        return INSTANCE;
    }

    public Call<GuestData> postGuestDate(GuestData guestData){
        return apiInterface.storeGuestData(guestData);
    }
}
