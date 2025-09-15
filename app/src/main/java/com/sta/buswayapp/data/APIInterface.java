package com.sta.buswayapp.data;

import com.sta.buswayapp.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("categories.php")
    public Call<Root> getCategory();
}
