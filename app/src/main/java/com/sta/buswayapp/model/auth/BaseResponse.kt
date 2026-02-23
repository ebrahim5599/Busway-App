package com.sta.buswayapp.model.auth

import com.google.gson.annotations.SerializedName
data class BaseResponse<T>(
    @SerializedName("success") val success : Boolean,
    @SerializedName("token") val token : String?,
    @SerializedName("message") val message : String,
    @SerializedName("userData") val userData : T?
)