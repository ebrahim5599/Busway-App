package com.sta.buswayapp.data;

import com.sta.buswayapp.model.Root;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private APIInterface apiInterface;
    private static DataClient INSTATNCE;


    public DataClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static DataClient getINSTATNCE() {
        if (INSTATNCE == null){
            INSTATNCE = new DataClient();
        }
        return INSTATNCE;
    }

    public Call<Root> getCategory(){
        return apiInterface.getCategory();
    }
}
