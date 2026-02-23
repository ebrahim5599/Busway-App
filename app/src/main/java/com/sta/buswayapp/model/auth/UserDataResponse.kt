package com.sta.buswayapp.model.auth

import com.google.gson.annotations.SerializedName

data class UserDataResponse (
    @SerializedName("userId") val userId : Int,
    @SerializedName("email") val email : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("role") val role : String,
    @SerializedName("department") val department : String,
    @SerializedName("departmentCode") val departmentCode : Int
)