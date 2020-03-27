package com.skills.rentaride.model

import com.google.gson.annotations.SerializedName

data class ResponseDTO (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("data")
    val data: MutableList<Any>?
)
